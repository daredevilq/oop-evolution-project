package project.model.simulation.maps;
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
import project.model.simulation.fetures.spawningPlants.SpawnPlantWithForestedEquators;
import project.model.simulation.fetures.spawningPlants.SpawnPlantWithMovingJungle;
import project.model.simulation.worldelements.Animal;
import project.model.simulation.worldelements.Grass;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AbstractMapTest {

    @Test
    public void testEatPlant(){
        //given
        int grassEnergy = 3;
        int grassNumber = 10;
        Modifications modifications = new Modifications(new SpawnPlantWithForestedEquators(),new Default(), new ClassicBreed(), new DefaultMutation());
        MapSettings mapSettings = new MapSettings(10, 10, grassNumber, 10, grassEnergy, 8, 0,10, 10, 10,5, 0.2);
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
    public void testCheckEatingAllPlants(){
        //given
        int grassEnergy = 3;
        int grassNumber = 10;
        Modifications modifications = new Modifications(new SpawnPlantWithForestedEquators(),new Default(), new ClassicBreed(), new DefaultMutation());
        MapSettings mapSettings = new MapSettings(10, 10, 10, 10, 0, grassEnergy, 3,0, grassNumber, 10,5, 0.2);
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

    @Test
    public void testEatingPlantByAnimalWithHigherEnergy(){
        //given
        int grassEnergy = 3;
        int grassNumber = 1;
        Modifications modifications = new Modifications(new SpawnPlantWithForestedEquators(),new Default(), new ClassicBreed(), new DefaultMutation());
        MapSettings mapSettings = new MapSettings(10, 10, 0, 10, 0, grassEnergy, 0,10, grassNumber, 10,5, 0.2);
        IWorldMap map = new EarthMap(mapSettings,modifications,new MapInit());

        Vector2D grassPosition = map.getMapPlants().keySet().iterator().next();

        Animal strongerAnimal = new Animal(grassPosition, MapDirection.NORTH, 20, List.of(1,2,3,4,5,6,7,0));
        Animal weakerAnimal = new Animal(grassPosition, MapDirection.NORTH, 10, List.of(1,2,3,4,5,6,7,0));

        map.addAnimal(strongerAnimal);
        map.addAnimal(weakerAnimal);

        //when
        map.eatPlants();

        //then
        assertEquals(23,  strongerAnimal.getEnergy());
        assertEquals(10,  weakerAnimal.getEnergy());
        assertEquals(1, strongerAnimal.getEatenPlants());
        assertEquals(0, weakerAnimal.getEatenPlants());
    }

    @Test
    public void testEatingPlantByOlderAnimal(){
        //given
        int grassEnergy = 3;
        int grassNumber = 1;
        Modifications modifications = new Modifications(new SpawnPlantWithForestedEquators(),new Default(), new ClassicBreed(), new DefaultMutation());
        MapSettings mapSettings = new MapSettings(10, 10, grassNumber, 10, grassEnergy, 3, 0,0, 10, 10,5, 0.2);
        IWorldMap map = new EarthMap(mapSettings,modifications,new MapInit());

        Vector2D grassPosition = map.getMapPlants().keySet().iterator().next();

        Animal strongerAnimal = new Animal(grassPosition, MapDirection.NORTH, 11, List.of(1,2,3,4,5,6,7,0));
        Animal weakerAnimal = new Animal(grassPosition, MapDirection.NORTH, 10, List.of(1,2,3,4,5,6,7,0));

        strongerAnimal.updateDailyStatsOnAnimal();

        map.addAnimal(strongerAnimal);
        map.addAnimal(weakerAnimal);

        //when
        map.eatPlants();

        //then
        assertEquals(10,  weakerAnimal.getEnergy());
        assertEquals(13, strongerAnimal.getEnergy());
        assertEquals(1, strongerAnimal.getEatenPlants());
        assertEquals(0, weakerAnimal.getEatenPlants());
        assertEquals(9 , map.getMapPlants().size());
    }

    @Test
    public void testObjectAt() {
        Modifications modifications = new Modifications(new SpawnPlantWithForestedEquators(),new Default(), new ClassicBreed(), new DefaultMutation());
        MapSettings mapSettings = new MapSettings(10, 10, 0, 10, 10, 3, 0,0, 2, 10,5, 0.2);
        IWorldMap map = new EarthMap(mapSettings,modifications,new MapInit());

        List<Grass> grasses = new ArrayList<>(map.getMapPlants().values());
        Grass grass1 = grasses.get(0);
        Grass grass2 = grasses.get(1);

        Animal animal = new Animal(grass1.getPosition(), MapDirection.NORTH, 11, List.of(1,2,3,4,5,6,7,0));
        map.addAnimal(animal);

        assertEquals(grass2, map.objectAt(grass2.getPosition()));
        assertEquals(animal, map.objectAt(grass1.getPosition()));
    }

    @Test
    public void testIsOccupied() {
        Modifications modifications = new Modifications(new SpawnPlantWithForestedEquators(),new Default(), new ClassicBreed(), new DefaultMutation());
        MapSettings mapSettings = new MapSettings(10, 10, 0, 10, 10, 3, 0,0, 2, 10,5, 0.2);
        IWorldMap map = new EarthMap(mapSettings,modifications,new MapInit());

        Animal animal = new Animal(new Vector2D(1, 1), MapDirection.NORTH, 11, List.of(1,2,3,4,5,6,7,0));
        map.addAnimal(animal);

        assertTrue(map.isOccupied(new Vector2D(1, 1)));
        assertFalse(map.isOccupied(new Vector2D(0, 0)));
    }

    @Test
    public void testDeleteDeadAnimals() {
        Modifications modifications = new Modifications(new SpawnPlantWithForestedEquators(),new Default(), new ClassicBreed(), new DefaultMutation());
        MapSettings mapSettings = new MapSettings(10, 10, 0, 10, 10, 3, 0,0, 2, 10,5, 0.2);
        IWorldMap map = new EarthMap(mapSettings,modifications,new MapInit());

        Animal animal1 = new Animal(new Vector2D(1, 1), MapDirection.NORTH, 11, List.of(1,2,3,4,5,6,7,0));
        Animal animal2 = new Animal(new Vector2D(1, 1), MapDirection.NORTH, -1, List.of(1,2,3,4,5,6,7,0));
        Animal animal3 = new Animal(new Vector2D(1, 1), MapDirection.NORTH, 0, List.of(1,2,3,4,5,6,7,0));
        map.addAnimal(animal1);
        map.addAnimal(animal2);
        map.addAnimal(animal3);

        map.deleteDeadAnimals();

        assertEquals(1, map.getAnimalsList().size());
        assertEquals(2, map.getDeadAnimals().size());
    }

    @Test
    public void testMoveAnimals() {
        Modifications modifications = new Modifications(new SpawnPlantWithForestedEquators(),new Default(), new ClassicBreed(), new DefaultMutation());
        MapSettings mapSettings = new MapSettings(10, 10, 0, 10, 10, 3, 0,0, 2, 10,5, 0.2);
        IWorldMap map = new EarthMap(mapSettings,modifications,new MapInit());

        Animal animal1 = new Animal(new Vector2D(1, 1), MapDirection.NORTH, 11, List.of(1,2,3,4,5,6,7,0));
        Animal animal2 = new Animal(new Vector2D(1, 1), MapDirection.NORTH, 9, List.of(1,2,3,4,5,6,7,0));
        Animal animal3 = new Animal(new Vector2D(1, 1), MapDirection.NORTH, 7, List.of(1,2,3,4,5,6,7,0));
        map.addAnimal(animal1);
        map.addAnimal(animal2);
        map.addAnimal(animal3);

        map.moveAnimals();

        assertEquals(new Vector2D(2,2), animal1.getPosition());
        assertEquals(new Vector2D(1, 1), animal2.getPosition());
        assertEquals(new Vector2D(1, 1), animal3.getPosition());
        assertEquals(1, animal1.getEnergy());
    }

    @Test
    public void testFreePlaces() {
        Modifications modifications = new Modifications(new SpawnPlantWithMovingJungle(),new Default(), new ClassicBreed(), new DefaultMutation());
        MapSettings mapSettings = new MapSettings(5, 5, 0, 10, 10, 3, 0,0, 10, 10,5, 0.2);
        IWorldMap map = new EarthMap(mapSettings,modifications,new MapInit());

        assertEquals(15, map.freePlacesOnMap());
        map.spawnPlants();
        assertEquals(10, map.freePlacesOnMap());
    }
}
