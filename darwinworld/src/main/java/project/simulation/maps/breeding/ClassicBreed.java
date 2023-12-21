package project.simulation.maps.breeding;

import project.MapDirection;
import project.RandomGen;
import project.Vector2D;
import project.simulation.worldelements.Animal;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ClassicBreed implements Breeding{

    @Override
    public void breed() {
        for (Vector2D position : mapAnimals.keySet()) {
            List<Animal> animalsOnField = mapAnimals.values().stream()
                    .filter(animal -> animal.getPosition().equals(position))
                    .collect(Collectors.toList());

            if (animalsOnField.size() >= 2) { // szukamy pola w mapie gdie mamy 2 zwierzęta
                List<Animal> sortedAnimals = animalsOnField.stream() // sortujemy by wybrać tylko 2
                        .sorted(Comparator.comparingInt(Animal::getEnergy).reversed()
                                .thenComparing(Comparator.comparingLong(Animal::getAge).reversed())
                                .thenComparing(Comparator.comparingInt(Animal::getChildrenCounter).reversed())
                                .thenComparing(animal -> Math.random()))
                        .collect(Collectors.toList());

                Animal parent1 = sortedAnimals.get(0);
                Animal parent2 = sortedAnimals.get(1);

                // warunki na rozmnażanie
                int startEnergy = mapSettings.startEnergy();
                if (parent1.getEnergy() >= startEnergy/2 && parent2.getEnergy() >= startEnergy/2) {

                    List<Integer> childGenotype = parent1.reproduce(parent2); // tworzymy genotyp dziecka
                    Animal child = new Animal(mapSettings, parent1.getPosition(), MapDirection.NORTHEAST.rotate(RandomGen.randInt(0, 7)), startEnergy, childGenotype);
                    mapAnimals.put(child.getPosition(), child);
                    animalsCount += 1;

                    double energyRatio = (double) parent1.getEnergy() / (parent1.getEnergy() + parent2.getEnergy());
                    parent1.changeStatsAfterBreeding((int) energyRatio * parent1.getEnergy());
                    parent1.changeStatsAfterBreeding(startEnergy - (int) energyRatio * parent2.getEnergy());
                }
            }
        }

    }
}
