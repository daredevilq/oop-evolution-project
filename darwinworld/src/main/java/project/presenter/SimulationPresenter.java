package project.presenter;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import project.RandomGen;
import project.Vector2D;
import project.simulation.Simulation;
import project.simulation.maps.Boundary;
import project.simulation.maps.IWorldMap;
import javafx.scene.control.Label;
import project.simulation.observer.SimulationChangeListener;
import project.simulation.statistics.SimulationStatistics;
import project.simulation.statistics.StatisticsWriter;

import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;

public class SimulationPresenter implements SimulationChangeListener {

    private static final int MAP_CONST =  600;
    @FXML
    private Button startStopButton;
    @FXML
    private Label dayNum;
    @FXML
    private Label aliveAnimalsCount;
    @FXML
    private Label plantsNumber;
    @FXML
    private Label deadAnimalsCount;
    @FXML
    private Label freePlacesOnMap;
    @FXML
    private Label averageLivingAnimalsEnergy;
    @FXML
    private Label averageDeadAnimalsLifespan;
    @FXML
    private Label averageChildrenNumberForLivingAnimals;
    @FXML
    private Label theMostPopularGenotype;


    @FXML
    private GridPane mapGrid;
    private IWorldMap map;
    private Simulation simulation;
    private SimulationStatistics simulationStatistics;

    public void setSimulation(Simulation simulation) throws IOException {
        this.simulation = simulation;
        this.map = simulation.getMap();
        this.simulationStatistics = simulation.getSimulationStatistics();
    }

    public void drawMap() {
        clearGrid();

        Boundary bounds = map.getBoundary();
        int minX = bounds.lowerLeftCorner().getX();
        int minY = bounds.lowerLeftCorner().getY();
        int maxX = bounds.upperRightCorner().getX();
        int maxY = bounds.upperRightCorner().getY();

        int cellWidth = (int) (MAP_CONST / (maxX - minX + 2));
        int cellHeight = (int) (MAP_CONST / (maxY - minY + 2));

        for (int x = minX - 1; x <= maxX; x++) {
            for (int y = maxY + 1; y >= minY; y--) {
                Label label;

                if (x == minX - 1 && y == maxY + 1) {
                    label = new Label("y\\x");
                } else if (x == minX - 1) {
                    label = new Label(String.valueOf(y));
                } else if (y == maxY + 1) {
                    label = new Label(String.valueOf(x));
                } else {
                    Object element = map.objectAt(new Vector2D(x, y));
                    if (element != null) {
                        try {
                            Label elementBox = new Label(element.toString());
                            elementBox.setAlignment(Pos.CENTER);
                            GridPane.setHalignment(elementBox, HPos.CENTER);
                            GridPane.setValignment(elementBox, VPos.CENTER);

                            mapGrid.add(elementBox, x - minX + 1, maxY - y + 1);
                            continue;
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    label = new Label("");
                }

                label.setAlignment(Pos.CENTER);
                GridPane.setHalignment(label, HPos.CENTER);
                GridPane.setValignment(label, VPos.CENTER);
                mapGrid.add(label, x - minX + 1, maxY - y + 1);
            }
        }

        for (int i = 0; i < (maxX - minX + 2); i++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(cellWidth));
        }

        for (int i = 0; i < (maxY - minY + 2); i++) {
            mapGrid.getRowConstraints().add(new RowConstraints(cellHeight));
        }
    }


    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }



    public void updateStats(){
        dayNum.setText("Day number: "+  simulationStatistics.getDayNum());
        aliveAnimalsCount.setText("Animals number: "+  simulationStatistics.getAliveAnimalsCount());
        plantsNumber.setText("Plants number: "+ simulationStatistics.getPlantsNumber());
        deadAnimalsCount.setText("Dead animas number: "+ simulationStatistics.getDeadAnimalsCount());
        freePlacesOnMap.setText("Free places on map: "+ simulationStatistics.getFreePlacesOnMap());
        averageLivingAnimalsEnergy.setText("Average animals energy: " + Math.round(simulationStatistics.getAverageLivingAnimalsEnergy()));
        averageDeadAnimalsLifespan.setText("Average lifespan: "+ Math.round(simulationStatistics.getAverageDeadAnimalsLifespan()) );
        averageChildrenNumberForLivingAnimals.setText("Average children number: "+ Math.round(simulationStatistics.getAverageChildrenNumberForLivingAnimals()));
        theMostPopularGenotype.setText("The most popular genotype: " + (simulationStatistics.getTheMostPopularGenotype()));
    }

    @Override
    public void simulationChanged(Simulation simulation) throws IOException {

        Platform.runLater(this::drawMap);
        Platform.runLater(this::updateStats);
        this.simulation.writeDownStatistics();
    }

    public void closeStatisticFile() throws IOException {
        this.simulation.saveStatistics();
    }

    public void onSimulationStartClicked(ActionEvent actionEvent) {
        if (! simulation.isRunning()) {
            simulation.startSimulation();
            startStopButton.setText("Stop Simulation");
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() {
                    simulation.run();
                    return null;
                }
            };
            task.setOnSucceeded(event -> simulation.stopSimulation());
            new Thread(task).start();
        }
        else {
            simulation.stopSimulation();
            startStopButton.setText("Start Simulation");
        }
    }


}
