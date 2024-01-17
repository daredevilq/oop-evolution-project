package project.simulation.maps.spawningPlants;

import org.junit.jupiter.api.Test;
import project.Vector2D;
import project.simulation.config.MapInit;
import project.simulation.config.MapSettings;
import project.simulation.config.Modifications;
import project.simulation.maps.EarthMap;
import project.simulation.maps.IWorldMap;
import project.simulation.maps.animalBehavior.Default;
import project.simulation.maps.animalMutations.DefaultMutation;
import project.simulation.maps.breeding.ClassicBreed;

import static org.junit.jupiter.api.Assertions.*;

class SpawnPlantWithForestedEquatorsTest {
    @Test
    public void testSpawnPlantWithForestedEquators(){
        //given
        SpawningPlants spawningPlants = new SpawnPlantWithForestedEquators();
        int plantsToSpawnNumber = 500;
        Modifications modifications = new Modifications(spawningPlants,new Default(), new ClassicBreed(), new DefaultMutation());
        MapSettings mapSettings = new MapSettings(500, 500, 20, 10, 1, 8, 0,0, plantsToSpawnNumber, 10,5, 0.2);

        //when
        IWorldMap map = new EarthMap(mapSettings,modifications,new MapInit());
        int insideJungle = 0;

        for(Vector2D vector: map.getMapPlants().keySet()){
            if(vector.follows(map.getJungleBoundary().lowerLeftCorner()) && vector.precedes(map.getJungleBoundary().upperRightCorner())){
                insideJungle++;
            }
        }
        int shouldBeInsideJungle = (int) (plantsToSpawnNumber * 0.8);
        double difference = (int) (plantsToSpawnNumber * 0.05); // ustawia, różnice na max 5%

        //then
        assertEquals(plantsToSpawnNumber, map.getMapPlants().size());
        assertTrue(insideJungle >= shouldBeInsideJungle - difference &&
                insideJungle <= shouldBeInsideJungle + difference);

    }

}