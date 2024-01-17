package project.model.simulation.maps.fetures.spawningPlants;
import org.junit.jupiter.api.Test;
import project.model.simulation.config.MapInit;
import project.model.simulation.config.MapSettings;
import project.model.simulation.config.Modifications;
import project.model.simulation.maps.EarthMap;
import project.model.simulation.maps.IWorldMap;
import project.model.simulation.fetures.animalBehavior.Default;
import project.model.simulation.fetures.animalMutations.DefaultMutation;
import project.model.simulation.fetures.breeding.ClassicBreed;
import project.model.simulation.fetures.spawningPlants.SpawnPlantWithForestedEquators;
import project.model.simulation.fetures.spawningPlants.SpawnPlantWithMovingJungle;
import project.model.simulation.fetures.spawningPlants.SpawningPlants;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpawningPlantsTest {

    @Test
    public void testSpawnAllPlants(){

        //given
        SpawningPlants spawningPlants = new SpawnPlantWithForestedEquators();
        int plantsToSpawn = 55;
        Modifications modifications = new Modifications(spawningPlants,new Default(), new ClassicBreed(),
                new DefaultMutation());

        MapSettings mapSettings = new MapSettings(100, 100, 20, 10, 1,
                8, 0,0, plantsToSpawn, 10, 5,0.2);

        //when
        IWorldMap map = new EarthMap(mapSettings,modifications,new MapInit());

        //then
        assertEquals(plantsToSpawn, map.getMapPlants().size());


    }
    @Test
    public void testSpawnAllPlantsWithMorePlants(){

        //given
        SpawningPlants spawningPlants = new SpawnPlantWithMovingJungle();
        int plantsToSpawn = 200;
        Modifications modifications = new Modifications(spawningPlants,new Default(), new ClassicBreed(),
                new DefaultMutation());

        MapSettings mapSettings = new MapSettings(100, 100, 20, 10, 1,
                8, 0,0, plantsToSpawn, 10,5, 0.2);

        //when
        IWorldMap map = new EarthMap(mapSettings,modifications,new MapInit());

        //then
        assertEquals(plantsToSpawn, map.getMapPlants().size());

    }

    @Test
    public void testSpawnPlantOnWholeMapInitial(){
        SpawningPlants spawningPlants = new SpawnPlantWithMovingJungle();
        int plantsToSpawn = 25;
        Modifications modifications = new Modifications(spawningPlants, new Default(), new ClassicBreed(),
                new DefaultMutation());
        MapSettings mapSettings = new MapSettings(5, 5, 20, 10, 1,
                8, 0,0, plantsToSpawn, 10,5, 0.2);

        IWorldMap map = new EarthMap(mapSettings,modifications,new MapInit());

        assertEquals(plantsToSpawn, map.getMapPlants().size());
        assertEquals(0, map.getFreePlaces().size());
    }


    @Test
    public void testSpawnPlantOnWholeMapDaily(){
        SpawningPlants spawningPlants = new SpawnPlantWithMovingJungle();
        int plantsPreDay = 5;
        int startPlants = 0;
        Modifications modifications = new Modifications(spawningPlants, new Default(), new ClassicBreed(),
                new DefaultMutation());

        MapSettings mapSettings = new MapSettings(5, 3, 20, 10, 1,
                8, 0,0, startPlants, 10, plantsPreDay, 0.2);

        IWorldMap map = new EarthMap(mapSettings,modifications,new MapInit());
        map.spawnPlants();
        map.spawnPlants();
        map.spawnPlants();

        assertEquals(3 * plantsPreDay, map.getMapPlants().size());
        assertEquals(0, map.getFreePlaces().size());
    }

}
