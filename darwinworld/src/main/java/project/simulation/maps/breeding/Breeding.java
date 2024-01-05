package project.simulation.maps.breeding;

import project.simulation.worldelements.Animal;

import java.util.List;

public interface Breeding {
    List<Animal> breed(List<Animal> animalList, int startEnergy, int breadEnergy);
}
