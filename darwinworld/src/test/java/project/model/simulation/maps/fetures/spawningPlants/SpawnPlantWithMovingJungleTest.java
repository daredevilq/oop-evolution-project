package project.model.simulation.maps.fetures.spawningPlants;

import org.junit.jupiter.api.Test;
import project.model.simulation.worldelements.MapDirection;
import project.model.simulation.worldelements.Vector2D;
import project.model.simulation.config.MapInit;
import project.model.simulation.config.MapSettings;
import project.model.simulation.config.Modifications;
import project.model.simulation.maps.EarthMap;
import project.model.simulation.maps.IWorldMap;
import project.model.simulation.fetures.animalBehavior.Default;
import project.model.simulation.fetures.animalMutations.DefaultMutation;
import project.model.simulation.fetures.breeding.ClassicBreed;
import project.model.simulation.fetures.spawningPlants.SpawnPlantWithMovingJungle;
import project.model.simulation.fetures.spawningPlants.SpawningPlants;
import project.model.simulation.worldelements.Grass;

import static org.junit.jupiter.api.Assertions.*;

public class SpawnPlantWithMovingJungleTest {

    @Test
    public void testSpawnPlant() {
        //given
        SpawningPlants spawningPlants = new SpawnPlantWithMovingJungle();
        int plantsToSpawn = 100;
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
    public void testPreferredPlacesAroundPlant() {
        SpawningPlants spawningPlants = new SpawnPlantWithMovingJungle();
        Modifications modifications = new Modifications(spawningPlants, new Default(), new ClassicBreed(),
                new DefaultMutation());
        MapSettings mapSettings = new MapSettings(5, 5, 20, 10, 1,
                8, 0,0, 0, 10,1, 0.2);

        Vector2D grassPosition = new Vector2D(1, 1);
        IWorldMap map = new EarthMap(mapSettings,modifications,new MapInit());
        map.addPlant(new Grass(grassPosition, 8));

        assertEquals(1, map.getMapPlants().size());
        for (MapDirection direction : MapDirection.values())
            assertTrue(spawningPlants.isPreferredGrowPlace(grassPosition.add(direction.toUnitVector()), map));

    }


    @Test
    public void testIsPreferredPlaceWhilePlantThere() {
        SpawningPlants spawningPlants = new SpawnPlantWithMovingJungle();
        Modifications modifications = new Modifications(spawningPlants, new Default(), new ClassicBreed(),
                new DefaultMutation());
        MapSettings mapSettings = new MapSettings(5, 5, 20, 10, 1,
                8, 0,0, 0, 10,1, 0.2);

        Vector2D grassPosition1 = new Vector2D(1, 1);
        Vector2D grassPosition2 = new Vector2D(1, 2);
        IWorldMap map = new EarthMap(mapSettings,modifications,new MapInit());
        map.addPlant(new Grass(grassPosition1, 8));
        map.addPlant(new Grass(grassPosition2, 8));

        assertEquals(2, map.getMapPlants().size());
        assertFalse(spawningPlants.isPreferredGrowPlace(grassPosition1, map));
        assertFalse(spawningPlants.isPreferredGrowPlace(grassPosition1, map));
    }

}