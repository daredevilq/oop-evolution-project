package project.simulation.worldelements;

import project.MapDirection;
import project.Vector2D;
import project.simulation.maps.EarthMap;
import project.simulation.maps.IWorldMap;

import java.util.List;

public class Animal {
    private static final int MIN_GENE_NUM = 0;
    private static final int MAX_GENE_NUM = 7;

    private Vector2D position;
    private MapDirection direction;
    private int energy;
    private final List<Integer> genotype;
    private int currentGeneIndex;
    private int age;
    private int childrenCounter;
//    private EarthMap map;



    public Animal(Vector2D position, MapDirection direction, int energy, List<Integer> genotype) {
        this.position = position;
        this.direction = direction;
        this.energy = energy;
        this.genotype = genotype;
        this.currentGeneIndex = 0;
        this.age = 0;
        this.childrenCounter = 0;
    }

    public void setNextGene() {
        this.currentGeneIndex++;
        currentGeneIndex = currentGeneIndex % genotype.size();
    }

    public Vector2D getPosition() {
        return position;
    }

    public void move(EarthMap validator) {
        this.direction = this.direction.rotate(currentGeneIndex);

        Vector2D currPosition = this.position;
        Vector2D newPosition = validator.getNextPosition(this.position, direction.toUnitVector());

        if (currPosition == newPosition){
            this.direction = this.direction.rotate(3); // obroc gdy zwierze sie nie poruszylo
        }

        this.position = newPosition;
        setNextGene();
    }



}
