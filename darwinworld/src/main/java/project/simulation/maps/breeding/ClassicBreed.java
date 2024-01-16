package project.simulation.maps.breeding;

import project.MapDirection;
import project.RandomGenerator;
import project.Vector2D;
import project.simulation.maps.animalMutations.AnimalMutation;
import project.simulation.worldelements.Animal;
import project.simulation.worldelements.AnimalComparator;

import java.util.*;
import java.util.stream.Collectors;

public class ClassicBreed implements Breeding {
    private static final AnimalComparator ANIMAL_COMPARATOR = new AnimalComparator();
    @Override
    public List<Animal> breed(List<Animal> animalList, int breadEnergy, int readyToBreedEnergy, AnimalMutation mutation) {

        Map<Vector2D, List<Animal>> mapAnimals = createAnimalMap(animalList);
        List<Animal> animals = new ArrayList<>(animalList);

        for (Vector2D position : mapAnimals.keySet()) {
            List<Animal> animalsOnField = mapAnimals.get(position);

            if (animalsOnField != null && animalsOnField.size() >= 2) { // szukamy pola w mapie gdie mamy 2 zwierzęta
                List<Animal> sortedAnimals = animalsOnField.stream()
                        .sorted(ANIMAL_COMPARATOR)
                        .collect(Collectors.toList());



                Animal parent1 = sortedAnimals.get(0);
                Animal parent2 = sortedAnimals.get(1);


                if (parent1.getEnergy() >= readyToBreedEnergy && parent2.getEnergy() >= readyToBreedEnergy) {

                    List<Integer> childGenotype = parent1.reproduce(parent2, mutation); // tworzymy genotyp dziecka
                    Animal child = new Animal(parent1.getPosition(), MapDirection.NORTHEAST.rotate(RandomGenerator.randInt(0, 7)), 2 * breadEnergy, childGenotype);
                    animals.add(child);

                    double energyRatio = (double) parent1.getEnergy() / (parent1.getEnergy() + parent2.getEnergy());
                    parent1.changeStatsAfterBreeding(breadEnergy, child);
                    parent2.changeStatsAfterBreeding(breadEnergy, child);
                }
            }
        }
        return animals;

    }

    public Map<Vector2D, List<Animal>> createAnimalMap(List<Animal> animalList) {
        Map<Vector2D, List<Animal>> mapAnimals = new HashMap<>();
        for (Animal animal : animalList) {
            Vector2D position = animal.getPosition();

            List<Animal> animalsAtPosition = mapAnimals.getOrDefault(position, new ArrayList<>());

            animalsAtPosition.add(animal);

            mapAnimals.put(position, animalsAtPosition);
        }

        return mapAnimals;
    }
}

