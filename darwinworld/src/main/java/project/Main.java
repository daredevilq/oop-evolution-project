package project;

import project.simulation.Simulation;
import project.simulation.config.MapInit;
import project.simulation.config.MapSettings;
import project.simulation.config.Modifications;
import project.simulation.maps.EarthMap;
import project.simulation.maps.IWorldMap;
import project.simulation.maps.animalBehavior.Default;
import project.simulation.maps.animalMutations.DefaultMutation;
import project.simulation.maps.breeding.ClassicBreed;
import project.simulation.maps.spawningPlants.SpawnPlantWithMovingJungle;

public class Main {
    public static void main(String[] args) {

        Modifications modifications = new Modifications(new SpawnPlantWithMovingJungle(),new Default(), new ClassicBreed(), new DefaultMutation());
        MapSettings mapSettings = new MapSettings(20, 20, 2000, 2, 1, 7, 5, 1,1, 5, 5,0.2);

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
