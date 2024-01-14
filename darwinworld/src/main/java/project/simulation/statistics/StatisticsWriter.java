package project.simulation.statistics;

import com.opencsv.CSVWriter;
import project.simulation.Simulation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class StatisticsWriter {
    private final List<String[]> buffer = new ArrayList<>();
    private final File file;

    public StatisticsWriter(String simulationUUID) {
        this.file = new File("darwinworld/statistics/simulationStatistics" + simulationUUID + ".csv");


        buffer.add(new String[]{"Day number", "Alive animals count", "Plants number", "Dead animals count",
                "Free places on map", "Average living animals energy", "Average dead animals lifespan",
                "Average children number for living animals", "The most popular genotype"});
    }

    public void writeStats(SimulationStatistics simulationStatistics) {
        buffer.add(new String[]{String.valueOf(simulationStatistics.getDayNum()),
                String.valueOf(simulationStatistics.getAliveAnimalsCount()),
                String.valueOf(simulationStatistics.getPlantsNumber()),
                String.valueOf(simulationStatistics.getDeadAnimalsCount()),
                String.valueOf(simulationStatistics.getFreePlacesOnMap()),
                String.valueOf(simulationStatistics.getAverageLivingAnimalsEnergy()),
                String.valueOf(simulationStatistics.getAverageDeadAnimalsLifespan()),
                String.valueOf(simulationStatistics.getAverageChildrenNumberForLivingAnimals()),
                String.valueOf(simulationStatistics.getTheMostPopularGenotype())});
    }

    public void writeToFile(SimulationStatistics simulationStatistics) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(file, true))) {
            writer.writeAll(buffer);
            writer.flush();
        } catch (IOException e) {
            System.err.println("Error writing to the log file: " + e.getMessage());
        }
    }

}
