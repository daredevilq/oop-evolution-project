package project.simulation.worldelements;

import project.MapDirection;
import project.Vector2D;

import java.util.List;

public class Genotype {
    public static final int MIN_GENE_NUM = 0;

    public static final int MAX_GENE_NUM = 7;

    private final List<Integer> genotype;

    private int currentGeneIndex;

    public List<Integer> getGenotype() {
        return genotype;
    }

    public int getCurrentGeneIndex() {
        return currentGeneIndex;
    }

    public void setCurrentGeneIndex(int currentGeneIndex) {
        this.currentGeneIndex = currentGeneIndex;
    }

    public Genotype(List<Integer> genotype) {
        this.genotype = genotype;
        this.currentGeneIndex = 0;
    }
}
