package project.simulation.config;

import project.MapDirection;
import project.RandomGen;
import project.Vector2D;
import project.simulation.maps.Boundary;
import project.simulation.worldelements.Animal;
import project.simulation.worldelements.Grass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record MapInit (

){

    public List<Animal> randomlyPlaceAnimals (MapSettings mapSettings, Boundary boundary) {

        List<Animal> animalList = new ArrayList<>();

        //umieszczenie losowo poczatkowej ilosci zwierzat
        for (int i = 0; i < mapSettings.startAnimals(); i++) {
            Vector2D position = RandomGen.randomFreePlace(boundary.lowerLeftCorner(), boundary.upperRightCorner());
            Animal animal = new Animal(position, MapDirection.NORTHEAST.rotate((int) (Math.random() * 8)), mapSettings.startEnergy(), RandomGen.randIntList(0, 7, mapSettings.genomeSize()));
            animalList.add(animal);
        }
        return animalList;
    }

}
