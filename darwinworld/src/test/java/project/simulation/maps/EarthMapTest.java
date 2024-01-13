package project.simulation.maps;

import org.junit.jupiter.api.Test;
import project.MapDirection;
import project.Vector2D;
import project.simulation.config.MapInit;
import project.simulation.config.MapSettings;
import project.simulation.config.Modifications;
import project.simulation.maps.animalBehavior.Default;
import project.simulation.maps.animalMutations.DefaultMutation;
import project.simulation.maps.breeding.ClassicBreed;
import project.simulation.maps.spawningPlants.SpawnPlantWithForestedEquators;
import project.simulation.worldelements.Animal;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EarthMapTest {

    @Test
    public void testGetNextPosition(){
        //given
        Modifications modifications = new Modifications(new SpawnPlantWithForestedEquators(),new Default(), new ClassicBreed(), new DefaultMutation());
        MapSettings mapSettings = new MapSettings(5, 5, 20, 10, 1, 8, 5,10, 10, 10,5, 0.2);

        IWorldMap map = new EarthMap(mapSettings,modifications,new MapInit());
        Vector2D newPosition = new Vector2D(5,1);
        //when
        Vector2D resultVector = map.getNextPosition(newPosition);
        //then
        Vector2D expectedVector = new Vector2D(0,1);

        assertEquals(resultVector, expectedVector);
    }


    @Test
    public void testMoveToOtherSideMap(){
        Modifications modifications = new Modifications(new SpawnPlantWithForestedEquators(),new Default(), new ClassicBreed(), new DefaultMutation());
        MapSettings mapSettings = new MapSettings(5, 5, 20, 10, 1, 8, 5,0, 0, 10,0, 0.2);

        IWorldMap map = new EarthMap(mapSettings, modifications, new MapInit());

        List<Integer> genotype = new ArrayList<>(List.of(0,0,0,0,0));
        Animal animal = new Animal(new Vector2D(4,0), MapDirection.EAST, 100, genotype);

        animal.move(map, modifications.animalBehavior());
        assertEquals(new Vector2D(0,0), animal.getPosition());

        animal.move(map, modifications.animalBehavior());
        assertEquals(new Vector2D(1,0), animal.getPosition());
    }


    @Test
    public void testBlockMovingToOtherSideMap(){
        Modifications modifications = new Modifications(new SpawnPlantWithForestedEquators(),new Default(), new ClassicBreed(), new DefaultMutation());
        MapSettings mapSettings = new MapSettings(5, 5, 20, 10, 1, 8, 5,0, 0, 10,0, 0.2);

        IWorldMap map = new EarthMap(mapSettings, modifications, new MapInit());

        List<Integer> genotype = new ArrayList<>(List.of(0,0,0,0,0));
        Animal animal = new Animal(new Vector2D(0, 4), MapDirection.NORTH, 100, genotype);

        animal.move(map, modifications.animalBehavior());
        assertEquals(new Vector2D(0,4), animal.getPosition());

        animal.move(map, modifications.animalBehavior());
        assertEquals(new Vector2D(0,3), animal.getPosition());
    }
}
