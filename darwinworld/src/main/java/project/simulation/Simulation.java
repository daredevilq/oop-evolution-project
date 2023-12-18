package project.simulation;

import project.simulation.maps.EarthMap;
import project.simulation.worldelements.Animal;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Simulation implements Runnable{
    private List<Animal> animals;
    private final EarthMap map;

    public Simulation(EarthMap map) {
        this.map = map;
        this.animals = new LinkedList<>(map.getMapAnimals().values());
        map.mapInitialize();
    }


    public void run(){

        while (true){

            animals = map.deleteDeadAnimals();

            for (Animal animal : animals) {
                map.moveAnimals(animal);
            }

            map.eatPlants();
            map.breeding();

            map.spawnPlants();

        }
    }
}
