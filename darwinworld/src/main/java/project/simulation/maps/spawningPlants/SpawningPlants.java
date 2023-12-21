package project.simulation.maps.spawningPlants;

import project.Vector2D;
import project.simulation.maps.Boundary;
import project.simulation.maps.IWorldMap;
import project.simulation.worldelements.Grass;

import java.util.HashMap;

public interface SpawningPlants {

    void spawnAllPlants(HashMap<Vector2D, Grass> mapPlants, Boundary boundary, int grassEnergy);

    Vector2D spawnPlant(HashMap <Vector2D, Grass> mapPlants, Boundary boundary);
}
