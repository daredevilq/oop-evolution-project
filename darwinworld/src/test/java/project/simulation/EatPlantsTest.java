package project.simulation;

import org.junit.jupiter.api.Test;
import project.MapDirection;
import project.Vector2D;
import project.simulation.maps.animalMutations.AnimalMutation;
import project.simulation.maps.animalMutations.DefaultMutation;
import project.simulation.maps.breeding.ClassicBreed;
import project.simulation.worldelements.Animal;
import project.simulation.worldelements.Grass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EatPlantsTest {

    @Test
    public void testEatingPlantByAnimal(){
        //given
        HashMap<Vector2D, Grass> mapPlants = new HashMap<>();
        List<Animal> animals = new ArrayList<>();
        List<Integer> genotype1 = new ArrayList<>(List.of(1,2,3,4,5,6,7,0));
        Animal animal = new Animal(new Vector2D(0, 0), MapDirection.NORTH, 100,genotype1);
        animals.add(animal);

        Grass grass = new Grass(new Vector2D(0, 0), 10);
        mapPlants.put(new Vector2D(0, 0), grass);
        //when
        EatPlants.eatPlants(mapPlants,animals);

        //then
        assertEquals(110, animal.getEnergy());
        assertEquals(1, animal.getEatenPlants());
    }


    @Test
    public void testEatingPlantByAnimalWithHigherEnergy(){
        //given
        HashMap<Vector2D, Grass> mapPlants = new HashMap<>();
        List<Animal> animals = new ArrayList<>();

        List<Integer> genotype1 = new ArrayList<>(List.of(1,2,3,4,5,6,7,0));
        Animal animal1 = new Animal(new Vector2D(0, 0), MapDirection.NORTH, 100,genotype1);
        Animal animal2 = new Animal(new Vector2D(0, 0), MapDirection.NORTH, 150,genotype1);
        animals.add(animal1);
        animals.add(animal2);

        Grass grass = new Grass(new Vector2D(0, 0), 10);
        mapPlants.put(new Vector2D(0, 0), grass);

        //when
        EatPlants.eatPlants(mapPlants,animals);

        //then
        assertEquals(100, animal1.getEnergy());
        assertEquals(160, animal2.getEnergy());
        assertEquals(0, animal1.getEatenPlants());
        assertEquals(1, animal2.getEatenPlants());
    }

    @Test
    public void testEatingPlantsWithTreeAnimalsOnOnePLace(){
        //given
        HashMap<Vector2D, Grass> mapPlants = new HashMap<>();
        List<Animal> animals = new ArrayList<>();
        List<Integer> genotype1 = new ArrayList<>(List.of(1,2,3,4,5,6,7,0));
        Animal animal1 = new Animal(new Vector2D(1, 2), MapDirection.NORTH, 90, genotype1);
        Animal animal2 = new Animal(new Vector2D(1, 2), MapDirection.NORTH, 30, genotype1);
        Animal animal3 = new Animal(new Vector2D(1, 2), MapDirection.NORTH, 100, genotype1);

        //when
        animals.add(animal1);
        animals.add(animal2);
        animals.add(animal3);

        Grass grass = new Grass(new Vector2D(1, 2), 10);
        mapPlants.put(new Vector2D(1, 2), grass);

        EatPlants.eatPlants(mapPlants,animals);

        //then
        assertEquals(90, animal1.getEnergy());
        assertEquals(30, animal2.getEnergy());
        assertEquals(110, animal3.getEnergy());
    }

    @Test
    public void testWithEatingPlantsFiveAnimalsOnOnePLaceWithDifferentStats(){
        //given
        List<Integer> genotype1 = new ArrayList<>(List.of(1,2,3,4,5,6,7,0));
        List<Integer> genotype2 = new ArrayList<>(List.of(2,2,2,4,5,1,1,1));
        Animal animal1 = new Animal(new Vector2D(1, 2), MapDirection.NORTH, 90, genotype1);
        Animal animal2 = new Animal(new Vector2D(1, 2), MapDirection.NORTH, 30, genotype2);
        Animal animal3 = new Animal(new Vector2D(1, 2), MapDirection.NORTH, 99, genotype1);
        Animal animal4 = new Animal(new Vector2D(1, 2), MapDirection.NORTH, 99, genotype2);
        Animal animal5 = new Animal(new Vector2D(1, 2), MapDirection.NORTH, 100, genotype1);

        List<Animal> animalList = List.of(animal1, animal2, animal3, animal4, animal5);

        //when
        animal3.updateDailyStatsOnAnimal();
        animal5.updateDailyStatsOnAnimal();

        HashMap<Vector2D, Grass> mapPlants = new HashMap<>();
        Grass grass = new Grass(new Vector2D(1, 2), 10);
        mapPlants.put(new Vector2D(1, 2), grass);
        EatPlants.eatPlants(mapPlants,animalList);

        //then
        assertEquals(0, animal3.getEatenPlants());
        assertEquals(0, animal4.getEatenPlants());
        assertEquals(1, animal5.getEatenPlants());
    }

}