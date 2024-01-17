package project.simulation.maps.spawningPlants;

import org.junit.jupiter.api.Test;
import project.simulation.config.MapInit;
import project.simulation.config.MapSettings;
import project.simulation.config.Modifications;
import project.simulation.maps.EarthMap;
import project.simulation.maps.IWorldMap;
import project.simulation.maps.animalBehavior.Default;
import project.simulation.maps.animalMutations.DefaultMutation;
import project.simulation.maps.breeding.ClassicBreed;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpawnPlantWithMovingJungleTest {

    @Test
    public void testSpawnPlant() {
        //given
        SpawningPlants spawningPlants = new SpawnPlantWithMovingJungle();
        int plantsToSpawnNumber = 100;
        Modifications modifications = new Modifications(spawningPlants,new Default(), new ClassicBreed(), new DefaultMutation());
        MapSettings mapSettings = new MapSettings(100, 100, 20, 10, 1, 8, 0,0, plantsToSpawnNumber, 10,5, 0.2);

        //when
        IWorldMap map = new EarthMap(mapSettings,modifications,new MapInit());

        //then
        assertEquals(plantsToSpawnNumber, map.getMapPlants().size());

    }

}