package project.simulation.maps.spawningPlants;

import project.RandomGen;
import project.Vector2D;
import project.simulation.fetures.MapAreaType;
import project.simulation.maps.Boundary;
import project.simulation.maps.IWorldMap;
import project.simulation.worldelements.Grass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class SpawnPlantWithForestedEquators extends SpawnAllPlants {


    @Override
    public Vector2D spawnPlant(IWorldMap map, Map<Vector2D, Grass> mapPlants) {
        if (RandomGen.random()<=0.8){
            List<Vector2D> jungleAreas = map.getFreePlaces().stream()
                    .filter(map::isJungleArea)
                    .toList();

            if (!jungleAreas.isEmpty()) {
                return pickRandomVectorFromList(jungleAreas, map);
            }

        }


            List<Vector2D> nonJungleAreas = map.getFreePlaces().stream()
                    .filter(entry -> !map.isJungleArea(entry))
                    .toList();

            if (!nonJungleAreas.isEmpty()) {
                return pickRandomVectorFromList(nonJungleAreas, map);
            }


            else{ //!!!!!!!!!!!!!!!!!!!!!!
                return generateVectorInBoundaries(map, map.getBoundary());
            }

    }


//    private boolean isJungleArea(Vector2D position, Boundary jungleBoundary) {
//        return position.precedes(jungleBoundary.upperRightCorner()) && position.follows(jungleBoundary.lowerLeftCorner());
//    }


}
