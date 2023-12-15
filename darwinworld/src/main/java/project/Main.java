package project;

import project.simulation.Simulation;
import project.simulation.maps.EarthMap;
import project.simulation.worldelements.Animal;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Vector2D upperrigt = new Vector2D(5,5);
        Vector2D lowerleft = new Vector2D(0,0);
        EarthMap map = new EarthMap(10,10,10,1,10,upperrigt,lowerleft,new Vector2D(2,2));

       Animal animal = new Animal(new Vector2D(2,2), MapDirection.NORTH, 10, List.of(1,2,3,4,5,6,7,0));

        Simulation simulation = new Simulation(map);

    }
}