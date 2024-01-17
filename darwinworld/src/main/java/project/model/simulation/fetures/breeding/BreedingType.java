package project.model.simulation.fetures.breeding;

public enum BreedingType {
    DEFAULT;

    public String toString() {
        return switch (this) {
            case DEFAULT -> "Default breeding";
        };
    }
    public Breeding createBreedingClass(){
        return switch (this) {
            case DEFAULT -> new ClassicBreed();
        };
    }
}
