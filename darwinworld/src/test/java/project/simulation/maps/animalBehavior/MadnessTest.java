package project.simulation.maps.animalBehavior;

import org.junit.jupiter.api.Test;
import project.RandomGenerator;

import java.sql.SQLOutput;

import static org.junit.jupiter.api.Assertions.*;

class MadnessTest {

    @Test
    void setGeneIndex() {
        Madness madness = new Madness();
        int genomSize = 10;

        for (int i = 0; i < 1000; i++) {
            int currentGene = RandomGenerator.randInt(0, genomSize - 1);
            int result = madness.SetGeneIndex(currentGene, genomSize);
            assertTrue(result >= 0 && result < genomSize);
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
        boolean outcome = temp > 150 || temp <250;
        assertTrue(outcome);
    }
}