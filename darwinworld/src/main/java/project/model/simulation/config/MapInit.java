package project.model.simulation.config;

import project.model.simulation.worldelements.MapDirection;
import project.model.simulation.worldelements.Vector2D;
import project.model.simulation.maps.Boundary;
import project.model.simulation.worldelements.Animal;
import project.model.simulation.worldelements.Grass;

import java.util.*;

public class MapInit {

    public List<Animal> randomlyPlaceAnimals (MapSettings mapSettings, Boundary boundary) {
        List<Animal> animalList = new ArrayList<>();

        for (int i = 0; i < mapSettings.startAnimals(); i++) {
            Vector2D position = randomFreePlace(boundary.lowerLeftCorner(), boundary.upperRightCorner());
            Animal animal = new Animal(position, MapDirection.NORTHEAST.rotate((int) (Math.random() * 8)),
                    mapSettings.startEnergy(), RandomGenerator.randIntList(0, 7, mapSettings.genomeSize()));
            animalList.add(animal);
        }
        return animalList;
    }


    public Set<Vector2D> computeFreePlacesForPlants(Map<Vector2D, Grass> mapPlants, int width, int height){
        Set<Vector2D> freePlaces = new HashSet<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Vector2D position = new Vector2D(i,j);
                if (!mapPlants.containsKey(position)){
                    freePlaces.add(position);
                }
            }
        }
        return freePlaces;
    }

    public static Vector2D randomFreePlace(Vector2D loweLeft, Vector2D upperRight){
        int minX = loweLeft.getX();
        int maxX = upperRight.getX();
        int minY = loweLeft.getY();
        int maxY = upperRight.getY();
        return Vector2D.randomVector(minX, maxX, minY, maxY);
    }
}
