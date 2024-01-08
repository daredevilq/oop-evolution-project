package project.presenter;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.gui.SimulationApp;
import javafx.scene.control.Button;
import project.simulation.Simulation;
import project.simulation.config.MapInit;
import project.simulation.config.MapSettings;
import project.simulation.config.Modifications;
import project.simulation.fetures.*;
import project.simulation.maps.IWorldMap;

public class InitPresenter {
    @FXML
    private VBox Title;
    @FXML
    private Spinner<Integer> readyToBreedEnergy;
    @FXML
    private Spinner<Integer> startPlants;
    @FXML
    private Spinner<Integer> grassEnergy;
    @FXML
    private Spinner<Integer> plantsPerDay;
    @FXML
    private Spinner<Integer> animalNumber;
    @FXML
    private Spinner<Integer> startEnergy;
    @FXML
    private Spinner<Integer> animalsBreedEnergy;
    @FXML
    private Spinner<Integer> moveEnergy;
    @FXML
    private Spinner<Integer> genomSize;
    @FXML
    private ComboBox<BreedingType> animalBreedingType;
    @FXML
    private Spinner<Integer> mapWidth;
    @FXML
    private Spinner<Integer> mapHeight;
    @FXML
    private ComboBox<AnimalBehaviorType> animalBehaviorType;
    @FXML
    private ComboBox<VegetationDynamicsType> vegetation;
    @FXML
    private ComboBox<MutationType> mutationType;
    @FXML
    private ComboBox<MapType> mapType;
    @FXML
    private Button startSimulationButton;

    @FXML
    private void initialize() {
        mapType.getItems().addAll(MapType.values());
        mutationType.getItems().addAll(MutationType.values());
        vegetation.getItems().addAll(VegetationDynamicsType.values());
        animalBehaviorType.getItems().addAll(AnimalBehaviorType.values());
        animalBreedingType.getItems().addAll(BreedingType.values());
    }



    // Metoda do ustawiania obiektu klasy Stage
    @FXML
    private void startSimulation() {

        if (inputDataValidation()) {
            try {
                Simulation simulation = createSimulation();
                //przekazujemy symulacje do SimulationApp
                SimulationApp simulationApp = new SimulationApp(simulation);
                Stage secondStage = new Stage();

                simulationApp.start(secondStage);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            showAlert(Alert.AlertType.INFORMATION, "OJOJ!!!", "Nie podano wszystkich danych!");
        }
    }

    private boolean inputDataValidation(){
        return mapType.getValue() != null && vegetation.getValue() != null && animalBehaviorType.getValue() != null && animalBreedingType.getValue() != null &&  mutationType.getValue() != null;
    }

    private Simulation createSimulation() {
        Modifications modifications = new Modifications(vegetation.getValue().createVegetationClass(), animalBehaviorType.getValue().createAnimalBehaviorClass(), animalBreedingType.getValue().createBreedingClass(), mutationType.getValue().createAnimalMutationClass());
        MapSettings mapSettings = new MapSettings(mapWidth.getValue(), mapHeight.getValue(), startEnergy.getValue(), readyToBreedEnergy.getValue(), moveEnergy.getValue(), grassEnergy.getValue(), genomSize.getValue(), animalNumber.getValue(), startPlants.getValue(), animalsBreedEnergy.getValue(), plantsPerDay.getValue(), 0.2);

        IWorldMap map = mapType.getValue().createMapClass(mapSettings, modifications, new MapInit());

        Simulation simulation = new Simulation(map, modifications);

        return simulation;
    }

    private void showAlert(Alert.AlertType alertType, String title, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
