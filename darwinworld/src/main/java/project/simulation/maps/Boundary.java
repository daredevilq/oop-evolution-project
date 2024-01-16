package project.simulation.maps;

import project.Vector2D;
import project.simulation.config.MapSettings;

import static java.lang.Math.max;

public record Boundary(Vector2D lowerLeftCorner, Vector2D upperRightCorner) {
    public static Boundary computeJungleBounds(int mapHeight, double jungleMapRatio, Boundary mapBounds){
        int jungleHeight = (int) (mapHeight * jungleMapRatio);

        if (mapHeight % 2 == 0 && jungleHeight % 2 != 0) {
            jungleHeight -= 1;
        } else if (mapHeight % 2 != 0 && jungleHeight % 2 == 0) {
            jungleHeight += 1;
        }

        int halfJungleHeight = jungleHeight / 2;
        int halfMapHeight = mapHeight / 2;

        halfJungleHeight = max(1,halfJungleHeight);


        if (mapHeight % 2 == 1)
            return new Boundary(
                    new Vector2D(mapBounds.lowerLeftCorner().getX(), halfMapHeight - halfJungleHeight),
                    new Vector2D(mapBounds.upperRightCorner().getX(), halfMapHeight + halfJungleHeight));
        else
            return new Boundary(
                    new Vector2D(mapBounds.lowerLeftCorner().getX(), halfMapHeight - halfJungleHeight),
                    new Vector2D(mapBounds.upperRightCorner().getX(), halfMapHeight + halfJungleHeight-1));
    }
}