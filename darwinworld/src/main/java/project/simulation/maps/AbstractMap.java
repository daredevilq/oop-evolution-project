package project.simulation.maps;

import project.Vector2D;
import project.simulation.config.MapInit;
import project.simulation.config.MapSettings;
import project.simulation.config.Modifications;
import project.simulation.fetures.MapAreaType;
import project.simulation.worldelements.Animal;
import project.simulation.worldelements.Grass;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractMap implements IWorldMap {
    private final MapSettings mapSettings;
    private final Modifications modifications;
    private final Boundary boundary;
    private final Boundary jungleBoundary;
    private List<Animal> animalsList = new ArrayList<>();
    private Map<Vector2D, Grass> mapPlants = new HashMap<>();
    private Map<Vector2D, MapAreaType> freePlaces = new HashMap<>();

    public AbstractMap(MapSettings mapSettings,Modifications modifications, MapInit mapInitialize) {
        this.mapSettings = mapSettings;
        this.modifications = modifications;
        this.boundary = new Boundary(new Vector2D(0, 0), new Vector2D(mapSettings.width()-1, mapSettings.height()-1));

        this.jungleBoundary = Boundary.computeJungleBounds(mapSettings, boundary);
        animalsList = mapInitialize.randomlyPlaceAnimals(mapSettings, boundary);
        spawnInitPlants();
        computeFreePlaces();
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

    public Map<Vector2D, MapAreaType> getFreePlaces() {
        return Collections.unmodifiableMap(freePlaces);
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

        for (Grass grass : grassToRemove){ // usun trawe z mapPlant po gdy zostala zjedzona i dodaje do wolnych miejsc
            mapPlants.remove(grass.getPosition());
            MapAreaType type = (grass.getPosition().precedes(jungleBoundary.upperRightCorner()) && grass.getPosition().follows(jungleBoundary.lowerLeftCorner())) ? MapAreaType.JUNGLE : MapAreaType.NORMAL;
            freePlaces.put(grass.getPosition(), type);
        }

    }
    @Override
    public void moveAnimals() {
        if (animalsList.isEmpty()) {
            throw new IllegalStateException("The animalsList is empty.");
        }
        for (Animal animal : this.animalsList) {
            moveAnimal(animal);
        }
    }


    public void moveAnimal(Animal animal) {
        Vector2D oldPosition = animal.getPosition();
        if (animal.getEnergy() >= mapSettings.moveEnergy())
            animal.move(this, modifications.animalBehavior());
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
    public void spawnPlants(){
        modifications.spawningPlants().spawnAllPlants(this, mapPlants, mapSettings.plantsPerDay(), mapSettings.grassEnergy());
    }
    public void spawnInitPlants(){
        modifications.spawningPlants().spawnAllPlants(this, mapPlants, mapSettings.startPlants(), mapSettings.grassEnergy());
    }

    @Override
    public void breeding(Modifications modification){
      this.animalsList = modification.breeding().breed(animalsList, mapSettings.startEnergy(), mapSettings.breedEnergy(), modification.animalMutation());
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

    public void computeFreePlaces(){
        freePlaces.clear();
        for (int i = 0; i < mapSettings.width(); i++) {
            for (int j = 0; j < mapSettings.height(); j++) {
                Vector2D position = new Vector2D(i,j);
                if (!mapPlants.containsKey(position)){
                    MapAreaType type = (position.precedes(jungleBoundary.upperRightCorner()) && position.follows(jungleBoundary.lowerLeftCorner())) ? MapAreaType.JUNGLE : MapAreaType.NORMAL;
                    freePlaces.put(position, type);
                }
            }
        }
    }
    public void removeFreePlace(Vector2D position){
        freePlaces.remove(position);
    }

    public void addAnimal(Animal animal){
        animalsList.add(animal);
    }

    public void addPlant(Grass grass){
        mapPlants.put(grass.getPosition(), grass);
    }
}
