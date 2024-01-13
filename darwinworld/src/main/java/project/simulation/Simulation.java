package project.simulation;


import project.simulation.config.Modifications;
import project.simulation.maps.IWorldMap;
import project.simulation.observer.SaveStatistics;
import project.simulation.observer.SubscribersManager;
import project.simulation.statistics.SimulationStatistics;

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
    public Simulation(IWorldMap map, Modifications modifications) {
        this.modifications = modifications;
        this.simulationStatistics = new SimulationStatistics();
        this.map = map;
        this.uuid = UUID.randomUUID();
        this.subscribersManager = new SubscribersManager();
        subscribersManager.addSubscriber(new SaveStatistics());
    }

    public SimulationStatistics getSimulationStatistics() {
        return simulationStatistics;
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


    public void run() throws IllegalStateException {


        while (isRunning){
            subscribersManager.notifySubscribers(this);

            map.deleteDeadAnimals();
            map.moveAnimals();
            map.eatPlants();
            map.breeding(modifications);
            map.spawnPlants();

            map.updateDailyAnimalStats();


            try {
                int sleepDuration = (int) (500 * this.simulationSpeed);
                Thread.sleep(sleepDuration);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void setStoreStatistics(boolean storeStatistics) {
        this.storeStatistics = storeStatistics;
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

