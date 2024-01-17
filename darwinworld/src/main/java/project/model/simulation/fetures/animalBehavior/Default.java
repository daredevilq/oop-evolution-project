package project.model.simulation.fetures.animalBehavior;

public class Default implements AnimalBehavior {
    @Override
    public int SetGeneIndex(int currentGene, int genomSize) {
        return (currentGene+1) % genomSize;
    }
}
