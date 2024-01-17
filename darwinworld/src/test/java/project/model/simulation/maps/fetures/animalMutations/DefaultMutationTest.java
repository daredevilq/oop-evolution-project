package project.model.simulation.maps.fetures.animalMutations;

import org.junit.jupiter.api.Test;
import project.model.simulation.fetures.animalMutations.AnimalMutation;
import project.model.simulation.fetures.animalMutations.DefaultMutation;

import static org.junit.jupiter.api.Assertions.*;
import static project.model.simulation.worldelements.Genotype.MAX_GENE_NUM;
import static project.model.simulation.worldelements.Genotype.MIN_GENE_NUM;

import java.util.ArrayList;
import java.util.List;

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