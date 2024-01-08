package project.simulation.maps.breeding;

import project.MapDirection;
import project.RandomGen;
import project.Vector2D;
import project.simulation.maps.animalMutations.AnimalMutation;
import project.simulation.worldelements.Animal;
import project.simulation.worldelements.AnimalComparator;

import java.util.*;
import java.util.stream.Collectors;

public class ClassicBreed implements Breeding {
    private static final AnimalComparator animalComparator = new AnimalComparator();
    @Override
    public List<Animal> breed(List<Animal> animalList, int startEnergy, int breadEnergy, AnimalMutation mutation) {

        Map<Vector2D, List<Animal>> mapAnimals = createAnimalMap(animalList);
        List<Animal> animals = new ArrayList<>(animalList);

        for (Vector2D position : mapAnimals.keySet()) {
            List<Animal> animalsOnField = mapAnimals.get(position);

            if (animalsOnField != null && animalsOnField.size() >= 2) { // szukamy pola w mapie gdie mamy 2 zwierzęta
                List<Animal> sortedAnimals = animalsOnField.stream()
                        .sorted(animalComparator)
                        .collect(Collectors.toList());


//
//                List<Animal> sortedAnimals = animalsOnField.stream() // sortujemy by wybrać tylko 2
//                        .sorted(Comparator.comparingInt(Animal::getEnergy).reversed()
//                                .thenComparing(Comparator.comparingLong(Animal::getAge).reversed())
//                                .thenComparing(Comparator.comparingInt(Animal::getChildrenCounter).reversed())
//                                .thenComparing(animal -> Math.random()))
//                        .collect(Collectors.toList());

                Animal parent1 = sortedAnimals.get(0);
                Animal parent2 = sortedAnimals.get(1);

                // warunki na rozmnażanie
//                if (parent1.getEnergy() >= startEnergy / 2 && parent2.getEnergy() >= startEnergy / 2) {
                if (parent1.getEnergy() >= breadEnergy && parent2.getEnergy() >= breadEnergy) {

                    List<Integer> childGenotype = parent1.reproduce(parent2, mutation); // tworzymy genotyp dziecka
                    Animal child = new Animal(parent1.getPosition(), MapDirection.NORTHEAST.rotate(RandomGen.randInt(0, 7)), startEnergy, childGenotype);
                    animals.add(child);

                    double energyRatio = (double) parent1.getEnergy() / (parent1.getEnergy() + parent2.getEnergy());
                    parent1.changeStatsAfterBreeding((int) (energyRatio * startEnergy), child);
                    parent2.changeStatsAfterBreeding(startEnergy - (int) (energyRatio * startEnergy), child);
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

