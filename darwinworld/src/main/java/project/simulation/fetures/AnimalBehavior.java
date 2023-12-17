package project.simulation.fetures;

public enum AnimalBehavior {
    PREDESTINATION,
    MADNESS;

    public String toString() {
        return switch (this) {
            case PREDESTINATION -> "Kompletna predestynacja";
            case MADNESS -> "Szaleństwo";
        };
    }

}
