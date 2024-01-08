package project.simulation;

import javafx.collections.MapChangeListener;
import project.simulation.config.MapSettings;
import project.simulation.config.Modifications;
import project.simulation.maps.IWorldMap;
import project.simulation.observer.SimulationChangeListener;
import project.simulation.worldelements.Animal;

import java.util.*;

public class Simulation implements Runnable{
    private List<Animal> deadAnimals = new ArrayList<>();
    private final IWorldMap map;

    private List<SimulationChangeListener> subscribers = new ArrayList<>();
    private final Modifications modifications;
    private long dayNum = 0;
    private long aliveAnimalsCount=0;
    private long deadAnimalsCount = 0;

    public Simulation(IWorldMap map, Modifications modifications) {
        this.modifications = modifications;
        this.map = map;
    }

    public IWorldMap getMap() {
        return map;
    }

    public void run() throws IllegalStateException {

        while (true){
            this.notifySubscribers();
            map.deleteDeadAnimals(this.deadAnimals);
            map.moveAnimals();
            map.eatPlants();
            map.breeding(modifications);
            map.spawnPlants();

            aliveAnimalsCount = map.getAnimalsList().size();
            deadAnimalsCount = deadAnimals.size();
            map.updateDailyStatistics();
            dayNum++;

//            System.out.println(
//                    "zwierzaki: "+map.getAnimalsList().toString());
//            System.out.println("Liczba roslin: " + map.getMapPlants().size() + " Liczba miejsc wolnych: " + map.getFreePlaces().size());
//            System.out.println("-----------------------------");
//            System.out.println(map.toString());
            //tylko do debugowania
            try {
                Thread.sleep(1000);
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

