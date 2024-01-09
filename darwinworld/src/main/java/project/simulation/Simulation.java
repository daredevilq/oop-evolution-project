package project.simulation;


import project.simulation.config.Modifications;
import project.simulation.maps.IWorldMap;
import project.simulation.observer.SimulationChangeListener;
import project.simulation.statistics.SimulationStatistics;
import project.simulation.worldelements.Animal;

import java.util.*;

public class Simulation implements Runnable{
    private List<Animal> deadAnimals = new ArrayList<>();
    private final IWorldMap map;

    private List<SimulationChangeListener> subscribers = new ArrayList<>();
    private final Modifications modifications;
    private SimulationStatistics simulationStatistics;
    
    public Simulation(IWorldMap map, Modifications modifications) {
        this.modifications = modifications;
        this.simulationStatistics = new SimulationStatistics();
        this.map = map;
    }


    public SimulationStatistics getSimulationStatistics() {
        return simulationStatistics;
    }

    public IWorldMap getMap() {
        return this.map;
    }

    public void run() throws IllegalStateException {


        while (true){
            this.notifySubscribers();
            map.deleteDeadAnimals(this.deadAnimals);
            map.moveAnimals();
            map.eatPlants();
            map.breeding(modifications);
            map.spawnPlants();


//            aliveAnimalsCount = map.getAnimalsList().size();
//            deadAnimalsCount = deadAnimals.size();
            simulationStatistics.updateDailySimulationStats(map.getAnimalsList(), deadAnimals, map.getMapPlants().size(), map.freePlacesOnMap());
            map.updateDailyAnimalStats();


            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void addSubscriber(SimulationChangeListener subscriber){
        subscribers.add(subscriber);
    }

    public void removeSubscriber(SimulationChangeListener subscriber){
        subscribers.remove(subscriber);
    }

    public void notifySubscribers(){
        for (SimulationChangeListener subscriber : subscribers) {
            subscriber.simulationChanged(this);
        }
    }


}

