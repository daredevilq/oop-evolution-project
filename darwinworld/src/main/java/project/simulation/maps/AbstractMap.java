package project.simulation.maps;
import project.MapDirection;
import project.RandomGen;
import project.Vector2D;
import project.simulation.config.MapInit;
import project.simulation.config.MapSettings;
import project.simulation.config.Modifications;
import project.simulation.worldelements.Animal;
import project.simulation.worldelements.Grass;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractMap implements IWorldMap {
    private final MapSettings mapSettings;




    private final Boundary boundary;
    private Boundary jungleBoundary;


//    private Map<Vector2D, List<Animal>> mapAnimals = new HashMap<>();
    private List<Animal> animalsList = new ArrayList<>();
    private Map<Vector2D, Grass> mapPlants = new HashMap<>();






    public AbstractMap(MapSettings mapSettings,Modifications modifications, MapInit mapInitialize) {
        this.mapSettings = mapSettings;
        this.boundary = new Boundary(new Vector2D(0, 0), new Vector2D(mapSettings.width()-1, mapSettings.height()-1));

        this.jungleBoundary = Boundary.computeJungleBounds(mapSettings, boundary);
        animalsList = mapInitialize.randomlyPlaceAnimals(mapSettings, boundary);
        spawnPlants(modifications);
    }


    @Override
    public Map<Vector2D, Grass> getMapPlants() {

        return Collections.unmodifiableMap(mapPlants);
    }
    public List<Animal> getAnimalsList() {
        return Collections.unmodifiableList(animalsList);
    }
    @Override
    public Boundary getBoundary() {
        return boundary;
    }

    @Override
    public MapSettings getMapSettings() {
        return mapSettings;
    }

    @Override
    public Boundary getJungleBoundary() {
        return jungleBoundary;
    }


    public List<Animal> animalsAtPosition(Vector2D position) {
        return this.animalsList.stream()
                .filter(animal -> animal.getPosition().equals(position))
                .collect(Collectors.toList());
    }


    @Override
    public void eatPlants(){

        List<Grass> grassToRemove = new ArrayList<>();

        for (Grass grass : mapPlants.values()){

            Vector2D grassPosistion = grass.getPosition();
            List<Animal> animalsOnField = animalsAtPosition(grassPosistion);

            if (!animalsOnField.isEmpty()) {
                Animal dominantAnimal = animalsOnField.stream() // zwróć kandydata do zjedzenia rośliny
                        .sorted(Comparator.comparingInt(Animal::getEnergy).reversed()
                                .thenComparing(Comparator.comparingLong(Animal::getAge).reversed())
                                .thenComparing(Comparator.comparingInt(Animal::getChildrenCounter).reversed())
                                .thenComparing(animal -> Math.random()))
                        .findFirst()
                        .orElse(null);

                if (dominantAnimal != null) {
                    dominantAnimal.eatPlant(grass.getEnergy());
                    grassToRemove.add(grass);
                }

            }
        }

        for (Grass grass : grassToRemove){ // usun trawe z mapPlant po gdy zostala zjedzona
            mapPlants.remove(grass.getPosition());

        }

    }
    @Override
    public void moveAnimals(){
        if (!animalsList.isEmpty()) {
            for (Animal animal : this.animalsList)
                moveAnimal(animal);
        }else
            System.out.println("pusta lista");
    }

    public void moveAnimal(Animal animal) {
        Vector2D oldPosition = animal.getPosition();
        if (animal.getEnergy() >= mapSettings.moveEnergy())
            animal.move(this);
    }

    @Override
    public void deleteDeadAnimals(List<Animal> deadAnimalsList) {
        List<Animal> newDeadAnimals = animalsList.stream()
                .filter(animal -> animal.getEnergy() <= 0)
                .toList();

        animalsList.removeIf(newDeadAnimals::contains);
        //animalsList.removeIf(animal -> newDeadAnimals.contains(animal)); to jest to samo co wyzej
        deadAnimalsList.addAll(newDeadAnimals);
    }

    @Override
    public abstract boolean canMoveTo(Vector2D position);

    @Override
    public abstract Vector2D getNextPosition(Vector2D newPositnion);


    @Override
    public void spawnPlants(Modifications modification){
        modification.spawningPlants().spawnAllPlants(this, mapPlants, mapSettings);
    }

    @Override
    public void breeding(Modifications modification){
      this.animalsList = modification.breeding().breed(animalsList, mapSettings.startEnergy());
    }

    public boolean isOccupied(Vector2D position){
        return !animalsAtPosition(position).isEmpty();
    }

    public boolean isOccupiedByPlant(Vector2D position){
        return mapPlants.containsKey(position);
    }

    @Override
    public void decreaceAllAnimalsEnergy(){
        for (Animal animal : animalsList){
            animal.decreaseEnergyBy1();
        }
    }
}