package project.simulation.maps.spawningPlants;

import project.Vector2D;
import project.simulation.config.MapSettings;
import project.simulation.maps.Boundary;
import project.simulation.maps.IWorldMap;
import project.simulation.worldelements.Grass;

import java.util.HashMap;
import java.util.Map;

public interface SpawningPlants {

    void spawnAllPlants(IWorldMap map,Map<Vector2D, Grass> mapPlants, MapSettings mapSettings);

    Vector2D spawnPlant(IWorldMap map, Map <Vector2D, Grass> mapPlants) ;
}
