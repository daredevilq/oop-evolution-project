package project.simulation.worldelements;

import java.util.Comparator;

public class AnimalComparator implements Comparator<Animal> {
    @Override
    public int compare(Animal animal1, Animal animal2) {
        return Comparator
                .comparingInt(Animal::getEnergy).reversed()
                .thenComparing(Comparator.comparingLong(Animal::getAge).reversed())
                .thenComparing(Comparator.comparingInt(Animal::getChildrenCounter).reversed())
                .thenComparingDouble(animal -> -Math.random())
                .compare(animal1, animal2);

    }
}
