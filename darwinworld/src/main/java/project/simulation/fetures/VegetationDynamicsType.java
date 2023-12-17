package project.simulation.fetures;

public enum VegetationDynamicsType {
    EQUATOR,
    CRAWLING_JUNGLE;

    @Override
    public String toString() {
        return switch (this) {
            case EQUATOR -> "Zalesione równiki";
            case CRAWLING_JUNGLE -> "Pełzająca dżungla";
        };
    }
}
