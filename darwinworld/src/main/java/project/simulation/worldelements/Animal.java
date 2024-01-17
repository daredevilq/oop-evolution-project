package project.simulation.worldelements;

import project.MapDirection;
import project.Vector2D;
import project.simulation.maps.IWorldMap;
import project.simulation.maps.animalBehavior.AnimalBehavior;
import project.simulation.maps.animalMutations.AnimalMutation;

import java.util.*;

public class Animal implements IWorldElement{
    private final Genotype genotype;
    private Vector2D position;
    private MapDirection direction;
    private int energy;
    private int age;
    private int eatenPlants;
    private int childrenCounter;
    private List<Animal> childrenList = new ArrayList<>();
    private boolean isAlive;


    public Animal(Vector2D position, MapDirection direction, int energy, List<Integer> gens) {
        this.position = position;
        this.direction = direction;
        this.energy = energy;
        this.genotype = new Genotype(gens);
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
        return genotype.getGenotype();
    }

    public int getCurrentGeneIndex() {
        return genotype.getCurrentGeneIndex();
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
        int currentGeneIndex = genotype.getCurrentGeneIndex();
        List<Integer> gens = genotype.getGenotype();

        this.direction = this.direction.rotate(gens.get(currentGeneIndex));
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
        genotype.setCurrentGeneIndex(animalBehavior.SetGeneIndex(currentGeneIndex, gens.size()));
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
