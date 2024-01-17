package project.model.simulation.observer;

import project.model.simulation.Simulation;
import project.model.simulation.statistics.StatisticsWriter;
import project.model.simulation.statistics.SimulationStatistics;

public class UpdateStatsInFile implements SimulationChangeListener{
    private final SimulationStatistics simulationStatistics;
    private final StatisticsWriter writer;

    public UpdateStatsInFile(SimulationStatistics stats, StatisticsWriter writer) {
        this.simulationStatistics = stats;
        this.writer = writer;
    }

    @Override
    public void simulationChanged(Simulation simulation) {
        writer.writeStats(simulationStatistics);
    }
}
