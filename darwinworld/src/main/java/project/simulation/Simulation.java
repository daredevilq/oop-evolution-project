package project.simulation;


import project.simulation.config.Modifications;
import project.simulation.maps.IWorldMap;
import project.simulation.observer.SimulationChangeListener;
import project.simulation.statistics.SimulationStatistics;
import project.simulation.statistics.StatisticsWriter;
import project.simulation.worldelements.Animal;

import java.io.IOException;
import java.util.*;

public class Simulation implements Runnable{
    private final IWorldMap map;
    private final UUID  uuid;
    private final Modifications modifications;
    private List<Animal> deadAnimals = new ArrayList<>();
    private List<SimulationChangeListener> subscribers = new ArrayList<>();
    private SimulationStatistics simulationStatistics;
    private StatisticsWriter statisticsWriter;
    private volatile boolean isRunning = false;
    private boolean storeStatistics = false;
    public Simulation(IWorldMap map, Modifications modifications) {
        this.modifications = modifications;
        this.simulationStatistics = new SimulationStatistics();
        this.map = map;
        this.uuid = UUID.randomUUID();
    }


    public SimulationStatistics getSimulationStatistics() {
        return simulationStatistics;
    }

    public IWorldMap getMap() {
        return this.map;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public boolean isStoreStatistics() {
        return storeStatistics;
    }



    public void run() throws IllegalStateException {


        while (isRunning){
            try {
                this.notifySubscribers();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            map.deleteDeadAnimals(this.deadAnimals);
            map.moveAnimals();
            map.eatPlants();
            map.breeding(modifications);
            map.spawnPlants();
            simulationStatistics.updateDailySimulationStats(map.getAnimalsList(), deadAnimals, map.getMapPlants().size(), map.freePlacesOnMap());
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
        if (storeStatistics){
            try {
                statisticsWriter = new StatisticsWriter(this);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void writeDownStatistics() throws IOException {
        if (storeStatistics){
            statisticsWriter.writeStats(simulationStatistics);
        }
    }

    public void saveStatistics() throws IOException {
        if (storeStatistics){
            statisticsWriter.closeFile();
        }
    }
    public void stopSimulation(){
        isRunning = false;
    }

    public void startSimulation(){
        isRunning = true;
    }

    public void addSubscriber(SimulationChangeListener subscriber){
        subscribers.add(subscriber);
    }

    public void removeSubscriber(SimulationChangeListener subscriber){
        subscribers.remove(subscriber);
    }

    public void notifySubscribers() throws IOException {
        for (SimulationChangeListener subscriber : subscribers) {
            subscriber.simulationChanged(this);
        }
    }

    @Override
    public String toString() {
        return uuid.toString();
    }
}

