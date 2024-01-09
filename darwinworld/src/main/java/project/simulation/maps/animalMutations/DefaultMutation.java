package project.simulation.maps.animalMutations;

import project.RandomGen;

import java.util.List;

import static project.simulation.worldelements.Animal.MAX_GENE_NUM;
import static project.simulation.worldelements.Animal.MIN_GENE_NUM;


public class DefaultMutation implements AnimalMutation{
    @Override
    public void mutate(List<Integer> childGenotype) {
        int numberOfMutations = RandomGen.randInt(childGenotype.size()); // Losowa liczba mutacji

        for (int i = 0; i < numberOfMutations; i++) {
            int mutationIndex = RandomGen.randInt(childGenotype.size() - 1); // Losowy indeks do mutacji
            int newGeneValue = RandomGen.randInt(MIN_GENE_NUM, MAX_GENE_NUM); // Losowa nowa wartość genu
            childGenotype.set(mutationIndex, newGeneValue);
        }
    }
}
