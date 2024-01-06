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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AbstractMapTest {

    @Test
    public void eatPlantsTest(){
        //given
        int grassEnergy = 3;
        int grassNumber = 10;
        Modifications modifications = new Modifications(new SpawnPlantWithForestedEquators(),new Default(), new ClassicBreed(), new DefaultMutation());
        MapSettings mapSettings = new MapSettings(10, 10, grassNumber, 10, grassEnergy, 8, 0,10, 10, 10, 0.2);
        IWorldMap map = new EarthMap(mapSettings,modifications,new MapInit());

        //when
        for (Vector2D vector : map.getMapPlants().keySet()){
            map.addAnimal(new Animal(vector, MapDirection.NORTH, 10, List.of(1,2,3,4,5,6,7,0)));
        }

        map.eatPlants();

        //then
        assertEquals(0, map.getMapPlants().size());
    }

    @Test
    public void eatPlantsTest2(){
        //given
        int grassEnergy = 3;
        int grassNumber = 10;
        Modifications modifications = new Modifications(new SpawnPlantWithForestedEquators(),new Default(), new ClassicBreed(), new DefaultMutation());
        MapSettings mapSettings = new MapSettings(10, 10, grassNumber, 10, grassEnergy, 8, 0,10, 10, 10, 0.2);
        IWorldMap map = new EarthMap(mapSettings,modifications,new MapInit());

        //when
        for (Vector2D vector : map.getMapPlants().keySet()){
            map.addAnimal(new Animal(vector, MapDirection.NORTH, 10, List.of(1,2,3,4,5,6,7,0)));
        }

        map.eatPlants();

        //then
        boolean outcome = true;
        for (Animal animal: map.getAnimalsList()){
            if (animal.getEnergy() != 13){
                outcome = false;
                break;
            }
        }

        assertTrue(outcome);
    }



}
