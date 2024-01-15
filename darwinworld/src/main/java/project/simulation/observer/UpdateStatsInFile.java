package project.simulation.observer;

import project.simulation.Simulation;
import project.simulation.statistics.SimulationStatistics;
import project.simulation.statistics.StatisticsWriter;

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
