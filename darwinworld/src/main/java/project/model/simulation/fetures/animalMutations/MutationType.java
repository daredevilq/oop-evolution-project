package project.model.simulation.fetures.animalMutations;

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
