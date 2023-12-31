package project.simulation.config;

import project.simulation.maps.animalBehavior.AnimalBehavior;
import project.simulation.maps.breeding.Breeding;
import project.simulation.maps.spawningPlants.SpawningPlants;

public record Modifications(
    SpawningPlants spawningPlants,

    AnimalBehavior animalBehavior,

    Breeding breeding
) {}
