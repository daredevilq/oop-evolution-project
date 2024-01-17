package project.model.simulation.maps;

import project.model.simulation.config.MapInit;
import project.model.simulation.config.MapSettings;
import project.model.simulation.config.Modifications;

public enum MapType {
    EARTH;

    @Override
    public String toString() {
        return switch (this) {
            case EARTH -> "Earth";

        };
    }
    public IWorldMap createMapClass(MapSettings mapSettings, Modifications modifications, MapInit mapInitialize){
        return switch (this) {
            case EARTH -> new EarthMap(mapSettings,  modifications,  mapInitialize);
        };
    }

}
