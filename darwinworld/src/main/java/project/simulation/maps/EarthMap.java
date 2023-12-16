package project.simulation.maps;

import project.Vector2D;
import project.simulation.config.MapSettings;
import project.simulation.fetures.AnimalBehavior;
import project.simulation.fetures.MapType;
import project.simulation.fetures.MutationType;
import project.simulation.fetures.VegetationDynamicsType;
import project.simulation.worldelements.Animal;
import project.simulation.worldelements.Grass;

import java.util.HashMap;
import java.util.Map;

public class EarthMap extends AbstractMap {


    public EarthMap(MapSettings mapSettings) {
        super(mapSettings);
    }

    //Metoda zwraca następną pozycję zwierzęcia po wykonaniu ruchu w przypadku mapy zakrzywionej horyzontalnie
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





}
