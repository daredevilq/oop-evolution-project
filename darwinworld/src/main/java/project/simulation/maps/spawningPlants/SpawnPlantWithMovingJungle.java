package project.simulation.maps.spawningPlants;

import project.MapDirection;
import project.RandomGen;
import project.Vector2D;
import project.simulation.fetures.MapAreaType;
import project.simulation.maps.Boundary;
import project.simulation.maps.IWorldMap;
import project.simulation.worldelements.Grass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpawnPlantWithMovingJungle extends SpawnAllPlants{

    @Override
    public Vector2D spawnPlant(IWorldMap map, Map<Vector2D, Grass> mapPlant) {

        if (RandomGen.random()<=0.8){
            List<Vector2D> adjacentFreeAreas = map.getFreePlaces().stream()
                    .filter(entry -> adjacentToPlant(entry, mapPlant))
                    .toList();

            if (!adjacentFreeAreas.isEmpty()) {
                return pickRandomVectorFromList(adjacentFreeAreas, map);
            }
        }
        List<Vector2D> freeAreas = map.getFreePlaces().stream()
                .toList();

        if (!freeAreas.isEmpty()) {
            return pickRandomVectorFromList(freeAreas, map);
        }

        // !!!!!!!!!!!!!!!!!!!!!!!!
//        return new Vector2D(0,0);
        return generateVectorInBoundaries(map, map.getBoundary());
    }

    private boolean adjacentToPlant(Vector2D position,  Map<Vector2D, Grass> mapPlant){
        for (MapDirection direction : MapDirection.values()) {
            Vector2D adjacentPosition = position.add(direction.toUnitVector());
            if (mapPlant.containsKey(adjacentPosition)) {
                return true;
            }
        }
        return false;
    }


}
