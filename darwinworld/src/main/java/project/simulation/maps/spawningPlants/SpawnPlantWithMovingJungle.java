package project.simulation.maps.spawningPlants;

import project.MapDirection;
import project.RandomGenerator;
import project.Vector2D;
import project.simulation.maps.IWorldMap;
import project.simulation.worldelements.Grass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpawnPlantWithMovingJungle extends SpawnAllPlants {
    @Override
    public Vector2D spawnPlant(IWorldMap map, Map<Vector2D, Grass> mapPlant) {
        if (RandomGenerator.random() <= 0.8) {
            List<Vector2D> adjacentFreeAreas = map.getFreePlaces().stream()
                    .filter(entry -> isPreferredGrowPlace(entry, map))
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
        return new Vector2D(0, 0);
    }

    @Override
    public boolean isPreferredGrowPlace(Vector2D position, IWorldMap map) {
        Map<Vector2D, Grass> mapPlants = map.getMapPlants();
        if (mapPlants.containsKey(position))
            return false;

        for (MapDirection direction : MapDirection.values()) {
            Vector2D adjacentPosition = position.add(direction.toUnitVector());
            if (map.getMapPlants().containsKey(adjacentPosition)) {
                return true;
            }
        }
        return false;
    }


}
