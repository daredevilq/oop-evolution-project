package project.model.simulation.maps;

import org.junit.jupiter.api.Test;
import project.model.simulation.worldelements.Vector2D;

import static org.junit.jupiter.api.Assertions.*;

class BoundaryTest {

    @Test
    public void testComputeJungleBoundsEvenMapHeight() {
        int mapHeight = 100;
        double jungleMapRatio = 0.4;
        Boundary mapBounds = new Boundary(new Vector2D(0, 0), new Vector2D(100, 100));

        Boundary jungleBounds = Boundary.computeJungleBounds(mapHeight, jungleMapRatio, mapBounds);

        assertEquals(new Vector2D(0, 30), jungleBounds.lowerLeftCorner());
        assertEquals(new Vector2D(100, 69), jungleBounds.upperRightCorner());
    }

    @Test
    public void testComputeJungleBoundsOddMapHeight() {
        int mapHeight = 199;
        double jungleMapRatio = 0.1;
        Boundary mapBounds = new Boundary(new Vector2D(0, 0), new Vector2D(200, 199));

        Boundary jungleBounds = Boundary.computeJungleBounds(mapHeight, jungleMapRatio, mapBounds);

        assertEquals(new Vector2D(0, 90), jungleBounds.lowerLeftCorner());
        assertEquals(new Vector2D(200, 108), jungleBounds.upperRightCorner());
    }

    @Test
    public void testComputeJungleBoundsRandomHeightAndWidth() {
        int mapHeight = 54;
        double jungleMapRatio = 0.1;
        Boundary mapBounds = new Boundary(new Vector2D(0, 0), new Vector2D(67, 54));

        Boundary jungleBounds = Boundary.computeJungleBounds(mapHeight, jungleMapRatio, mapBounds);

        assertEquals(new Vector2D(0, 25), jungleBounds.lowerLeftCorner());
        assertEquals(new Vector2D(67, 28), jungleBounds.upperRightCorner());
    }
}