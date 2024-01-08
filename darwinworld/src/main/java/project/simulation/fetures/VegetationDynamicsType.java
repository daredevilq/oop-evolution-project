package project.simulation.fetures;

import project.simulation.config.MapInit;
import project.simulation.config.MapSettings;
import project.simulation.config.Modifications;
import project.simulation.maps.EarthMap;
import project.simulation.maps.IWorldMap;
import project.simulation.maps.spawningPlants.SpawnPlantWithForestedEquators;
import project.simulation.maps.spawningPlants.SpawnPlantWithMovingJungle;
import project.simulation.maps.spawningPlants.SpawningPlants;

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
