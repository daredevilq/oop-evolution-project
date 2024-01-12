package project.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import project.presenter.SimulationPresenter;
import project.simulation.Simulation;

import java.io.IOException;


public class SimulationApp extends Application {

    private final Simulation simulation;

    public SimulationApp(Simulation simulation) {
        super();
        this.simulation = simulation;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();
        SimulationPresenter presenter = loader.getController();



        presenter.setSimulation(simulation);
        simulation.addSubscriber(presenter);

        configureStage(primaryStage, viewRoot);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            try {
                presenter.closeStatisticFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }


}