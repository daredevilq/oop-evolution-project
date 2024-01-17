package project.simulation.fetures;

import project.simulation.config.MapInit;
import project.simulation.config.MapSettings;
import project.simulation.config.Modifications;
import project.simulation.maps.EarthMap;
import project.simulation.maps.IWorldMap;
import project.simulation.maps.breeding.Breeding;
import project.simulation.maps.breeding.ClassicBreed;

public enum MapType {
    EARTH;

    @Override
    public String toString() {
        return switch (this) {
            case EARTH -> "Earth";
        };
    }

    public IWorldMap createMapClass(MapSettings mapSettings, Modifications modifications, MapInit mapInitialize) {
        return switch (this) {
            case EARTH -> new EarthMap(mapSettings, modifications, mapInitialize);
        };
    }
}
