package project.simulation.config;

import project.MapDirection;
import project.RandomGen;
import project.Vector2D;
import project.simulation.maps.Boundary;
import project.simulation.worldelements.Animal;

import java.util.HashMap;
import java.util.Map;

public record MapInit (
    int initialAnimalsNumber
){
    public Map<Vector2D, Animal> randomlyPlaceAnimals(MapSettings mapSettings, Boundary boundary) {
        Map<Vector2D, Animal> mapAnimals = new HashMap<>();

        for (int i = 0; i < initialAnimalsNumber; i++) {
            Vector2D position = RandomGen.randomFreePlace(mapAnimals, boundary.lowerLeftCorner(), boundary.upperRightCorner());
            Animal animal = new Animal(mapSettings, position, MapDirection.NORTHEAST.rotate((int) (Math.random() * 8)), mapSettings.startEnergy(), RandomGen.randIntList(0, 7, mapSettings.genomeSize()));
            mapAnimals.put(position, animal);
        }
        return mapAnimals;
    }
}
