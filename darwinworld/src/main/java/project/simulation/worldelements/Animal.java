package project.simulation.worldelements;

import project.MapDirection;
import project.RandomGen;
import project.Vector2D;
import project.simulation.config.MapSettings;
import project.simulation.maps.IWorldMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal extends WorldElement{
    private static final int MIN_GENE_NUM = 0;
    private static final int MAX_GENE_NUM = 7;

//    MapSettings settings;
    private Vector2D position;
    private MapDirection direction;
    private int energy;
    private final List<Integer> genotype;
    private int currentGeneIndex;
    private int age;
    private int atePlants = 0;
    private int childrenCounter;




    public Animal(Vector2D position, MapDirection direction, int energy, List<Integer> genotype) {
//        this.settings = settings;
        this.position = position;
        this.direction = direction;
        this.energy = energy;
        this.genotype = genotype;
        this.currentGeneIndex = RandomGen.randInt(0, genotype.size() - 1);
        this.age = 0;
        this.childrenCounter = 0;
    }

    public Vector2D getPosition() {
        return position;
    }

//    public void setNextGeneIndex() {
//        this.currentGeneIndex++;
//        currentGeneIndex = currentGeneIndex % genotype.size();
//    }
//
//    public void setRandomGenIndex() {
//        this.currentGeneIndex = RandomGen.randInt(0, genotype.size() - 1);
//    }
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


    //Metoda rusza zwierzaka na nastepna pozycje
    // uzwzgledniajac przy tym losowosc zachowania zwierzaka (MADNESS / PREDESTINATION) jeden z wariantow
    // map.getAnimalBehavior() - zwraca aktualny wariant zachowania zwierzaka podany w konfikuracji MapSettings
    // zrobilem co switchem, w sumie nie wiem jak inaczej uwzglednic te warianty, moze lazarz podpowie nam w poniedzialek
    // jest 20% szans na szalenstwo

//    public void move(IWorldMap map) {
//
//        this.direction = this.direction.rotate(currentGeneIndex);
//        Vector2D currPosition = this.position;
//        Vector2D newPosition = map.getNextPosition(this.position, direction.toUnitVector());
//
//        if (currPosition == newPosition){
//            this.direction = this.direction.rotate(3); // obroc gdy zwierze sie nie poruszylo
//        }
//
//        this.position = newPosition;
//        this.age++;
//        this.energy -= map.getMapSettings().moveEnergy();
//
//        this.currentGeneIndex = map.getMapSettings().animalBehavior(currentGeneIndex, map.getMapSettings().genomeSize());
//    }

    public void move(IWorldMap map){
        this.direction = this.direction.rotate(currentGeneIndex);
        Vector2D currPosition = this.position;

        Vector2D newPosition = currPosition.add(direction.toUnitVector());

        if (map.canMoveTo(newPosition)) {
            newPosition = map.getNextPosition(newPosition);
            this.position = newPosition;
        } else {
            this.direction = this.direction.rotate(3);
            this.position = currPosition;
        }

        this.age++;
        this.energy -= map.getMapSettings().moveEnergy();
    }

    public void eatPlant(int energy){
        this.energy += energy;
        this.atePlants += 1;
    }

    public void changeStatsAfterBreeding(int energy){
        this.childrenCounter += 1;
        this.energy -= energy;
    }


    public List<Integer> reproduce(Animal partner) {
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

        mutate(childGenotype);

        return childGenotype;
    }

    private void mutate(List<Integer> childGenotype) {
        int numberOfMutations = RandomGen.randInt(genotype.size()); // Losowa liczba mutacji

        for (int i = 0; i < numberOfMutations; i++) {
            int mutationIndex = RandomGen.randInt(genotype.size()); // Losowy indeks do mutacji
            int newGeneValue = RandomGen.randInt(MIN_GENE_NUM, MAX_GENE_NUM); // Losowa nowa wartość genu
            childGenotype.set(mutationIndex, newGeneValue);
        }
    }

    public void decreaseEnergyBy1(){
        this.energy -= 1;
    }

    @Override
    public String toString() {
        return "(" + this.position.toString() + " Energia: " + this.energy + ")";
    }
}
