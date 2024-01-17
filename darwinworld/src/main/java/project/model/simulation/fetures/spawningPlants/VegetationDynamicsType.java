package project.model.simulation.fetures.spawningPlants;

public enum VegetationDynamicsType {
    EQUATOR,
    CRAWLING_JUNGLE;

    @Override
    public String toString() {
        return switch (this) {
            case EQUATOR -> "Forested equators";
            case CRAWLING_JUNGLE -> "Moving jungle";
        };
    }
    public SpawningPlants createVegetationClass(){
        return switch (this) {
            case EQUATOR -> new SpawnPlantWithForestedEquators();
            case CRAWLING_JUNGLE -> new SpawnPlantWithMovingJungle();
        };
    }
}
