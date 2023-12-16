package project.simulation.config;

import project.simulation.fetures.AnimalBehavior;
import project.simulation.fetures.MapType;
import project.simulation.fetures.MutationType;
import project.simulation.fetures.VegetationDynamicsType;

public record MapSettings(
        int width,
        int height,
        int startEnergy,
        int moveEnergy,
        int grassEnergy,
        int initialAnimalsNumber,
        int genomeSize,
        MapType mapType,
        AnimalBehavior animalBehavior,
        VegetationDynamicsType vegetationDynamicsType,
        MutationType mutationType
        ) {
}