package project.simulation.maps.spawningPlants;

import project.RandomGen;
import project.Vector2D;
import project.simulation.maps.Boundary;
import project.simulation.maps.IWorldMap;
import project.simulation.worldelements.Grass;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SpawnPlantWithForestedEquators extends SpawnAllPlants {

    //moze sie wylosowac miejsce juz zajete przez trawe,
    //mozna to zmienic ale bedzie sporo problemow z tym zwiazanych
    @Override
    public Vector2D spawnPlant(IWorldMap map, Map<Vector2D, Grass> mapPlants) {
        if (RandomGen.random()<=0.8){
            return RandomGen.randomFreePlace(map.getJungleBoundary().lowerLeftCorner(), map.getJungleBoundary().upperRightCorner());
        } else {

            Vector2D randomPosition =  RandomGen.randomFreePlace(map.getBoundary().lowerLeftCorner(),map.getBoundary().upperRightCorner());
            while(randomPosition.follows(map.getJungleBoundary().lowerLeftCorner()) && randomPosition.precedes(map.getJungleBoundary().upperRightCorner())){
                randomPosition =  RandomGen.randomFreePlace(map.getBoundary().lowerLeftCorner(),map.getBoundary().upperRightCorner());
            }
            return randomPosition;
        }

    }


}
