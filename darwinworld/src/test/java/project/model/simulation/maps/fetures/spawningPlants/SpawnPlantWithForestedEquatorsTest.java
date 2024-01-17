package project.model.simulation.maps.fetures.spawningPlants;

import org.junit.jupiter.api.Test;
import project.model.simulation.worldelements.Vector2D;
import project.model.simulation.config.MapInit;
import project.model.simulation.config.MapSettings;
import project.model.simulation.config.Modifications;
import project.model.simulation.maps.EarthMap;
import project.model.simulation.maps.IWorldMap;
import project.model.simulation.fetures.animalBehavior.Default;
import project.model.simulation.fetures.animalMutations.DefaultMutation;
import project.model.simulation.fetures.breeding.ClassicBreed;
import project.model.simulation.fetures.spawningPlants.SpawnPlantWithForestedEquators;
import project.model.simulation.fetures.spawningPlants.SpawningPlants;

import static org.junit.jupiter.api.Assertions.*;

class SpawnPlantWithForestedEquatorsTest {
    @Test
    public void testSpawnPlantWithForestedEquators(){
        //given
        SpawningPlants spawningPlants = new SpawnPlantWithForestedEquators();
        int plantsToSpawn = 500;
        Modifications modifications = new Modifications(spawningPlants,new Default(), new ClassicBreed(),
                new DefaultMutation());

        MapSettings mapSettings = new MapSettings(500, 500, 20, 10, 1,
                8, 0,0, plantsToSpawn, 10,5, 0.2);

        //when
        IWorldMap map = new EarthMap(mapSettings,modifications,new MapInit());
        int insideJungle = 0;

        for(Vector2D vector: map.getMapPlants().keySet()){
            if(vector.follows(map.getJungleBoundary().lowerLeftCorner()) && vector.precedes(map.getJungleBoundary().upperRightCorner())){
                insideJungle++;
            }
        }
        int shouldBeInsideJungle = (int) (plantsToSpawn * 0.8);
        double difference = (int) (plantsToSpawn * 0.05);

        //then
        assertEquals(plantsToSpawn, map.getMapPlants().size());
        assertTrue(insideJungle >= shouldBeInsideJungle - difference &&
                insideJungle <= shouldBeInsideJungle + difference);

    }

    @Test
    public void testPreferredPlacesInMiddleLine() {
        SpawningPlants spawningPlants = new SpawnPlantWithForestedEquators();
        Modifications modifications = new Modifications(spawningPlants, new Default(), new ClassicBreed(),
                new DefaultMutation());
        MapSettings mapSettings = new MapSettings(5, 5, 20, 10, 1,
                8, 0,0, 0, 10,0, 0.2);

        IWorldMap map = new EarthMap(mapSettings,modifications,new MapInit());

        for (int i=0; i < 5; i++)
            assertTrue(spawningPlants.isPreferredGrowPlace(new Vector2D(i, 2), map));

    }

}