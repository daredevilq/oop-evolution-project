package project.simulation.maps.animalBehavior;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultTest {

    @Test
    void setGeneIndex() {
        //given
        AnimalBehavior animalBehavior = new Default();
        int currentGene = 7;
        int genomSize = 8;

        //when
        int result = animalBehavior.SetGeneIndex(currentGene, genomSize);

        //then
        assertEquals(0, result);


    }
}