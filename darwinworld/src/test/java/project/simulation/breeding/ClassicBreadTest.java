package project.simulation.breeding;

import org.junit.jupiter.api.Test;
import project.MapDirection;
import project.Vector2D;
import project.simulation.maps.animalMutations.AnimalMutation;
import project.simulation.maps.animalMutations.DefaultMutation;
import project.simulation.maps.breeding.ClassicBreed;
import project.simulation.worldelements.Animal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ClassicBreadTest {
    @Test
    public void testCreateAnimalMap(){
        List<Integer> genotype1 = new ArrayList<>(List.of(1,2,3,4,5,6,7,0));
        List<Integer> genotype2 = new ArrayList<>(List.of(2,2,2,4,5,1,1,1));
        Animal animal1 = new Animal(new Vector2D(1, 2), MapDirection.NORTH, 100, genotype1);
        Animal animal2 = new Animal(new Vector2D(1, 2), MapDirection.NORTH, 50, genotype2);
        Animal animal3 = new Animal(new Vector2D(3, 4), MapDirection.NORTH, 100, genotype1);

        List<Animal> animalList = List.of(animal1, animal2, animal3);
        ClassicBreed classicBreed = new ClassicBreed();

        Map<Vector2D, List<Animal>> map = classicBreed.createAnimalMap(animalList);


        assertNotNull(map);
        assertEquals(2, map.size());

        assertTrue(map.containsKey(new Vector2D(1, 2)));
        assertEquals(2, map.get(new Vector2D(1, 2)).size());

        assertTrue(map.containsKey(new Vector2D(3, 4)));
        assertEquals(1, map.get(new Vector2D(3, 4)).size());
    }

    @Test
    public void testBreeding(){
        List<Integer> genotype1 = new ArrayList<>(List.of(1,2,3,4,5,6,7,0));
        List<Integer> genotype2 = new ArrayList<>(List.of(2,2,2,4,5,1,1,1));
        Animal animal1 = new Animal(new Vector2D(1, 2), MapDirection.NORTH, 90, genotype1);
        Animal animal2 = new Animal(new Vector2D(1, 2), MapDirection.NORTH, 30, genotype2);
        Animal animal3 = new Animal(new Vector2D(3, 4), MapDirection.NORTH, 100, genotype1);

        List<Animal> animalList = List.of(animal1, animal2, animal3);
        ClassicBreed classicBreed = new ClassicBreed();
        AnimalMutation mutation = new DefaultMutation();
        List<Animal> animals = classicBreed.breed(animalList, 20, 20, mutation);

        assertEquals(4, animals.size());

        assertEquals(75, animal1.getEnergy());
        assertEquals(25, animal2.getEnergy());
    }

    @Test
    public void testBreedingWithTreeAnimalsOnOnePLace(){
        List<Integer> genotype1 = new ArrayList<>(List.of(1,2,3,4,5,6,7,0));
        List<Integer> genotype2 = new ArrayList<>(List.of(2,2,2,4,5,1,1,1));
        Animal animal1 = new Animal(new Vector2D(1, 2), MapDirection.NORTH, 90, genotype1);
        Animal animal2 = new Animal(new Vector2D(1, 2), MapDirection.NORTH, 30, genotype2);
        Animal animal3 = new Animal(new Vector2D(1, 2), MapDirection.NORTH, 100, genotype1);

        List<Animal> animalList = List.of(animal1, animal2, animal3);
        ClassicBreed classicBreed = new ClassicBreed();
        AnimalMutation mutation = new DefaultMutation();
        List<Animal> animals = classicBreed.breed(animalList, 20, 20, mutation);

        assertEquals(4, animals.size());

        assertEquals(1, animal3.getChildrenCounter());
        assertEquals(0, animal2.getChildrenCounter());
        assertEquals(1, animal1.getChildrenCounter());
    }

    @Test
    public void testBreedingWithFiveAnimalsOnOnePLaceWithDifferentStats(){
        List<Integer> genotype1 = new ArrayList<>(List.of(1,2,3,4,5,6,7,0));
        List<Integer> genotype2 = new ArrayList<>(List.of(2,2,2,4,5,1,1,1));
        Animal animal1 = new Animal(new Vector2D(1, 2), MapDirection.NORTH, 90, genotype1);
        Animal animal2 = new Animal(new Vector2D(1, 2), MapDirection.NORTH, 30, genotype2);
        Animal animal3 = new Animal(new Vector2D(1, 2), MapDirection.NORTH, 100, genotype1);
        Animal animal4 = new Animal(new Vector2D(1, 2), MapDirection.NORTH, 99, genotype2);
        Animal animal5 = new Animal(new Vector2D(1, 2), MapDirection.NORTH, 100, genotype1);

        List<Animal> animalList = List.of(animal1, animal2, animal3, animal4, animal5);

        animal3.updateDailyStatsOnAnimal();
        animal5.updateDailyStatsOnAnimal();

        ClassicBreed classicBreed = new ClassicBreed();
        AnimalMutation mutation = new DefaultMutation();
        List<Animal> animals = classicBreed.breed(animalList, 20, 20, mutation);

        assertEquals(6, animals.size());

        assertEquals(1, animal3.getChildrenCounter());
        assertEquals(0, animal4.getChildrenCounter());
        assertEquals(1, animal5.getChildrenCounter());
    }
}
