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

            animals = map.deleteDeadAnimals();

            for (Animal animal : animals) {
                map.moveAnimals(animal);
            }

            eatPlants();
            map.breeding();

            map.spawnPlants();

            modifications.spawningPlants().spawnAllPlants(map);
            mod.getSpawningPlants().spawnAllPlants(map);


        }
    }

//    public void eatPlants(HashMap <Vector2D, Animal> mapAnimals, HashMap <Vector2D, Grass> mapPlants){
    public void eatPlants(){

        List<Grass> grassToRemove = new ArrayList<>();

        HashMap <Vector2D, Animal> mapAnimals = map.get
        HashMap <Vector2D, Grass> mapPlants = ma.get

        for (Grass grass : mapPlants.values()){
            if (mapAnimals.containsKey(grass.getPosition())) {
                List<Animal> animalsOnField = mapAnimals.values().stream() // zwróć zwierzęta które stoja na danym polu
                        .filter(animal -> animal.getPosition().equals(grass.getPosition()))
                        .collect(Collectors.toList());

                Animal dominantAnimal = animalsOnField.stream() // zwróć kandydata do zjedzenia rośliny
                        .sorted(Comparator.comparingInt(Animal::getEnergy).reversed()
                                .thenComparing(Comparator.comparingLong(Animal::getAge).reversed())
                                .thenComparing(Comparator.comparingInt(Animal::getChildrenCounter).reversed())
                                .thenComparing(animal -> Math.random()))
                        .findFirst()
                        .orElse(null);

                if (dominantAnimal != null){
                    dominantAnimal.eatPlant(grass.getEnergy());
                    grassToRemove.add(grass);
                }
            }
        }

        for (Grass grass : grassToRemove){ // usun trawe z mapPlant po gdy zostala zjedzona
            mapPlants.remove(grass.getPosition());
            plantsCount -= 1;
        }
    }
}

