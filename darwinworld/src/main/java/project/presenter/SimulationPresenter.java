package project.presenter;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import project.Vector2D;
import project.simulation.Simulation;
import project.simulation.maps.Boundary;
import project.simulation.maps.IWorldMap;
import javafx.scene.Node;
import java.awt.*;
import java.util.List;

import javafx.scene.control.Label;
import project.simulation.observer.SimulationChangeListener;

public class SimulationPresenter implements SimulationChangeListener {

    @FXML
    private GridPane mapGrid;


    private IWorldMap map;
    private Simulation simulation;

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
        this.map = simulation.getMap();
    }

    public void drawMap() {
        clearGrid();

        Boundary bounds = map.getBoundary();
        int minX = bounds.lowerLeftCorner().getX();
        int minY = bounds.lowerLeftCorner().getY();
        int maxX = bounds.upperRightCorner().getX();
        int maxY = bounds.upperRightCorner().getY();

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
            mapGrid.getColumnConstraints().add(new ColumnConstraints(40));
        }

        for (int i = 0; i < (maxY - minY + 2); i++) {
            mapGrid.getRowConstraints().add(new RowConstraints(40));
        }
    }


    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }



    @Override
    public void simulationChanged(Simulation simulation) {
        Platform.runLater(this::drawMap);
    }
    private boolean isSimulationRunning = false;
    public void onSimulationStartClicked(ActionEvent actionEvent) {
        //simulation.run();

        if (!isSimulationRunning) {
            isSimulationRunning = true;
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() {
                    simulation.run();
                    return null;
                }
            };

            task.setOnSucceeded(event -> isSimulationRunning = false);

            new Thread(task).start();
        }
    }



//    @FXML
//    private void onSimulationStartClicked() {
//        String[] commandLineArgs = movesTextArea.getText().split("\\s+");
//
//        try {
//            List<MoveDirection> directions = OptionsParser.parse(commandLineArgs);
//            List<Vector2d> positions = List.of(new Vector2d(0, 0), new Vector2d(2, 3));
//            Simulation simulation = new Simulation(directions, positions, worldMap);
//
//            Task<Void> simulationTask = new Task<>() {
//                @Override
//                protected Void call() {
//                    simulation.run();
//                    return null;
//                }
//            };
//
//            simulationTask.setOnSucceeded(event -> {
//                drawMap();
//                System.out.println("Simulation finished.");
//            });
//
//            new Thread(simulationTask).start();
//
//
//        } catch (IllegalArgumentException e) {
//            System.out.println("Error: " + e.getMessage());
//            System.exit(0);
//        }
//    }
}
