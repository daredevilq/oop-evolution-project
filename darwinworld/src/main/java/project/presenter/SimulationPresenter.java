package project.presenter;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import project.Vector2D;
import project.simulation.Simulation;
import project.simulation.maps.Boundary;
import project.simulation.maps.IWorldMap;
import javafx.scene.control.Label;
import project.simulation.observer.SimulationChangeListener;
import project.simulation.statistics.SimulationStatistics;
import project.simulation.statistics.StatisticsWriter;
import project.simulation.worldelements.IWorldElement;
import project.simulation.worldelements.WorldElementBox;

import java.io.IOException;


public class SimulationPresenter implements SimulationChangeListener {

    private static final int MAP_CONST =  600;
    @FXML
    private Button startStopButton;
    @FXML
    private Button speedButtonx05;
    @FXML
    private Button speedButtonx1;
    @FXML
    private Button speedButtonx2;

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
        drawMap();
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
                Label label = new Label("");
                StackPane cellPane = new StackPane();

                if (x == minX - 1 && y == maxY + 1) {
                    label = new Label("y\\x");
                } else if (x == minX - 1) {
                    label = new Label(String.valueOf(y));
                } else if (y == maxY + 1) {
                    label = new Label(String.valueOf(x));
                } else {
                    if (map.isJungleArea(new Vector2D(x, y)))
                        cellPane.setStyle("-fx-background-color: #679f65; " +
                                "-fx-border-color: black; -fx-border-width: 1;");
                    else
                        cellPane.setStyle("-fx-background-color: #bde5b4; " +
                                "-fx-border-color: black; -fx-border-width: 1;");

                    Object element = map.objectAt(new Vector2D(x, y));
                    if (element != null) {
                        try {
                            WorldElementBox elementBox = new WorldElementBox((IWorldElement) element, (int) (0.8*cellWidth));
                            cellPane.getChildren().add(elementBox);
                            mapGrid.add(cellPane, x - minX + 1, maxY - y + 1);
                            continue;
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }

                }

                label.setAlignment(Pos.CENTER);
                cellPane.getChildren().add(label);
                GridPane.setHalignment(label, HPos.CENTER);
                mapGrid.add(cellPane, x - minX + 1, maxY - y + 1);
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
    public void simulationChanged(Simulation simulation) {
        Platform.runLater(this::drawMap);
        Platform.runLater(this::updateStats);
    }

    public void saveStatsToFile() {
        if (this.simulation.isStoreStatistics()){
            StatisticsWriter statisticsWriter = simulation.getStatisticsWriter();
            statisticsWriter.writeToFile(simulationStatistics);
        }
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

    @FXML
    private void changeSpeed(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonText = clickedButton.getText();

        double multipier;
        switch (buttonText) {
            case "x0.5" -> multipier = 10.0;
            case "x1" -> multipier = 1.0;
            case "x2" -> multipier = 0.5;
            default -> multipier = 1.0;
        }

        this.simulation.setSimulationSpeed(multipier);
    }

}
