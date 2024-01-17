package project.simulation.worldelements;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import project.RandomGenerator;
import project.simulation.maps.animalMutations.AnimalMutation;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReproductionTest {

    @Test
    void testChildGenotypeSize() {
        Animal animal1 = Mockito.mock(Animal.class);
        Animal animal2 = Mockito.mock(Animal.class);
        AnimalMutation mutation = Mockito.mock(AnimalMutation.class);

        List<Integer> genotype1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> genotype2 = Arrays.asList(6, 7, 8, 9, 10);

        when(animal1.getGenotype()).thenReturn(genotype1);
        when(animal2.getGenotype()).thenReturn(genotype2);

        when(animal1.getEnergy()).thenReturn(50);
        when(animal2.getEnergy()).thenReturn(30);

        doNothing().when(mutation).mutate(Mockito.anyList());

        List<Integer> childGenotype = Reproduction.reproduce(animal1, animal2, mutation);

        assertEquals(genotype1.size(), childGenotype.size());

    }

    @Test
    void testCheckGenotypeAfterTakingLeftFirst() {
        try (MockedStatic<RandomGenerator> mockedStatic = mockStatic(RandomGenerator.class)) {
            mockedStatic.when(RandomGenerator::randomBoolean).thenReturn(true);
            Animal animal1 = Mockito.mock(Animal.class);
            Animal animal2 = Mockito.mock(Animal.class);
            AnimalMutation mutation = Mockito.mock(AnimalMutation.class);

            List<Integer> genotype1 = Arrays.asList(1, 2, 3, 4, 5);
            List<Integer> genotype2 = Arrays.asList(6, 7, 8, 9, 10);

            when(animal1.getGenotype()).thenReturn(genotype1);
            when(animal2.getGenotype()).thenReturn(genotype2);

            when(animal1.getEnergy()).thenReturn(50);
            when(animal2.getEnergy()).thenReturn(30);

            doNothing().when(mutation).mutate(Mockito.anyList());

            List<Integer> childGenotype = Reproduction.reproduce(animal1, animal2, mutation);

            assertEquals(genotype1.size(), childGenotype.size());
            assertEquals(Arrays.asList(1, 2, 3, 9, 10), childGenotype);
        }
    }

    @Test
    void testCheckGenotypeAfterTakingRightFirst() {
        try (MockedStatic<RandomGenerator> mockedStatic = mockStatic(RandomGenerator.class)) {
            mockedStatic.when(RandomGenerator::randomBoolean).thenReturn(false);
            Animal animal1 = Mockito.mock(Animal.class);
            Animal animal2 = Mockito.mock(Animal.class);
            AnimalMutation mutation = Mockito.mock(AnimalMutation.class);

            List<Integer> genotype1 = Arrays.asList(1, 2, 3, 4, 5);
            List<Integer> genotype2 = Arrays.asList(6, 7, 8, 9, 10);

            when(animal1.getGenotype()).thenReturn(genotype1);
            when(animal2.getGenotype()).thenReturn(genotype2);

            when(animal1.getEnergy()).thenReturn(50);
            when(animal2.getEnergy()).thenReturn(30);

            doNothing().when(mutation).mutate(Mockito.anyList());

            List<Integer> childGenotype = Reproduction.reproduce(animal1, animal2, mutation);

            assertEquals(genotype1.size(), childGenotype.size());
            assertEquals(Arrays.asList(6, 7, 3, 4, 5), childGenotype);
        }
    }
}