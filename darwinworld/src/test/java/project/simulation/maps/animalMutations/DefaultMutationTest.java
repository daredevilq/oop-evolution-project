package project.simulation.maps.animalMutations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import static project.simulation.worldelements.Animal.MAX_GENE_NUM;
import static project.simulation.worldelements.Animal.MIN_GENE_NUM;

class DefaultMutationTest {
    @Test
    void testMutate() {
        List<Integer> childGenotype = new ArrayList<>(List.of(1,2,3,4,5,6,7,0));


        AnimalMutation mutation = new DefaultMutation();
        mutation.mutate(childGenotype);

        for (int geneValue : childGenotype) {
            assertTrue(geneValue >= MIN_GENE_NUM && geneValue <= MAX_GENE_NUM);
        }

    }
}