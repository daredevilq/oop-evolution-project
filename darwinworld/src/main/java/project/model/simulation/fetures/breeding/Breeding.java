package project.model.simulation.fetures.breeding;

import project.model.simulation.fetures.animalMutations.AnimalMutation;
import project.model.simulation.worldelements.Animal;

import java.util.List;

public interface Breeding {
    List<Animal> breed(List<Animal> animalList,  int breadEnergy, int readyToBreedEnergy, AnimalMutation animalMutation);
}
