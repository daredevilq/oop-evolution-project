package project.presenter;

import javafx.fxml.FXML;

public class SimulationPresenter {
    @FXML
    private Label messageLabel;
    @FXML
    private GridPane mapGrid;

    @FXML
    private Label infoLabel;
    private WorldMap worldMap;

    public void setWorldMap(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public void drawMap() {
        clearGrid();

        Boundary bounds = worldMap.getCurrentBounds();
        int minX = bounds.lowerLeftCorner().getX();
        int minY = bounds.lowerLeftCorner().getY();
        int maxX = bounds.upperRightCorner().getX();
        int maxY = bounds.upperRightCorner().getY();


        for (int x = minX - 1; x <= maxX; x++) {
            for (int y = maxY + 1; y >= minY; y--) {
                Label label = new Label("");

                if (x == minX - 1 && y == maxY + 1) {
                    label = new Label("y\\x");
                } else if (x == minX - 1) {
                    label = new Label(String.valueOf(y));
                } else if (y == maxY + 1) {
                    label = new Label(String.valueOf(x));
                } else {
                    WorldElement element = worldMap.objectAt(new Vector2d(x, y));
                    if (element != null) {
                        try {
                            WorldElementBox elementBox = new WorldElementBox(element);
                            mapGrid.add(elementBox, x - minX + 1, maxY - y + 1);
                            continue;
                        } catch (IllegalArgumentException e){
                            System.out.println(e.getMessage());
                        }
                    }
                }

                label.setAlignment(Pos.CENTER);
                GridPane.setHalignment(label, HPos.CENTER);
                mapGrid.add(label, x - minX+1, maxY - y+1);
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
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(() -> {
            drawMap();
            messageLabel.setText(message);
        });
    }


    @FXML
    private void onSimulationStartClicked() {
        String[] commandLineArgs = movesTextArea.getText().split("\\s+");

        try {
            List<MoveDirection> directions = OptionsParser.parse(commandLineArgs);
            List<Vector2d> positions = List.of(new Vector2d(0, 0), new Vector2d(2, 3));
            Simulation simulation = new Simulation(directions, positions, worldMap);

            Task<Void> simulationTask = new Task<>() {
                @Override
                protected Void call() {
                    simulation.run();
                    return null;
                }
            };

            simulationTask.setOnSucceeded(event -> {
                drawMap();
                System.out.println("Simulation finished.");
            });

            new Thread(simulationTask).start();


        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(0);
        }
    }
}
