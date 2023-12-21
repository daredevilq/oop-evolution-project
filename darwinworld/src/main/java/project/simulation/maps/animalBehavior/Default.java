package project.simulation.maps.animalBehavior;

import project.RandomGen;

public class Default implements AnimalBehavior {
    @Override
    public int SetGeneIndex(int currentGene, int genomSize) {
        return (currentGene+1) % genomSize;
    }
}
