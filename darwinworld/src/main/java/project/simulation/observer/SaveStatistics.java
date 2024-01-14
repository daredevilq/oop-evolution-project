package project.simulation.observer;

import javafx.collections.MapChangeListener;
import project.simulation.Simulation;
import project.simulation.maps.IWorldMap;
import project.simulation.statistics.SimulationStatistics;
import project.simulation.worldelements.IWorldElement;

public class SaveStatistics implements SimulationChangeListener {
    private final SimulationStatistics simulationStatistics;
    private final IWorldMap map;

    public SaveStatistics(SimulationStatistics simulationStatistics, IWorldMap map) {
        this.simulationStatistics = simulationStatistics;
        this.map = map;
    }

    @Override
    public void simulationChanged(Simulation simulation) {
        simulationStatistics.updateDailySimulationStats(map.getAnimalsList(), map.getDeadAnimals(),
                map.getMapPlants().size(), map.freePlacesOnMap());
    }
}
