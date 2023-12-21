package project.simulation.maps.animalBehavior;

import project.RandomGen;

public class Madness implements AnimalBehavior{

    @Override
    public int SetGeneIndex(int currentGene, int genomSize) {
        if (Math.random() <= 0.2)
            return RandomGen.randInt(0, genomSize - 1);
        else
            return (currentGene+1) % genomSize;
    }
}
