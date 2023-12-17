package project;

import project.simulation.Simulation;
import project.simulation.config.MapSettings;
import project.simulation.fetures.AnimalBehavior;
import project.simulation.fetures.MapType;
import project.simulation.fetures.MutationType;
import project.simulation.fetures.VegetationDynamicsType;
import project.simulation.maps.EarthMap;
import project.simulation.worldelements.Animal;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //testowa konfiguracja dla 10dni, na razie tutaj potem sie to wszsytko wrzuci do simulation
        MapSettings mapSettings = new MapSettings(10,10,100,5,5,1
                ,10, MapType.EARTH, AnimalBehavior.PREDESTINATION, VegetationDynamicsType.EQUATOR,MutationType.RANDOMNESS );

        EarthMap map = new EarthMap(mapSettings);

        Simulation simulation = new Simulation(map);

        simulation.run();

    }
}
