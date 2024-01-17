package project.model.simulation.maps.fetures.animalBehavior;

import org.junit.jupiter.api.Test;
import project.model.simulation.config.RandomGenerator;
import project.model.simulation.fetures.animalBehavior.Madness;


import static org.junit.jupiter.api.Assertions.*;

class MadnessTest {

    @Test
    void setGeneIndex() {
        Madness madness = new Madness();
        int genomeSize = 10;

        for (int i = 0; i < 1000; i++) {
            int currentGene = RandomGenerator.randInt(0, genomeSize - 1);
            int result = madness.SetGeneIndex(currentGene, genomeSize);
            assertTrue(result >= 0 && result < genomeSize);
        }
    }

    @Test
    void setGeneIndexWithProbability() {
        Madness madness = new Madness();
        int genomSize = 10;
        int currentGene = 0;
        int temp=0;
        for (int i=0; i<1000; i++){
            int result = madness.SetGeneIndex(currentGene, genomSize);
            if (result != (currentGene+1) % genomSize) temp++;
        }
        // margin 5%
        assertTrue(temp > 150 && temp <250);
    }
}