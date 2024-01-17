package project.model.simulation.maps;

import project.model.simulation.worldelements.Vector2D;
import project.model.simulation.config.MapSettings;
import project.model.simulation.config.Modifications;
import project.model.simulation.worldelements.Animal;
import project.model.simulation.worldelements.Grass;
import project.model.simulation.worldelements.IWorldElement;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IWorldMap {



    MapSettings getMapSettings();

    Boundary getBoundary();

    List<Animal> getDeadAnimals();

    Map<Vector2D, Grass> getMapPlants();
    List<Animal> getAnimalsList();
    boolean canMoveTo(Vector2D position);

    Vector2D getNextPosition(Vector2D newPosition);

    void eatPlants();
    void breeding(Modifications modification);
    void moveAnimals();
    void deleteDeadAnimals();

    void spawnPlants();
    Boundary getJungleBoundary();

    Set<Vector2D> getFreePlaces();

    int freePlacesOnMap();

    void removeFreePlace(Vector2D randomPosition);

    void addAnimal(Animal animal);

    boolean isOccupied(Vector2D currentPosition);

    IWorldElement objectAt(Vector2D currentPosition);

    void updateDailyAnimalStats();
    Modifications getModifications();

    void addPlant(Grass grass);

}
