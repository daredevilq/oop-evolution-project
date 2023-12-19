package project.simulation.maps.spawningPlants;

import project.RandomGen;
import project.Vector2D;
import project.simulation.maps.IWorldMap;

import java.util.Random;

public class SpawnPlantWithForestedEquators extends SpawnAllPlants {
    int fieldsNumberToPlant;

    public SpawnPlantWithForestedEquators(int fieldsNumberToPlant) {
        this.fieldsNumberToPlant = fieldsNumberToPlant;
    }

    @Override
    public Vector2D spawnPlant(IWorldMap map) {
        // tu jeszcze trzeba warunek że w jungli są jeszcze wolne pola
        if (new Random().nextBoolean()){ // inne warunki
            // losujemy z jungli
            return RandomGen.randomFreePlace(mapPlants, jungleLowerleft, jungleUpperRight);
        } else {
            // losujemy z całej mapy - możliwe że wylosuje się jungla
            return RandomGen.randomFreePlace(mapPlants, mapLowerLeft, mapUpperRight);
        }
    }
}
