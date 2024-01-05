package project;

import project.simulation.Simulation;
import project.simulation.config.MapInit;
import project.simulation.config.MapSettings;
import project.simulation.config.Modifications;
import project.simulation.fetures.AnimalBehavior;
import project.simulation.fetures.MapType;
import project.simulation.fetures.MutationType;
import project.simulation.fetures.VegetationDynamicsType;
import project.simulation.maps.EarthMap;
import project.simulation.maps.IWorldMap;
import project.simulation.maps.animalBehavior.Default;
import project.simulation.maps.animalBehavior.Madness;
import project.simulation.maps.breeding.ClassicBreed;
import project.simulation.maps.spawningPlants.SpawnPlantWithForestedEquators;
import project.simulation.maps.spawningPlants.SpawnPlantWithMovingJungle;
import project.simulation.maps.spawningPlants.SpawningPlants;
import project.simulation.worldelements.Animal;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Modifications modifications = new Modifications(new SpawnPlantWithForestedEquators(),new Default(), new ClassicBreed());
        MapSettings mapSettings = new MapSettings(10, 10, 20, 2, 1, 7, 2, 1, 10, 0.2);

        IWorldMap map = new EarthMap(mapSettings,modifications,new MapInit());
        Simulation simulation = new Simulation(map, modifications);

        try {
            simulation.run();
        } catch (IllegalStateException e) {
            System.out.println(e);
            System.exit(1);
        }
    }
}
