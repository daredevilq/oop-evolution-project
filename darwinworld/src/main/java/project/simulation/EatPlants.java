package project.simulation;

import project.Vector2D;
import project.simulation.worldelements.Animal;
import project.simulation.worldelements.AnimalComparator;
import project.simulation.worldelements.Grass;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EatPlants {
    private static final AnimalComparator ANIMAL_COMPARATOR = new AnimalComparator();

    public static List<Grass> eatPlants(Map<Vector2D, Grass> mapPlants, List<Animal> animalsList) {
        List<Grass> grassToRemove = new ArrayList<>();

        for (Grass grass : mapPlants.values()){
            Vector2D grassPosistion = grass.getPosition();
            List<Animal> animalsOnField = animalsAtPosition(grassPosistion, animalsList);

            if (!animalsOnField.isEmpty()) {
                Animal dominantAnimal = animalsOnField.stream()
                        .min(ANIMAL_COMPARATOR)
                        .orElse(null);
                dominantAnimal.eatPlant(grass.getEnergy());
                grassToRemove.add(grass);
            }
        }
        return grassToRemove;
    }

    public static List<Animal> animalsAtPosition(Vector2D position, List<Animal> animalsList) {
        return animalsList.stream()
                .filter(animal -> animal.getPosition().equals(position))
                .collect(Collectors.toList());
    }

}
