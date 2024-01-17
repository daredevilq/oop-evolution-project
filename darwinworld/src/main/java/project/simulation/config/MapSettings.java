package project.simulation.config;

import project.simulation.maps.animalBehavior.AnimalBehavior;
import project.simulation.maps.spawningPlants.SpawningPlants;

public record MapSettings(
        int width,
        int height,
        int startEnergy,
        int readyToBreedEnergy,
        int moveEnergy,
        int grassEnergy,
        int genomeSize,
        int startAnimals,
        int startPlants,
        int breedEnergy,
        int plantsPerDay,
        double JUNGLE_MAP_RATIO
) {
}