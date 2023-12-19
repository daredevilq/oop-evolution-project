package project.simulation.maps.spawningPlants;

import project.Vector2D;
import project.simulation.maps.Boundary;
import project.simulation.maps.IWorldMap;
import project.simulation.worldelements.Grass;

public abstract class SpawnAllPlants implements SpawningPlants{

    @Override
    public void spawnAllPlants(IWorldMap map){
        Boundary boundary = map.getBoundary();
        int plantsCount = 10;

        int fieldsNumber = (boundary.upperRightCorner().getX() - boundary.lowerLeftCorner().getX()) * (boundary.upperRightCorner().getY() - boundary.lowerLeftCorner().getY());

        for (int i = 0; i < (int) Math.sqrt(fieldsNumber) & plantsCount < fieldsNumber; i++) {

            Vector2D position = spawnPlant(map);

            Grass grass = new Grass(position, mapSettings.grassEnergy());
            mapPlants.put(position, grass);
            plantsCount += 1;
        }
        
    }

    @Override
    public abstract Vector2D spawnPlant(IWorldMap map);
}
