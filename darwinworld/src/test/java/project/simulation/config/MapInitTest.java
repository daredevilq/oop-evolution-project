package project.simulation.config;

import org.junit.jupiter.api.Test;
import project.Vector2D;
import project.simulation.maps.Boundary;
import project.simulation.maps.animalBehavior.Default;
import project.simulation.maps.animalMutations.DefaultMutation;
import project.simulation.maps.breeding.ClassicBreed;
import project.simulation.maps.spawningPlants.SpawnPlantWithForestedEquators;
import project.simulation.worldelements.Animal;
import project.simulation.worldelements.Grass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MapInitTest {

    @Test
    public void testRandomlyPlaceAnimals() {
        //given
        int startAnimals = 25;
        Boundary boundary = new Boundary(new Vector2D(0, 0), new Vector2D(50, 50));
        MapSettings mapSettings = new MapSettings(50, 50, 20, 10, 1, 8, 5,startAnimals, 10, 10,5, 0.2);
        MapInit mapInit = new MapInit();

        //when
        List<Animal> animalList = mapInit.randomlyPlaceAnimals(mapSettings, boundary);

        //then
        assertFalse(animalList.isEmpty());
        assertEquals(mapSettings.startAnimals(), animalList.size());

        for (Animal animal : animalList) {
            assertTrue(animal.getPosition().precedes(boundary.upperRightCorner()));
            assertTrue(animal.getPosition().follows(boundary.lowerLeftCorner()));
        }
    }

    @Test
    public void testComputeFreePlacesForPlants() {
        //given
        MapInit mapInit = new MapInit();
        Map<Vector2D, Grass> mapPlants = new HashMap<>();
        int width = 10;
        int height = 10;
        mapPlants.put(new Vector2D(2, 2), new Grass(new Vector2D(2, 2), 5));
        mapPlants.put(new Vector2D(5, 5), new Grass(new Vector2D(5, 5), 3));

        //when
        Set<Vector2D> freePlaces = mapInit.computeFreePlacesForPlants(mapPlants, width, height);

        //then
        assertEquals(width * height - mapPlants.size(), freePlaces.size());
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Vector2D position = new Vector2D(i, j);
                if (!mapPlants.containsKey(position)) {
                    assertTrue(freePlaces.contains(position));
                }
            }
        }
    }

    @Test
    public void testRandomFreePlace() {
        //given
        MapInit mapInit = new MapInit();
        Vector2D lowerLeft = new Vector2D(0, 0);
        Vector2D upperRight = new Vector2D(50, 50);

        //when
        Vector2D randomPlace = mapInit.randomFreePlace(lowerLeft, upperRight);

        //then
        assertTrue(randomPlace.precedes(upperRight) && randomPlace.follows(lowerLeft));
    }
}