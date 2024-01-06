package project.simulation.config;

import project.simulation.maps.animalBehavior.AnimalBehavior;
import project.simulation.maps.spawningPlants.SpawningPlants;

public record MapSettings(
        int width,
        int height,
        int startEnergy,
        int moveEnergy,
        int grassEnergy,
        int genomeSize,
        int startAnimals,
        int startPlants,
        int breedEnergy,
//        int minMutations,
//        int maxMutations,
        int plantsPerDay,
        double JUNGLE_MAP_RATIO

        ) {
}