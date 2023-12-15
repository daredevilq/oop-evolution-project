package project.simulation;

import project.simulation.maps.EarthMap;
import project.simulation.worldelements.Animal;

import java.util.List;

public class Simulation {
//    private final List<Animal> animals; // linked list
    private final EarthMap map;

    public Simulation(EarthMap map) {
//        this.animals = animals;
        this.map = map;
    }


    public void run(){

        while (true){
//            for (int i = 0; i < animals) { //usuniecie martwych zwierzat
//                if (animal.getenergy = 0){
//
//                }
//            }
            //map.remove()
            //map.move()
            //map.reproduce
            //map ..
            map.move();

        }
    }
}
