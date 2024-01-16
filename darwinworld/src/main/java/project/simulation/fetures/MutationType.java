package project.simulation.fetures;

import project.simulation.maps.animalMutations.AnimalMutation;
import project.simulation.maps.animalMutations.DefaultMutation;

public enum MutationType {
    RANDOMNESS;
    public String toString() {
        return switch (this) {
            case RANDOMNESS -> "Full randomness";
        };
    }
    public AnimalMutation createAnimalMutationClass(){
        return switch (this) {
            case RANDOMNESS -> new DefaultMutation();
        };
    }
}
