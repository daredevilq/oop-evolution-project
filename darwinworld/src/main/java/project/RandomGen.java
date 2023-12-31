package project;

import project.simulation.maps.Boundary;
import project.simulation.maps.IWorldMap;
import project.simulation.worldelements.IWorldElement;
import project.simulation.worldelements.WorldElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RandomGen {

    public static double random() {
        return Math.random();
    }

    public static int randInt(int maxVal) { // maxVal inclusive
        return (int)(Math.random() * (maxVal + 1));
    }

    public static int randInt(int minVal, int maxVal) { // maxVal inclusive
        return (int)(Math.random() * (maxVal - minVal + 1) + minVal);
    }

    public static List<Integer> randIntList(int minVal, int maxVal, int size) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(randInt(minVal, maxVal));
        }
        return list;
    }



    public static Vector2D randomFreePlace(Vector2D loweLeft, Vector2D upperRight){
        int minX = loweLeft.getX();
        int maxX = upperRight.getX();
        int minY = loweLeft.getY();
        int maxY = upperRight.getY();

        Vector2D randomPosition = Vector2D.randomVector(minX, maxX, minY, maxY);

//        while (mapElements.containsKey(randomPosition)) {
//            randomPosition = Vector2D.randomVector(minX, maxX, minY, maxY);
//        }

        return randomPosition;
    }

    public static Vector2D randomAdjacentPosition(IWorldMap map, Vector2D position){
        int[] digits = {-1,0,1};
        int randomX = digits[randInt(2)];
        int randomY = digits[randInt(2)];

        Vector2D randomPosition = new Vector2D(position.getX()+randomX, position.getY()+randomY);

        while (!map.canMoveTo(randomPosition)){
            randomX = digits[randInt(2)];
            randomY = digits[randInt(2)];
            randomPosition = new Vector2D(position.getX()+randomX, position.getY()+randomY);
        }
        return randomPosition;
    }

}
