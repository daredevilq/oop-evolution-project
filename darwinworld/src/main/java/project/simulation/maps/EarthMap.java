package project.simulation.maps;

import project.MapDirection;
import project.Vector2D;
import project.simulation.worldelements.Animal;
import project.simulation.worldelements.Grass;

import java.util.HashMap;
import java.util.Map;

public class EarthMap extends AbstractMap {

    private final int width;
    private final int height;
    private final int grassEnergy;
    private final int moveEnergy;
    private final int startEnergy;

    private final int fieldsNumber;

    private Map<Vector2D, Animal> mapAnimals = new HashMap<>();
    private Map<Vector2D, Grass> mapGrass = new HashMap<>();

    private final Vector2D mapLowerLeft = new Vector2D(0, 0);
    private final Vector2D mapUpperRight;
    private final Vector2D jungleLowerleft;
    private final Vector2D jungleUpperRight;

    public EarthMap(int width, int height, int grassEnergy, int moveEnergy, int startEnergy, Vector2D mapUpperRight, Vector2D jungleLowerleft, Vector2D jungleUpperRight) {
        this.width = width;
        this.height = height;
        this.grassEnergy = grassEnergy;
        this.moveEnergy = moveEnergy;
        this.startEnergy = startEnergy;
        this.mapUpperRight = mapUpperRight;
        this.jungleLowerleft = jungleLowerleft;
        this.jungleUpperRight = jungleUpperRight;

        this.fieldsNumber = (mapUpperRight.getX() - mapLowerLeft.getX() + 1)
                * (mapUpperRight.getY() - mapLowerLeft.getY()) + 1;

    }


    public Vector2D getNextPosition(Vector2D currPosition, Vector2D moveVector) {

        Vector2D newPosition = currPosition.add(moveVector);

        int x = newPosition.getX();
        int y = newPosition.getY();

        int minX = mapLowerLeft.getX();
        int maxX = mapUpperRight.getX();
        int minY = mapLowerLeft.getY();
        int maxY = mapUpperRight.getY();

        int mapWidth = maxX - minX + 1;
        int mapHeight = maxY - minY + 1;

        //Mapa jest zakrzywiona horyzontalnie
        if      (x > maxX) x %= mapWidth;
        else if (x < minX) x += mapWidth;
        if      (y > maxY) y = currPosition.getY();
        else if (y < minY) y = currPosition.getY();

        return new Vector2D(x, y);
    }


    public void move(){ // for each animal
        for (Animal animal : mapAnimals.values()) {
            animal.move(this);
            System.out.println(animal.getPosition());
        }
    }

    public void eat(){

    }


}
