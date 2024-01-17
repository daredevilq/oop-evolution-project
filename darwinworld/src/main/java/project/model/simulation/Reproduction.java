package project.model.simulation;

import project.model.simulation.config.RandomGenerator;
import project.model.simulation.fetures.animalMutations.AnimalMutation;
import project.model.simulation.worldelements.Animal;

import java.util.ArrayList;
import java.util.List;

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
            childGenotype.addAll(genotype1.subList(0, splitIndex));
            childGenotype.addAll(genotype2.subList(splitIndex, genotypeSize));

        } else {
            childGenotype.addAll(genotype2.subList(0, genotypeSize - splitIndex));
            childGenotype.addAll(genotype1.subList(genotypeSize - splitIndex, genotypeSize));
        }

        mutation.mutate(childGenotype);
        return childGenotype;
    }

}
