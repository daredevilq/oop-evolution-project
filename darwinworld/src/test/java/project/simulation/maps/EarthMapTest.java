package project.simulation.maps;

import org.junit.jupiter.api.Test;
import project.Vector2D;
import project.simulation.config.MapInit;
import project.simulation.config.MapSettings;
import project.simulation.config.Modifications;
import project.simulation.maps.animalBehavior.Default;
import project.simulation.maps.animalMutations.DefaultMutation;
import project.simulation.maps.breeding.ClassicBreed;
import project.simulation.maps.spawningPlants.SpawnPlantWithForestedEquators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EarthMapTest {

    @Test
    public void getNextPositionTest1(){
        //given
        Modifications modifications = new Modifications(new SpawnPlantWithForestedEquators(),new Default(), new ClassicBreed(), new DefaultMutation());
        MapSettings mapSettings = new MapSettings(5, 5, 20, 10, 1, 8, 0,10, 10, 10, 0.2);

        IWorldMap map = new EarthMap(mapSettings,modifications,new MapInit());
        Vector2D newPosition = new Vector2D(5,1);
        //when
        Vector2D resultVector = map.getNextPosition(newPosition);
        //then
        Vector2D expectedVector = new Vector2D(0,1);

        assertEquals(resultVector, expectedVector);
    }


}
