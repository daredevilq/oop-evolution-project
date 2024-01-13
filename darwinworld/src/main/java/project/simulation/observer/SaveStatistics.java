package project.simulation.observer;

import javafx.collections.MapChangeListener;
import project.simulation.Simulation;
import project.simulation.maps.IWorldMap;
import project.simulation.statistics.SimulationStatistics;
import project.simulation.worldelements.IWorldElement;

public class SaveStatistics implements SimulationChangeListener {

    @Override
    public void simulationChanged(Simulation simulation) {
        SimulationStatistics simulationStatistics = simulation.getSimulationStatistics();
        IWorldMap map = simulation.getMap();

        simulationStatistics.updateDailySimulationStats(map.getAnimalsList(), map.getDeadAnimals(),
                map.getMapPlants().size(), map.freePlacesOnMap());
    }
}
