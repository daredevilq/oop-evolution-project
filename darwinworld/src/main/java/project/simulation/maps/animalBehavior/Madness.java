package project.simulation.maps.animalBehavior;

import project.RandomGenerator;

public class Madness implements AnimalBehavior {

    @Override
    public int SetGeneIndex(int currentGene, int genomSize) {
        if (Math.random() <= 0.2)
            return RandomGenerator.randInt(0, genomSize - 1);
        else
            return (currentGene + 1) % genomSize;
    }
}
