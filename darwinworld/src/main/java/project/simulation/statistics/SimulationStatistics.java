package project.simulation.statistics;

import project.Vector2D;
import project.simulation.worldelements.Animal;
import project.simulation.worldelements.Grass;

import java.text.DecimalFormat;
import java.util.*;

public class SimulationStatistics {
    private int dayNum = 0;
    private int aliveAnimalsCount = 0;
    private int plantsNumber = 0;
    private int deadAnimalsCount = 0;
    private int freePlacesOnMap = 0;
    private double averageLivingAnimalsEnergy = 0;
    private double averageDeadAnimalsLifespan = 0;
    private double averageChildrenNumberForLivingAnimals = 0;
    private List<Integer> theMostPopularGenotype = new ArrayList<>();


    public void updateDailySimulationStats(List<Animal> animalList, List<Animal> deadAnimalList, int mapPlantSize, int freePlaces) {
        dayNum++;

        this.aliveAnimalsCount = animalList.size();
        this.deadAnimalsCount = deadAnimalList.size();

        this.plantsNumber = mapPlantSize;
        this.freePlacesOnMap = freePlaces;

        this.averageLivingAnimalsEnergy = calculateAverageLivingAnimalsEnergy(animalList);
        this.averageDeadAnimalsLifespan = calculateAverageDeadAnimalsLifespan(deadAnimalList);
        this.averageChildrenNumberForLivingAnimals = calculateAverageChildrenNumberForLivingAnimals(animalList);
        this.theMostPopularGenotype = calculateTheMostPopularGenotype(animalList);
    }

    public int getDayNum() {
        return dayNum;
    }

    public int getAliveAnimalsCount() {
        return aliveAnimalsCount;
    }

    public int getPlantsNumber() {
        return plantsNumber;
    }

    public int getDeadAnimalsCount() {
        return deadAnimalsCount;
    }

    public int getFreePlacesOnMap() {
        return freePlacesOnMap;
    }

    public double getAverageLivingAnimalsEnergy() {
        return averageLivingAnimalsEnergy;
    }

    public double getAverageDeadAnimalsLifespan() {
        return averageDeadAnimalsLifespan;
    }

    public double getAverageChildrenNumberForLivingAnimals() {
        return averageChildrenNumberForLivingAnimals;
    }

    public List<Integer> getTheMostPopularGenotype() {
        return theMostPopularGenotype;
    }

    private double calculateAverageLivingAnimalsEnergy(List<Animal> animalList) {
        if (aliveAnimalsCount == 0) return 0.0;
        double energySum = 0.0;
        for (Animal animal : animalList) {
            energySum += animal.getEnergy();
        }
        return energySum / aliveAnimalsCount;
    }

    private double calculateAverageDeadAnimalsLifespan(List<Animal> deadAnimalList) {
        int sumLivedDays = 0;
        if (this.deadAnimalsCount == 0) return 0.0;
        for (Animal animal : deadAnimalList) {
            sumLivedDays += animal.getAge();
        }
        return (double) sumLivedDays / (double) this.deadAnimalsCount;
    }


    private double calculateAverageChildrenNumberForLivingAnimals(List<Animal> animalList) {
        double childrenCounter = 0;
        for (Animal animal : animalList) {
            childrenCounter += animal.getChildrenList().size();
        }

        if (aliveAnimalsCount != 0) return childrenCounter / aliveAnimalsCount;

        return 0.0;
    }

    private List<Integer> calculateTheMostPopularGenotype(List<Animal> animalList) {
        Map<List<Integer>, Integer> countGenotypes = new HashMap<>();
        int mostCommonGenotypeCounter = 0;
        List<Integer> mostCommonGenotype = new ArrayList<>();
        int count;

        for (Animal animal : animalList) {
            List<Integer> genotype = animal.getGenotype();

            if (countGenotypes.containsKey(genotype)) {
                count = countGenotypes.get(genotype);
                countGenotypes.put(genotype, count + 1);

            } else {
                countGenotypes.put(genotype, 1);
                count = 0;
            }

            if (count + 1 > mostCommonGenotypeCounter) {
                mostCommonGenotype = genotype;
                mostCommonGenotypeCounter = count + 1;
            }
        }

        return mostCommonGenotype;
    }
}

