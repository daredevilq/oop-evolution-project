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
import project.simulation.worldelements.Grass;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SpawningPlantsTest {

    @Test
    public void testSpawnAllPlants1(){

        //given
        SpawningPlants spawningPlants = new SpawnPlantWithForestedEquators();
        int plantsToSpawnNumber = 55;
        Modifications modifications = new Modifications(spawningPlants,new Default(), new ClassicBreed(), new DefaultMutation());
        MapSettings mapSettings = new MapSettings(100, 100, 20, 10, 1, 8, 0,0, plantsToSpawnNumber, 10, 5,0.2);

        //when
        IWorldMap map = new EarthMap(mapSettings,modifications,new MapInit());

        //then
        int outcome = map.getMapPlants().size();
        assertEquals(55, outcome);


    }
    @Test
    public void testSpawnAllPlants2(){

        //given
        SpawningPlants spawningPlants = new SpawnPlantWithMovingJungle();
        int plantsToSpawnNumber = 31;
        Modifications modifications = new Modifications(spawningPlants,new Default(), new ClassicBreed(), new DefaultMutation());
        MapSettings mapSettings = new MapSettings(100, 100, 20, 10, 1, 8, 0,0, plantsToSpawnNumber, 10,5, 0.2);

        //when
        IWorldMap map = new EarthMap(mapSettings,modifications,new MapInit());

        //then
        int outcome = map.getMapPlants().size();
        assertEquals(31, outcome);

    }


    @Test
    public void testSpawnPlantWithForestedEquators(){
        //given
        SpawningPlants spawningPlants = new SpawnPlantWithForestedEquators();
        int plantsToSpawnNumber = 1000;
        Modifications modifications = new Modifications(spawningPlants,new Default(), new ClassicBreed(), new DefaultMutation());
        MapSettings mapSettings = new MapSettings(1000, 1000, 20, 10, 1, 8, 0,0, plantsToSpawnNumber, 10,5, 0.2);

        //when
        IWorldMap map = new EarthMap(mapSettings,modifications,new MapInit());
        System.out.println(map.getFreePlaces().size());

        //then
        //boolean outcome1 = (map.getMapPlants().size() == plantsToSpawnNumber);
        int insideJungle = 0;

        int shouldBeInsideJungle = (int) (plantsToSpawnNumber * 0.8);
        int error = (int) (plantsToSpawnNumber * 0.07);
        for(Vector2D vector: map.getMapPlants().keySet()){
            if(vector.follows(map.getJungleBoundary().lowerLeftCorner()) && vector.precedes(map.getJungleBoundary().upperRightCorner())){
                insideJungle++;
            }
        }
        System.out.println("plants spawned: " + map.getMapPlants().size());
        System.out.println("error: " + error);
        System.out.println("w jungli: " +insideJungle);
        System.out.println("powinno byc w jungli" + shouldBeInsideJungle);
        System.out.println("boundary jungli: " + map.getJungleBoundary());
        boolean outcome2 = (insideJungle >= shouldBeInsideJungle - error && insideJungle <= shouldBeInsideJungle + error);
        // nie wiem dlaczego generuje tylko ok 68,5% roslin w jungli
        //do naprawy
        //assertTrue(outcome1 && outcome2);
//        assertTrue(outcome1 && outcome2);
        //assertTrue(outcome1);
        assertTrue(outcome2);
    }

}
