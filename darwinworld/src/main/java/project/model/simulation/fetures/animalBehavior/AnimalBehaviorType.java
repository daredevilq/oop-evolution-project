package project.model.simulation.fetures.animalBehavior;

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
