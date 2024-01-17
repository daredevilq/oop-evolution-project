package project.presenter;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import project.gui.SimulationApp;
import project.model.simulation.Simulation;
import project.model.simulation.config.MapInit;
import project.model.simulation.config.MapSettings;
import project.model.simulation.config.Modifications;
import project.model.simulation.fetures.animalBehavior.AnimalBehaviorType;
import project.model.simulation.fetures.animalMutations.MutationType;
import project.model.simulation.fetures.breeding.BreedingType;
import project.model.simulation.fetures.spawningPlants.VegetationDynamicsType;
import project.model.simulation.maps.IWorldMap;
import project.model.simulation.maps.MapType;

public class InitPresenter {
    @FXML
    private Spinner<Integer> mapWidth;
    @FXML
    private Spinner<Integer> mapHeight;
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
    private Spinner<Integer> genomeSize;

    @FXML
    private ComboBox<MapType> mapType;
    @FXML
    private ComboBox<MutationType> mutationType;
    @FXML
    private ComboBox<VegetationDynamicsType> vegetation;
    @FXML
    private ComboBox<AnimalBehaviorType> animalBehaviorType;
    @FXML
    private ComboBox<BreedingType> animalBreedingType;

    @FXML
    private CheckBox saveStatistics;


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

    @FXML
    private void setAdvisedConfiguration() {
        mapWidth.getValueFactory().setValue(15);
        mapHeight.getValueFactory().setValue(15);
        startPlants.getValueFactory().setValue(25);
        grassEnergy.getValueFactory().setValue(5);
        readyToBreedEnergy.getValueFactory().setValue(70);
        plantsPerDay.getValueFactory().setValue(8);
        animalNumber.getValueFactory().setValue(10);
        startEnergy.getValueFactory().setValue(120);
        animalsBreedEnergy.getValueFactory().setValue(50);
        moveEnergy.getValueFactory().setValue(1);
        genomeSize.getValueFactory().setValue(10);


        mapType.setValue(MapType.EARTH);
        mutationType.setValue(MutationType.RANDOMNESS);
        vegetation.setValue(VegetationDynamicsType.EQUATOR);
        animalBehaviorType.setValue(AnimalBehaviorType.PREDESTINATION);
        animalBreedingType.setValue(BreedingType.DEFAULT);
    }

    private boolean inputDataValidation(){
        return mapType.getValue() != null && vegetation.getValue() != null && animalBehaviorType.getValue() != null &&
                animalBreedingType.getValue() != null &&  mutationType.getValue() != null;
    }

    private Simulation createSimulation() {
        Modifications modifications = new Modifications(vegetation.getValue().createVegetationClass(),
                animalBehaviorType.getValue().createAnimalBehaviorClass(),
                animalBreedingType.getValue().createBreedingClass(),
                mutationType.getValue().createAnimalMutationClass());

        MapSettings mapSettings = new MapSettings(mapWidth.getValue(), mapHeight.getValue(), startEnergy.getValue(),
                readyToBreedEnergy.getValue(), moveEnergy.getValue(), grassEnergy.getValue(), genomeSize.getValue(),
                animalNumber.getValue(), startPlants.getValue(), animalsBreedEnergy.getValue(),
                plantsPerDay.getValue(), 0.2);

        IWorldMap map = mapType.getValue().createMapClass(mapSettings, modifications, new MapInit());
        Simulation simulation = new Simulation(map, modifications);
        simulation.setStoreStatistics(saveStatistics.isSelected());
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
