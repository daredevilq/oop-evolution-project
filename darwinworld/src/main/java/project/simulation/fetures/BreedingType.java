package project.simulation.fetures;

import project.simulation.maps.breeding.Breeding;
import project.simulation.maps.breeding.ClassicBreed;

public enum BreedingType {
    DEFAULT;

    public String toString() {
        return switch (this) {
            case DEFAULT -> "Default breeding";
        };
    }

    public Breeding createBreedingClass() {
        return switch (this) {
            case DEFAULT -> new ClassicBreed();
        };
    }
}
