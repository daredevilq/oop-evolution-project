package project.simulation;


import project.simulation.config.Modifications;
import project.simulation.maps.IWorldMap;
import project.simulation.observer.SaveStatistics;
import project.simulation.observer.SubscribersManager;
import project.simulation.observer.UpdateStatsInFile;
import project.simulation.statistics.SimulationStatistics;
import project.simulation.statistics.StatisticsWriter;

import java.util.*;

public class Simulation implements Runnable{
    private final IWorldMap map;
    private final UUID  uuid;
    private final Modifications modifications;
    private final SubscribersManager subscribersManager;
    private final SimulationStatistics simulationStatistics;
    private volatile boolean isRunning = false;
    private boolean storeStatistics = false;
    private double simulationSpeed = 1.0;
    private final StatisticsWriter statisticsWriter;
    public Simulation(IWorldMap map, Modifications modifications)  {
        this.modifications = modifications;
        this.simulationStatistics = new SimulationStatistics();
        this.map = map;
        this.uuid = UUID.randomUUID();
        this.subscribersManager = new SubscribersManager();
        subscribersManager.addSubscriber(new SaveStatistics(simulationStatistics, map));

        this.statisticsWriter = new StatisticsWriter(toString());
        subscribersManager.addSubscriber(new UpdateStatsInFile(simulationStatistics, statisticsWriter));
    }

    public SimulationStatistics getSimulationStatistics() {
        return simulationStatistics;
    }

    public StatisticsWriter getStatisticsWriter() {
        return statisticsWriter;
    }

    public IWorldMap getMap() {
        return this.map;
    }
    public SubscribersManager getSubscribers() {
        return subscribersManager;
    }

    public boolean isStoreStatistics() {
        return storeStatistics;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setSimulationSpeed(double simulationSpeed) {
        this.simulationSpeed = simulationSpeed;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public void setStoreStatistics(boolean storeStatistics) {
        this.storeStatistics = storeStatistics;
    }


    public void run() throws IllegalStateException {
        while (isRunning){
            map.deleteDeadAnimals();


            map.moveAnimals();
            map.eatPlants();
            map.breeding(modifications);
            map.spawnPlants();

            map.updateDailyAnimalStats();

            subscribersManager.notifySubscribers(this);

            try {
                int sleepDuration = (int) (500 * this.simulationSpeed);
                Thread.sleep(sleepDuration);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public String toString() {
        return uuid.toString();
    }
}

