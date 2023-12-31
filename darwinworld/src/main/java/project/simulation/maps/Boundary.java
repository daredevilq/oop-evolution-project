package project.simulation.maps;

import project.Vector2D;
import project.simulation.config.MapSettings;

public record Boundary(Vector2D lowerLeftCorner, Vector2D upperRightCorner) {
    public static Boundary computeJungleBounds(MapSettings mapSettings, Boundary mapBounds){
        int jungleHeight = (int) (mapSettings.width() * mapSettings.JUNGLE_MAP_RATIO());

        if (jungleHeight % 2 != 0)
            jungleHeight += 1;

        int halfJungleHeight = jungleHeight / 2;
        int halfMapWidth = (int)mapSettings.width() / 2;

        return new Boundary(
                new Vector2D(mapBounds.lowerLeftCorner().getX(), halfMapWidth - halfJungleHeight),
                new Vector2D(mapBounds.upperRightCorner().getX(), halfMapWidth + halfJungleHeight)
        );
    }
}