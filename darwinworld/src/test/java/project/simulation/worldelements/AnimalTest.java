package project.simulation.worldelements;

import org.junit.jupiter.api.Test;
import project.MapDirection;
import project.Vector2D;
import project.simulation.config.MapInit;
import project.simulation.config.MapSettings;
import project.simulation.config.Modifications;
import project.simulation.maps.EarthMap;
import project.simulation.maps.IWorldMap;
import project.simulation.maps.animalBehavior.Default;
import project.simulation.maps.breeding.ClassicBreed;
import project.simulation.maps.spawningPlants.SpawnPlantWithForestedEquators;

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

        List<Integer> childGenotype = animal1.reproduce(animal2);

        assertEquals(genotype1.size(), childGenotype.size());
    }

    @Test
    public void testMove(){
        List<Integer> genotype = new ArrayList<>(List.of(1,2,3,4,5,6,7,0));
        Animal animal = new Animal(new Vector2D(0, 0), MapDirection.NORTH, 100,genotype);

        Modifications modifications = new Modifications(new SpawnPlantWithForestedEquators(),new Default(), new ClassicBreed());
        MapSettings mapSettings = new MapSettings(10, 10, 20, 10, 1, 8, 0, 10, 10, 0.2);

        IWorldMap map = new EarthMap(mapSettings,modifications,new MapInit());
        animal.move(map, modifications.animalBehavior());

        assertEquals(new Vector2D(1, 1), animal.getPosition());
        assertEquals(MapDirection.NORTHEAST, animal.getDirection());
        assertEquals(90, animal.getEnergy());
        assertEquals(1, animal.getAge());


        animal.move(map, modifications.animalBehavior());
        assertEquals(new Vector2D(2, 0), animal.getPosition());
        assertEquals(MapDirection.SOUTHEAST, animal.getDirection());
        assertEquals(80, animal.getEnergy());
        assertEquals(2, animal.getAge());
    }
}
