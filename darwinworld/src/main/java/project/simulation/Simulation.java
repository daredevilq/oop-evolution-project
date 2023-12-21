package project.simulation;

import project.Vector2D;
import project.simulation.config.Modifications;
import project.simulation.config.Mod;
import project.simulation.maps.IWorldMap;
import project.simulation.maps.spawningPlants.SpawningPlants;
import project.simulation.worldelements.Animal;
import project.simulation.worldelements.Grass;

import java.util.*;
import java.util.stream.Collectors;

public class Simulation implements Runnable{
    private List<Animal> animals;
    private final IWorldMap map;

    private final Modifications modifications;
    private final Mod mod;


    private int plantsCount;
    private long dayNum = 0;
    private long animalsCount;
    private long diedAnimalsCount = 0;





    public Simulation(IWorldMap map, Modifications modifications) {
        this.modifications = modifications;
        this.map = map;
        this.animals = new LinkedList<>(map.getMapAnimals().values());
        map.mapInitialize();
    }

    public long getPlantsCount() {
        return plantsCount;
    }

    public void run(){

        while (true){

            animals = map.deleteDeadAnimals(this.animals); // tu sie zmienia l zwierzat

            for (Animal animal : animals) {
                map.moveAnimal(animal);
            }

            int eatedPlants = (int) map.eatPlants();

            map.breeding(); // tu sie zmienia l zwierzat

            map.spawnPlants();

            modifications.spawningPlants().spawnAllPlants(map);
            mod.getSpawningPlants().spawnAllPlants(map);


        }
    }


}

