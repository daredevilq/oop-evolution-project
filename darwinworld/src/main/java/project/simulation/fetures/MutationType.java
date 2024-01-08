package project.simulation.fetures;

import project.simulation.config.MapInit;
import project.simulation.config.MapSettings;
import project.simulation.config.Modifications;
import project.simulation.maps.EarthMap;
import project.simulation.maps.IWorldMap;
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
