package project.simulation.fetures;

public enum MapType {
    EARTH;

    @Override
    public String toString() {
        return switch (this) {
            case EARTH -> "Kula Ziemska";

        };
    }
}
