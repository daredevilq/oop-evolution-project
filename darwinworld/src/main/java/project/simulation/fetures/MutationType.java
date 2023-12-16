package project.simulation.fetures;

public enum MutationType {
    RANDOMNESS;
    public String toString() {
        return switch (this) {
            case RANDOMNESS -> "Pełna losowość";
        };
    }

}
