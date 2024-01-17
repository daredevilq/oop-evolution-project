package project.simulation.worldelements;

import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class WorldElementBox extends StackPane {
    private static final Map<String, Image> imageCache = new HashMap<>();

    public WorldElementBox(IWorldElement worldElement, int size) throws IllegalArgumentException {
        String filepath = worldElement.getResourceName();
        Image image = getCachedImage(filepath);

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);

        if (worldElement instanceof Animal) {
            VBox vbox = setAnimalHealthBar((Animal) worldElement, imageView);

            this.getChildren().add(vbox);
        } else {
            this.getChildren().add(imageView);
        }

        this.setAlignment(Pos.CENTER);
    }

    private Image getCachedImage(String filepath) {
        if (imageCache.containsKey(filepath)) {
            return imageCache.get(filepath);
        } else {
            Image image = new Image(filepath);
            imageCache.put(filepath, image);
            return image;
        }
    }

    private VBox setAnimalHealthBar(Animal animal, ImageView imageView) {
        ProgressBar healthBar = new ProgressBar();

        double energy = animal.getEnergy();

        if (energy < 30) {
            healthBar.setStyle("-fx-accent: red;");
        } else if (energy < 70) {
            healthBar.setStyle("-fx-accent: yellow;");
        } else {
            healthBar.setStyle("-fx-accent: green;");
        }

        healthBar.setProgress(animal.getEnergy());

        VBox vbox = new VBox(imageView, healthBar);
        vbox.setAlignment(Pos.CENTER);

        return vbox;
    }
}
