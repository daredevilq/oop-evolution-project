package project.simulation.maps.spawningPlants;

import project.Vector2D;
import project.simulation.config.MapSettings;
import project.simulation.maps.Boundary;
import project.simulation.maps.IWorldMap;
import project.simulation.worldelements.Grass;

import java.util.HashMap;
import java.util.Map;

public abstract class SpawnAllPlants implements SpawningPlants{

    @Override
    public void spawnAllPlants(IWorldMap map,Map <Vector2D, Grass> mapPlants, int plantsToSpawnNumber, int grassEnergy){


        int fieldsNumber = (map.getBoundary().upperRightCorner().getX() - map.getBoundary().lowerLeftCorner().getX()) * (map.getBoundary().upperRightCorner().getY() - map.getBoundary().lowerLeftCorner().getY());
        int plantsToSpawn = Math.min(plantsToSpawnNumber, fieldsNumber - mapPlants.size());
        for (int i = 0; i < plantsToSpawn; i++) {

            Vector2D position = spawnPlant(map,mapPlants);
            Grass grass = new Grass(position, grassEnergy);
            mapPlants.put(position, grass);
        }
    }

    @Override
    public abstract Vector2D spawnPlant(IWorldMap map, Map<Vector2D, Grass> mapPlants);
}
