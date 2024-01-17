package project.model.simulation.observer;

import project.model.simulation.Simulation;
import project.model.simulation.maps.IWorldMap;
import project.model.simulation.statistics.SimulationStatistics;

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
