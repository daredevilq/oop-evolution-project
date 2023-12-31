package project.simulation.maps.spawningPlants;

import project.RandomGen;
import project.Vector2D;
import project.simulation.maps.Boundary;
import project.simulation.maps.IWorldMap;
import project.simulation.worldelements.Grass;

import java.util.HashMap;
import java.util.Map;

public class SpawnPlantWithMovingJungle extends SpawnAllPlants{

    @Override
    public Vector2D spawnPlant(IWorldMap map, Map<Vector2D, Grass> mapPlant) {


        Vector2D randomPosition = RandomGen.randomFreePlace(map.getJungleBoundary().lowerLeftCorner(), map.getJungleBoundary().upperRightCorner());

        if (RandomGen.random()<=0.8){
            int randomGrassIndex = RandomGen.randInt(mapPlant.size()-1);
            //to jest chyba najglupszy sposob zeby to robic, ale tez najprostszy XDDD
            //jest problem bo mamy HashMape a nie tablice, wiec nie mozna wybrac losowego indeksu z tablicy
            //a zeby to zrobic trzeba by zrobic tablice z kluczami i wtedy losowac indeks z tej tablicy
            //ale to by bylo nieoptymalne bo trzeba by to robic za kazdym razem jak dodajemy nowa trawe

            for (Vector2D key : mapPlant.keySet()) {

                if (randomGrassIndex == 0) {
                    randomPosition = key;
                    break;
                }
                randomGrassIndex--;
            }
        }
        return RandomGen.randomAdjacentPosition(map, randomPosition);
    }
}
