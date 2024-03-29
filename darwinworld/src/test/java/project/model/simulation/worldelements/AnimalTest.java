package project.model.simulation.worldelements;

import org.junit.jupiter.api.Test;
import project.model.simulation.worldelements.MapDirection;
import project.model.simulation.worldelements.Vector2D;
import project.model.simulation.config.MapInit;
import project.model.simulation.config.MapSettings;
import project.model.simulation.config.Modifications;
import project.model.simulation.maps.EarthMap;
import project.model.simulation.maps.IWorldMap;
import project.model.simulation.fetures.animalBehavior.Default;
import project.model.simulation.fetures.animalMutations.AnimalMutation;
import project.model.simulation.fetures.animalMutations.DefaultMutation;
import project.model.simulation.fetures.breeding.ClassicBreed;
import project.model.simulation.fetures.spawningPlants.SpawnPlantWithForestedEquators;
import project.model.simulation.worldelements.Animal;
import project.model.simulation.Reproduction;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalTest {

    @Test
    public void testReproduce(){
        List<Integer> genotype1 = new ArrayList<>(List.of(1,2,3,4,5,6,7,0));
        List<Integer> genotype2 = new ArrayList<>(List.of(2,2,2,4,5,1,1,1));
        Animal animal1 = new Animal(new Vector2D(0, 0), MapDirection.NORTH, 100,genotype1);
        Animal animal2 = new Animal(new Vector2D(0, 0), MapDirection.NORTH, 50,genotype2);
        AnimalMutation mutation = new DefaultMutation();
        List<Integer> childGenotype = Reproduction.reproduce(animal1, animal2, mutation);

        assertEquals(genotype1.size(), childGenotype.size());
    }

    @Test
    public void testMove(){
        List<Integer> genotype = new ArrayList<>(List.of(1,2,3,4,5,6,7,0));
        Animal animal = new Animal(new Vector2D(0, 0), MapDirection.NORTH, 100,genotype);

        Modifications modifications = new Modifications(new SpawnPlantWithForestedEquators(),new Default(), new ClassicBreed(), new DefaultMutation());
        MapSettings mapSettings = new MapSettings(10, 10, 20, 10, 1, 8, 0,10, 10, 10,5, 0.2);

        IWorldMap map = new EarthMap(mapSettings,modifications,new MapInit());
        animal.move(map, modifications.animalBehavior());

        assertEquals(new Vector2D(1, 1), animal.getPosition());
        assertEquals(MapDirection.NORTHEAST, animal.getDirection());
        assertEquals(99, animal.getEnergy());
        assertEquals(0, animal.getAge());


        animal.move(map, modifications.animalBehavior());
        assertEquals(new Vector2D(2, 0), animal.getPosition());
        assertEquals(MapDirection.SOUTHEAST, animal.getDirection());
        assertEquals(98, animal.getEnergy());
        assertEquals(0, animal.getAge());
    }

    @Test
    public void computeNumberOfDescendantsTest1(){
        //given
        List<Integer> randomGenotype = new ArrayList<>(List.of(1,2,3,4,5,6,7,0));
        Animal fatherAnimal = new Animal(new Vector2D(0, 0), MapDirection.NORTH, 100,randomGenotype);
        Animal motherAnimal = new Animal(new Vector2D(0, 0), MapDirection.NORTH, 50,randomGenotype);
        Animal kid1 = new Animal(new Vector2D(0, 0), MapDirection.NORTH, 100,randomGenotype);

        //when
        fatherAnimal.setChildrenList(List.of(kid1));
        motherAnimal.setChildrenList(List.of(kid1));

        //then
        assertEquals(1, fatherAnimal.computeNumberOfDescendants());
        assertEquals(1, motherAnimal.computeNumberOfDescendants());
    }
    @Test
    public void computeNumberOfDescendantsTest2(){
        //given
        List<Integer> randomGenotype = new ArrayList<>(List.of(1,2,3,4,5,6,7,0));
        Animal fatherAnimal = new Animal(new Vector2D(0, 0), MapDirection.NORTH, 100,randomGenotype);
        Animal motherAnimal = new Animal(new Vector2D(0, 0), MapDirection.NORTH, 50,randomGenotype);

        Animal kid1 = new Animal(new Vector2D(0, 0), MapDirection.NORTH, 100,randomGenotype);
        Animal kid2 = new Animal(new Vector2D(0, 0), MapDirection.NORTH, 100,randomGenotype);
        Animal kid3 = new Animal(new Vector2D(0, 0), MapDirection.NORTH, 100,randomGenotype);

        Animal kid13_1 = new Animal(new Vector2D(0, 0), MapDirection.NORTH, 100,randomGenotype);
        Animal kid13_2 = new Animal(new Vector2D(0, 0), MapDirection.NORTH, 100,randomGenotype);
        //when
        fatherAnimal.setChildrenList(List.of(kid1, kid2, kid3));
        motherAnimal.setChildrenList(List.of(kid1, kid2, kid3));

        kid1.setChildrenList(List.of(kid13_1, kid13_2));
        kid3.setChildrenList(List.of(kid13_1, kid13_2));

        //then
        assertEquals(5, fatherAnimal.computeNumberOfDescendants());
        assertEquals(5, motherAnimal.computeNumberOfDescendants());
        assertEquals(2, kid1.computeNumberOfDescendants());
        assertEquals(2, kid3.computeNumberOfDescendants());

    }

}
