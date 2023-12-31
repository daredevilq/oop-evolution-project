package project.simulation.maps;

import project.Vector2D;
import project.simulation.config.MapSettings;
import project.simulation.config.Modifications;
import project.simulation.fetures.AnimalBehavior;
import project.simulation.worldelements.Animal;
import project.simulation.worldelements.Grass;

import javax.management.ValueExp;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface IWorldMap {



    MapSettings getMapSettings();

    Boundary getBoundary();

    Map<Vector2D, Grass> getMapPlants();
    List<Animal> getAnimalsList();
    boolean canMoveTo(Vector2D position);

    Vector2D getNextPosition(Vector2D newPositnion);

    void eatPlants();
    void breeding(Modifications modification);
    void moveAnimals();
    void deleteDeadAnimals(List<Animal> deadAnimals);

    void spawnPlants(Modifications modifications);
    Boundary getJungleBoundary();

    void decreaceAllAnimalsEnergy();
}
