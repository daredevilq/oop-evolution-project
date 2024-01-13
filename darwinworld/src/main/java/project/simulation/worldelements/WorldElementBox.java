package project.simulation.worldelements;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

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

        this.getChildren().add(imageView);
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
}
