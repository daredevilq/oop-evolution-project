package project.presenter;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import project.gui.SimulationApp;

public class InitPresenter {
    private Stage primaryStage;

    // Metoda do ustawiania obiektu klasy Stage
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void startSimulation() {
        try {
            SimulationApp simulationApp = new SimulationApp();
            Stage secondStage = new Stage();
            simulationApp.start(secondStage);

            // Opcjonalnie: Możesz przekazać informacje między oknami
            // np. presenter.setWorldMap(worldMap);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
