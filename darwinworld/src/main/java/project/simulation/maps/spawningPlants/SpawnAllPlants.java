package project.simulation.maps.spawningPlants;

import project.Vector2D;
import project.simulation.maps.Boundary;
import project.simulation.maps.IWorldMap;
import project.simulation.worldelements.Grass;

import java.util.HashMap;

public abstract class SpawnAllPlants implements SpawningPlants{

    @Override
    public void spawnAllPlants(HashMap <Vector2D, Grass> mapPlants, Boundary boundary, int grassEnergy){
        int plantsCount = 10;

        int fieldsNumber = (boundary.upperRightCorner().getX() - boundary.lowerLeftCorner().getX()) * (boundary.upperRightCorner().getY() - boundary.lowerLeftCorner().getY());

        for (int i = 0; i < (int) Math.sqrt(fieldsNumber) & plantsCount < fieldsNumber; i++) {

            Vector2D position = spawnPlant(mapPlants, boundary);

            if (!mapPlants.containsKey(position)) {
                Grass grass = new Grass(position, grassEnergy);
                mapPlants.put(position, grass);
                plantsCount += 1;
            }
        }
    }

    @Override
    public abstract Vector2D spawnPlant(mapPlants, boundary);
}
