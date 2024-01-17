package project.model.simulation.fetures.spawningPlants;

import project.model.simulation.worldelements.Vector2D;
import project.model.simulation.maps.IWorldMap;
import project.model.simulation.worldelements.Grass;

import java.util.Map;

public interface SpawningPlants {

    void spawnAllPlants(IWorldMap map,Map <Vector2D, Grass> mapPlants, int plantsToSpawnNumber, int grassEnergy);

    Vector2D spawnPlant(IWorldMap map, Map <Vector2D, Grass> mapPlants) ;

    boolean isPreferredGrowPlace(Vector2D position, IWorldMap map);
}
