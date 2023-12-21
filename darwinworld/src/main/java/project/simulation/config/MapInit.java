package project.simulation.config;

import project.MapDirection;
import project.RandomGen;
import project.Vector2D;
import project.simulation.maps.Boundary;
import project.simulation.worldelements.Animal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record MapInit (
    int initialAnimalsNumber
){
    public Map<Vector2D, List<Animal>> randomlyPlaceAnimals(MapSettings mapSettings, Boundary boundary) {
        Map<Vector2D, List<Animal>> mapAnimals = new HashMap<>();

        for (int i = 0; i < initialAnimalsNumber; i++) {
            Vector2D position = RandomGen.randomFreePlace(boundary.lowerLeftCorner(), boundary.upperRightCorner());
            Animal animal = new Animal(mapSettings, position, MapDirection.NORTHEAST.rotate((int) (Math.random() * 8)), mapSettings.startEnergy(), RandomGen.randIntList(0, 7, mapSettings.genomeSize()));

            List<Animal> animalsAtPosition = mapAnimals.computeIfAbsent(position, k -> new ArrayList<>());

            animalsAtPosition.add(animal);
        }
        return mapAnimals;
    }
}
