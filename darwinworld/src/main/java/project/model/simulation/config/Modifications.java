package project.model.simulation.config;

import project.model.simulation.fetures.animalBehavior.AnimalBehavior;
import project.model.simulation.fetures.animalMutations.AnimalMutation;
import project.model.simulation.fetures.breeding.Breeding;
import project.model.simulation.fetures.spawningPlants.SpawningPlants;

public record Modifications(
        SpawningPlants spawningPlants,

        AnimalBehavior animalBehavior,

        Breeding breeding,

        AnimalMutation animalMutation
    ) {

}
