package project.simulation.maps.spawningPlants;

import project.RandomGenerator;
import project.Vector2D;
import project.simulation.maps.IWorldMap;
import project.simulation.worldelements.Grass;

import java.util.List;
import java.util.Map;

public abstract class SpawnAllPlants implements SpawningPlants{

    @Override
    public void spawnAllPlants(IWorldMap map,Map <Vector2D, Grass> mapPlants, int plantsToSpawnNumber, int grassEnergy){

        for (int i = 0; i < plantsToSpawnNumber; i++) {

            Vector2D position = spawnPlant(map,mapPlants);
            Grass grass = new Grass(position, grassEnergy);
            mapPlants.put(position, grass);
        }
    }

    @Override
    public abstract Vector2D spawnPlant(IWorldMap map, Map<Vector2D, Grass> mapPlants);

    protected Vector2D pickRandomVectorFromList(List<Vector2D> list, IWorldMap map){
        Vector2D randomPosition = list.get(RandomGenerator.randInt(0, list.size() - 1));
        map.removeFreePlace(randomPosition);
        return randomPosition;
    }

//    protected Vector2D generateVectorInBoundaries(IWorldMap map, Boundary boundary){
//        Vector2D randomPosition = RandomGen.randomFreePlace(boundary.lowerLeftCorner(),boundary.upperRightCorner());
//        map.removeFreePlace(randomPosition);
//        return randomPosition;
//    }
}
