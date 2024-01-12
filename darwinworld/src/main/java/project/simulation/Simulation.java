package project.simulation;


import project.simulation.config.Modifications;
import project.simulation.maps.IWorldMap;
import project.simulation.observer.SaveStatistics;
import project.simulation.observer.SubscribersManager;
import project.simulation.statistics.SimulationStatistics;
import project.simulation.statistics.StatisticsWriter;

import java.util.*;

public class Simulation implements Runnable{
    private final IWorldMap map;
    private final UUID  uuid;
    private final Modifications modifications;
    private final SubscribersManager subscribersMenager;
    private final SimulationStatistics simulationStatistics;
    private volatile boolean isRunning = false;
    private boolean storeStatistics = false;
    public Simulation(IWorldMap map, Modifications modifications) {
        this.modifications = modifications;
        this.simulationStatistics = new SimulationStatistics();
        this.map = map;
        this.uuid = UUID.randomUUID();
        this.subscribersMenager = new SubscribersManager();
        subscribersMenager.addSubscriber(new SaveStatistics());
    }


    public SimulationStatistics getSimulationStatistics() {
        return simulationStatistics;
    }

    public IWorldMap getMap() {
        return this.map;
    }
    public SubscribersManager getSubscribers() {
        return subscribersMenager;
    }

    public boolean isRunning() {
        return isRunning;
    }



    public void run() throws IllegalStateException {


        while (isRunning){
            subscribersMenager.notifySubscribers(this);

            map.deleteDeadAnimals();
            map.moveAnimals();
            map.eatPlants();
            map.breeding(modifications);
            map.spawnPlants();

            map.updateDailyAnimalStats();


            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void setStoreStatistics(boolean storeStatistics) {
        this.storeStatistics = storeStatistics;
    }
    public void saveStatistics() {
        if (storeStatistics){
            try {
                StatisticsWriter statisticsWriter = new StatisticsWriter(this);
                statisticsWriter.writeToFile(simulationStatistics);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }
    public void stopSimulation(){
        isRunning = false;
    }

    public void startSimulation(){
        isRunning = true;
    }

    @Override
    public String toString() {
        return uuid.toString();
    }
}

