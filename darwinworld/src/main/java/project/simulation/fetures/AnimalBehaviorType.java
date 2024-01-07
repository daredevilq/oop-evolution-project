package project.simulation.fetures;

import project.simulation.maps.animalBehavior.AnimalBehavior;
import project.simulation.maps.animalBehavior.Default;
import project.simulation.maps.animalBehavior.Madness;

public enum AnimalBehaviorType {
    PREDESTINATION,
    MADNESS;

    public String toString() {
        return switch (this) {
            case PREDESTINATION -> "Predestination";
            case MADNESS -> "Madness";
        };
    }
    public AnimalBehavior createAnimalBehaviorClass(){
        return switch (this) {
            case PREDESTINATION -> new Default();
            case MADNESS -> new Madness();
        };
    }
}
