package project.simulation.config;

import project.simulation.maps.animalBehavior.AnimalBehavior;
import project.simulation.maps.animalBehavior.Default;
import project.simulation.maps.animalMutations.AnimalMutation;
import project.simulation.maps.animalMutations.DefaultMutation;
import project.simulation.maps.breeding.Breeding;
import project.simulation.maps.breeding.ClassicBreed;
import project.simulation.maps.spawningPlants.SpawnPlantWithForestedEquators;
import project.simulation.maps.spawningPlants.SpawningPlants;

public record Modifications(
        SpawningPlants spawningPlants,

        AnimalBehavior animalBehavior,

        Breeding breeding,

        AnimalMutation animalMutation
) {

}
