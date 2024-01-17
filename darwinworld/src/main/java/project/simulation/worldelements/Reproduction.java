package project.simulation.worldelements;

import project.RandomGenerator;
import project.simulation.maps.animalMutations.AnimalMutation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Reproduction {

    public static List<Integer> reproduce(Animal animal1, Animal animal2, AnimalMutation mutation) {
        List<Integer> genotype1 = animal1.getGenotype();
        List<Integer> genotype2 = animal2.getGenotype();

        int genotypeSize = genotype1.size();

        double energyRatio = (double) animal1.getEnergy() / (animal1.getEnergy() + animal2.getEnergy());

        boolean takeLeft = RandomGenerator.randomBoolean();

        List<Integer> childGenotype = new ArrayList<>();
        int splitIndex = (int) (energyRatio * genotypeSize);

        if (takeLeft) {
            // Bierz lewą stronę genotypu od silniejszego osobnika
            childGenotype.addAll(genotype1.subList(0, splitIndex));

            // Bierz prawą stronę genotypu od słabszego osobnika
            childGenotype.addAll(genotype2.subList(splitIndex, genotypeSize));
        } else {
            // Bierz lewą stronę genotypu od słabszego osobnika
            childGenotype.addAll(genotype2.subList(0, genotypeSize - splitIndex));

            // Bierz prawą stronę genotypu od silniejszego osobnika
            childGenotype.addAll(genotype1.subList(genotypeSize - splitIndex, genotypeSize));
        }

        mutation.mutate(childGenotype);
        return childGenotype;
    }

}
