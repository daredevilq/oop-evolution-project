package project;

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



    public static <T extends IWorldElement> Vector2D randomFreePlace(Map<Vector2D, T> mapElements, Vector2D loweLeft, Vector2D upperRight){
        int minX = loweLeft.getX();
        int maxX = upperRight.getX();
        int minY = loweLeft.getY();
        int maxY = upperRight.getY();

        Vector2D randomPosition = Vector2D.randomVector(minX, maxX, minY, maxY);

        while (mapElements.containsKey(randomPosition)) {
            randomPosition = Vector2D.randomVector(minX, maxX, minY, maxY);
        }

        return randomPosition;
    }

}
