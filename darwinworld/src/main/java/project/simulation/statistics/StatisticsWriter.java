package project.simulation.statistics;

import com.opencsv.CSVWriter;
import project.simulation.Simulation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StatisticsWriter {

    private CSVWriter csvWriter;
    private List<String[]> buffer = new ArrayList<>();

    String path;

    public StatisticsWriter(Simulation simulation) throws IOException {
        path = "simulationStatistics" + simulation.toString() + ".csv";
        buffer.add(new String[]{"Day number", "Alive animals count", "Plants number", "Dead animals count", "Free places on map", "Average living animals energy", "Average dead animals lifespan", "Average children number for living animals", "The most popular genotype"});
    }

    public void writeStats(SimulationStatistics simulationStatistics) throws IOException {
        buffer.add(new String[]{String.valueOf(simulationStatistics.getDayNum()), String.valueOf(simulationStatistics.getAliveAnimalsCount()), String.valueOf(simulationStatistics.getPlantsNumber()), String.valueOf(simulationStatistics.getDeadAnimalsCount()), String.valueOf(simulationStatistics.getFreePlacesOnMap()), String.valueOf(simulationStatistics.getAverageLivingAnimalsEnergy()), String.valueOf(simulationStatistics.getAverageDeadAnimalsLifespan()), String.valueOf(simulationStatistics.getAverageChildrenNumberForLivingAnimals()), String.valueOf(simulationStatistics.getTheMostPopularGenotype())});
    }

    public void closeFile() throws IOException {
        this.csvWriter = new CSVWriter(new FileWriter(path));
        csvWriter.writeAll(buffer);
        csvWriter.close();
    }

}
