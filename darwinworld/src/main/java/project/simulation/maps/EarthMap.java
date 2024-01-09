package project.simulation.maps;

import project.Vector2D;
import project.simulation.config.MapInit;
import project.simulation.config.MapSettings;
import project.simulation.config.Modifications;

public class EarthMap extends AbstractMap {


    public EarthMap(MapSettings mapSettings, Modifications modifications, MapInit mapInitialize) {
        super(mapSettings, modifications, mapInitialize);
    }

    @Override
    public Vector2D getNextPosition(Vector2D newPosition) {

        int x = newPosition.getX();
        int y = newPosition.getY();

        int minX = getBoundary().lowerLeftCorner().getX();
        int maxX = getBoundary().upperRightCorner().getX();
        int minY = getBoundary().lowerLeftCorner().getY();
        int maxY = getBoundary().upperRightCorner().getY();

        int mapWidth = maxX - minX + 1;
        int mapHeight = maxY - minY + 1;

        //Mapa jest zakrzywiona horyzontalnie
        if (x > maxX)
            x %= mapWidth;
        else if (x < minX)
            x += mapWidth;

        return new Vector2D(x, y);
    }

    @Override
    public boolean canMoveTo(Vector2D position){
//        return position.follows(this.getBoundary().lowerLeftCorner()) && position.precedes(this.getBoundary().upperRightCorner());
        int minY = getBoundary().lowerLeftCorner().getY();
        int maxY = getBoundary().upperRightCorner().getY();

        int positionY = position.getY();
        return positionY <= maxY || position.getY() >= minY;
    }
}
