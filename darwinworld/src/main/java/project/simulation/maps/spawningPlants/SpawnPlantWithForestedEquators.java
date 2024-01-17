package project.simulation.maps.spawningPlants;

import project.RandomGenerator;
import project.Vector2D;
import project.simulation.maps.Boundary;
import project.simulation.maps.IWorldMap;
import project.simulation.worldelements.Grass;

import java.util.List;
import java.util.Map;

public class SpawnPlantWithForestedEquators extends SpawnAllPlants {


    @Override
    public Vector2D spawnPlant(IWorldMap map, Map<Vector2D, Grass> mapPlants) {
        if (RandomGenerator.random()<=0.8){
            List<Vector2D> jungleAreas = map.getFreePlaces().stream()
                    .filter(entry -> isPreferredGrowPlace(entry, map))
                    .toList();

            if (!jungleAreas.isEmpty()) {
                return pickRandomVectorFromList(jungleAreas, map);
            }

        }


        List<Vector2D> nonJungleAreas = map.getFreePlaces().stream()
                .filter(entry -> !isPreferredGrowPlace(entry, map))
                .toList();

        if (!nonJungleAreas.isEmpty()) {
            return pickRandomVectorFromList(nonJungleAreas, map);
        }

        return new Vector2D(0,0); // losowy wektor
    }

    @Override
    public boolean isPreferredGrowPlace(Vector2D position, IWorldMap map) {
        Boundary jungleBoundary = map.getJungleBoundary();
        return position.precedes(jungleBoundary.upperRightCorner()) && position.follows(jungleBoundary.lowerLeftCorner());
    }
}
