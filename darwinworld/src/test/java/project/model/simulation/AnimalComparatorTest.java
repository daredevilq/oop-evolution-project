package project.model.simulation;

import org.junit.jupiter.api.Test;
import project.model.simulation.worldelements.MapDirection;
import project.model.simulation.worldelements.Vector2D;
import project.model.simulation.worldelements.Animal;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnimalComparatorTest {

    @Test
    public void testCompareDifferentEnergy() {
        //given
        Animal animal1 = createAnimal(15, 5, 3);
        Animal animal2 = createAnimal(10, 5, 3);
        //when
        AnimalComparator comparator = new AnimalComparator();
        int result = comparator.compare(animal1, animal2);
        //then
        assertTrue(result < 0);
    }

    @Test
    public void testCompareDifferentAge() {
        //given
        Animal animal1 = createAnimal(10, 5, 3);
        Animal animal2 = createAnimal(10, 8, 3);
        //when
        AnimalComparator comparator = new AnimalComparator();
        int result = comparator.compare(animal1, animal2);
        //then
        assertTrue(result > 0);
    }

    @Test
    public void testCompareDifferentChildrenCount() {
        //given
        Animal animal1 = createAnimal(10, 5, 2);
        Animal animal2 = createAnimal(10, 5, 4);
        //when
        AnimalComparator comparator = new AnimalComparator();
        int result = comparator.compare(animal1, animal2);
        //then
        assertTrue(result > 0);
    }

    private Animal createAnimal(int energy, int age, int childrenCount) {
        Animal animal = new Animal(new Vector2D(0, 0), MapDirection.NORTH, energy, List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        animal.setAge(age);
        animal.setChildrenCounter(childrenCount);
        return animal;
    }
}