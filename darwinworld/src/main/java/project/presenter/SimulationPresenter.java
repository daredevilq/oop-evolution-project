package project.presenter;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.*;
import project.Vector2D;
import project.simulation.Simulation;
import project.simulation.maps.Boundary;
import project.simulation.maps.IWorldMap;
import javafx.scene.control.Label;
import project.simulation.observer.SimulationChangeListener;
import project.simulation.statistics.SimulationStatistics;
import project.simulation.statistics.StatisticsWriter;
import project.simulation.worldelements.Animal;
import project.simulation.worldelements.IWorldElement;
import project.simulation.worldelements.WorldElementBox;

import static java.lang.Math.min;


import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;

public class SimulationPresenter implements SimulationChangeListener {

    private static final int MAP_CONST =  700;
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

    //Traciking animal stats
    @FXML
    private Label animalTitle;
    @FXML
    private Label animalGenome;
    @FXML
    private Label genomeActivatedPart;
    @FXML
    private Label animalEnergy;
    @FXML
    private Label eatenPlants;
    @FXML
    private Label childrenCounter;
    @FXML
    private Label descendantsCounter;
    @FXML
    private Label aliveDays;
    @FXML
    private CheckBox showMostPopularGenotype;
    @FXML
    private CheckBox showPreferredGrowPlace;


    @FXML
    private GridPane mapGrid;
    private IWorldMap map;
    private Simulation simulation;
    private SimulationStatistics simulationStatistics;

    private Animal trackedAnimal;


    public void setSimulation(Simulation simulation) {
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

                int finalX = x;
                int finalY = y;
                cellPane.setOnMouseClicked(event ->{
                    if (!simulation.isRunning())
                        setTrackedAnimal(finalX, finalY);
                });
                if (x == minX - 1 && y == maxY + 1) {
                    label = new Label("y\\x");
                } else if (x == minX - 1) {
                    label = new Label(String.valueOf(y));
                } else if (y == maxY + 1) {
                    label = new Label(String.valueOf(x));
                } else {

                    colorCell(cellPane, x, y);
                    IWorldElement element = map.objectAt(new Vector2D(x, y));
                    if (element != null) {
                        try {
                            WorldElementBox elementBox = new WorldElementBox(element,
                                    (int) (0.7*(min(cellWidth,cellHeight))));

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
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0));
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }



    public void updateStats(){
        updateTrackedAnimalStats();
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

    @FXML
    public void onSimulationStartClicked(ActionEvent actionEvent) {
        if (! simulation.isRunning()) {
            simulation.setRunning(true);
            startStopButton.setText("Stop Simulation");
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() {
                    simulation.run();
                    return null;
                }
            };
            task.setOnSucceeded(event -> simulation.setRunning(false));
            new Thread(task).start();
        }
        else {
            simulation.setRunning(false);
            startStopButton.setText("Start Simulation");
        }
    }

    @FXML
    private void changeSpeed(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonText = clickedButton.getText();

        double multiplier;
        switch (buttonText) {
            case "x0.5" -> multiplier = 2.0; // 1 second
            case "x1" -> multiplier = 1.0; // 0.5 second
            case "x2" -> multiplier = 0.5; // 0.25 second
            default -> multiplier = 1.0;
        }

        this.simulation.setSimulationSpeed(multiplier);
    }

    private void setTrackedAnimal(int x, int y){
        for(Animal animal: map.getAnimalsList()){
            if (animal.getPosition().getX() == x && animal.getPosition().getY() == y) {

                trackedAnimal = animal;
                drawMap();
                updateTrackedAnimalStats();
                return;
            }
        }
    }


    private void colorCell(StackPane cell, int x, int y) {
        Vector2D position = new Vector2D(x, y);
        IWorldElement element = this.map.objectAt(position);

        if (trackedAnimal != null && trackedAnimal.getPosition().equals(position))
            cell.setStyle("-fx-background-color: #a0b0ee; " +
                    "-fx-border-color: black; -fx-border-width: 1;");

        else if (showMostPopularGenotype.isSelected() && (element instanceof Animal animal) &&
                animal.getGenotype().equals(simulationStatistics.getTheMostPopularGenotype()))
                cell.setStyle("-fx-background-color: #9c63b7; " +
                        "-fx-border-color: black; -fx-border-width: 1;");

        else if (showPreferredGrowPlace.isSelected() &&
                map.getModifications().spawningPlants().isPreferredGrowPlace(position, map))
            cell.setStyle("-fx-background-color: #4c794c; " +
                    "-fx-border-color: black; -fx-border-width: 1;");
        else
            cell.setStyle("-fx-background-color: #bde5b4; " +
                    "-fx-border-color: black; -fx-border-width: 1;");
    }

    @FXML
    private void resetTracker(ActionEvent event) {
        trackedAnimal = null;
        drawMap();

        animalTitle.setText("");
        animalGenome.setText("");
        genomeActivatedPart.setText("");
        animalEnergy.setText("");
        eatenPlants.setText("");
        childrenCounter.setText("");
        descendantsCounter.setText("");
        aliveDays.setText("");
    }

    private void updateTrackedAnimalStats() {
        if (trackedAnimal != null && !trackedAnimal.isAlive()){
            resetTracker(null);
        }

        if (trackedAnimal != null) {
            animalTitle.setText("Tracked animal");
            animalGenome.setText("Genome: " + trackedAnimal.getGenotype());
            genomeActivatedPart.setText("Genome activated part: " + trackedAnimal.getCurrentGeneIndex());
            animalEnergy.setText("Energy: " + trackedAnimal.getEnergy());
            eatenPlants.setText("Eaten plants: " + trackedAnimal.getEatenPlants());
            childrenCounter.setText("Children number: " + trackedAnimal.getChildrenList().size());
            descendantsCounter.setText("Descendants number: " + trackedAnimal.computeNumberOfDescendants());
            aliveDays.setText("Alive days: " + trackedAnimal.getAge());
        }
    }

    @FXML
    private void refreshMap(){
        drawMap();
    }

}
