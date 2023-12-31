package project.simulation;

import project.simulation.config.MapSettings;
import project.simulation.config.Modifications;
import project.simulation.maps.IWorldMap;
import project.simulation.worldelements.Animal;

import java.util.*;

public class Simulation implements Runnable{
    private List<Animal> deadAnimals = new ArrayList<>();
    private final IWorldMap map;

    private final Modifications modifications;
    private long dayNum = 0;
    private long aliveAnimalsCount=0;
    private long deadAnimalsCount = 0;

    public Simulation(IWorldMap map, Modifications modifications) {
        this.modifications = modifications;
        this.map = map;
    }


    public void run(){

        while (true){
            map.deleteDeadAnimals(this.deadAnimals);
            map.moveAnimals();
            map.eatPlants();
            map.breeding(modifications);
            map.spawnPlants(modifications);

            aliveAnimalsCount = map.getAnimalsList().size();
            deadAnimalsCount = deadAnimals.size();
            map.decreaceAllAnimalsEnergy();
            dayNum++;

            System.out.println(
                    "zwierzaki: "+map.getAnimalsList().toString());

            //tylko do debugowania
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}

