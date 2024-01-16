package project.simulation.worldelements;

import project.MapDirection;
import project.Vector2D;
import project.simulation.maps.IWorldMap;
import project.simulation.maps.animalBehavior.AnimalBehavior;
import project.simulation.maps.animalMutations.AnimalMutation;

import java.util.*;

public class Animal implements IWorldElement{
    public static final int MIN_GENE_NUM = 0;
    public static final int MAX_GENE_NUM = 7;

    private Vector2D position;
    private MapDirection direction;
    private int energy;
    private final List<Integer> genotype;
    private int currentGeneIndex;
    private int age;
    private int eatenPlants;
    private int childrenCounter;
    private List<Animal> childrenList = new ArrayList<>();
    private boolean isAlive;


    public Animal(Vector2D position, MapDirection direction, int energy, List<Integer> genotype) {
        this.position = position;
        this.direction = direction;
        this.energy = energy;
        this.genotype = genotype;
        this.currentGeneIndex = 0;
        this.age = 0;
        this.childrenCounter = 0;
        this.eatenPlants = 0;
        this.isAlive = true;
    }

    public Vector2D getPosition() {
        return position;
    }

    public int getEatenPlants() {
        return eatenPlants;
    }
    public MapDirection getDirection() {
        return direction;
    }

    public int getEnergy() {
        return energy;
    }

    public List<Integer> getGenotype() {
        return genotype;
    }

    public int getCurrentGeneIndex() {
        return currentGeneIndex;
    }

    public int getAge() {
        return age;
    }

    public int getChildrenCounter() {
        return childrenCounter;
    }

    public List<Animal> getChildrenList() {
        return Collections.unmodifiableList(childrenList);
    }

    public void setChildrenList(List<Animal> childrenList) {
        this.childrenList = childrenList;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }


    public int computeNumberOfDescendants() {
        Set<Animal> visitedAnimals = new HashSet<>();
        return countRecursive(this, visitedAnimals);
    }

    private int countRecursive(Animal currentAnimal, Set<Animal> visitedAnimals) {
        visitedAnimals.add(currentAnimal);
        int successorsCount = 0;

        for (Animal child : currentAnimal.childrenList) {
            if (!visitedAnimals.contains(child)) {
                successorsCount++; // Liczymy dziecko jako następnika
                successorsCount += countRecursive(child, visitedAnimals);
            }
        }
        return successorsCount;
    }

    public void move(IWorldMap map, AnimalBehavior animalBehavior){
        this.direction = this.direction.rotate(genotype.get(currentGeneIndex));
        Vector2D currPosition = this.position;

        Vector2D newPosition = currPosition.add(direction.toUnitVector());
        if (map.canMoveTo(newPosition)) {
            newPosition = map.getNextPosition(newPosition);
            this.position = newPosition;
        } else {
            this.direction = this.direction.rotate(4);
            this.position = currPosition;

        }
        this.energy -= map.getMapSettings().moveEnergy();
        this.currentGeneIndex = animalBehavior.SetGeneIndex(currentGeneIndex, genotype.size());
    }

    public void eatPlant(int energy){
        this.energy += energy;
        this.eatenPlants += 1;
    }

    public void changeStatsAfterBreeding(int energyLost, Animal child){
        this.energy -= energyLost;
        this.childrenList.add(child);
        this.childrenCounter = childrenList.size();
    }


    public List<Integer> reproduce(Animal partner, AnimalMutation mutation) {
        int genotypeSize = genotype.size();

        double energyRatio = (double) this.getEnergy() / (this.getEnergy() + partner.getEnergy());

        boolean takeLeft = new Random().nextBoolean();

        List<Integer> childGenotype = new ArrayList<>();
        int splitIndex = (int) (energyRatio * genotypeSize);

        if (takeLeft) {
            // Bierz lewą stronę genotypu od silniejszego osobnika
            childGenotype.addAll(this.genotype.subList(0, splitIndex));

            // Bierz prawą stronę genotypu od słabszego osobnika
            childGenotype.addAll(partner.genotype.subList(splitIndex, genotype.size()));
        } else {
            // Bierz lewą stronę genotypu od słabszego osobnika
            childGenotype.addAll(partner.genotype.subList(0, genotypeSize - splitIndex));

            // Bierz prawą stronę genotypu od silniejszego osobnika
            childGenotype.addAll(this.genotype.subList(genotypeSize - splitIndex, genotypeSize));
        }

        //mutate(childGenotype);
        mutation.mutate(childGenotype);
        return childGenotype;
    }

    public void updateDailyStatsOnAnimal(){
        this.energy -= 1;
        this.age += 1;
    }

    @Override
    public String toString(){
            return "Z";
    }

    @Override
    public String getResourceName() {
        return "img/animal.png";
    }
}
