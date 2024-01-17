package project.model.simulation.maps.fetures.animalBehavior;

import org.junit.jupiter.api.Test;
import project.model.simulation.fetures.animalBehavior.AnimalBehavior;
import project.model.simulation.fetures.animalBehavior.Default;

import static org.junit.jupiter.api.Assertions.*;

class DefaultTest {

    @Test
    void setGeneIndex() {
        //given
        AnimalBehavior animalBehavior = new Default();
        int currentGene = 7;
        int genomeSize = 8;

        //when
        int result = animalBehavior.SetGeneIndex(currentGene, genomeSize);

        //then
        assertEquals(0, result);
    }
}