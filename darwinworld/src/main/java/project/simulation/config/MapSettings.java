package project.simulation.config;

import project.simulation.maps.animalBehavior.AnimalBehavior;
import project.simulation.maps.spawningPlants.SpawningPlants;

public record MapSettings(
        int width,
        int height,
        int startEnergy,
        int moveEnergy,
        int grassEnergy,
        int genomeSize
//        MapType mapType,
//        AnimalBehavior animalBehavior,
//        VegetationDynamicsType vegetationDynamicsType,
//        MutationType mutationType



        ) {
}