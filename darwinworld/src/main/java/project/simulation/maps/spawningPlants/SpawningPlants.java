package project.simulation.maps.spawningPlants;

import project.Vector2D;
import project.simulation.maps.IWorldMap;

public interface SpawningPlants {

    void spawnAllPlants(IWorldMap map);

    Vector2D spawnPlant(IWorldMap map);
}
