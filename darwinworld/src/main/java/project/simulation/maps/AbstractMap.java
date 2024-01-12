package project.simulation.maps;

import project.MapVisualizer;
import project.Vector2D;
import project.simulation.EatPlants;
import project.simulation.config.MapInit;
import project.simulation.config.MapSettings;
import project.simulation.config.Modifications;
import project.simulation.fetures.MapAreaType;
import project.simulation.worldelements.Animal;
import project.simulation.worldelements.AnimalComparator;
import project.simulation.worldelements.Grass;

import javax.imageio.plugins.tiff.GeoTIFFTagSet;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractMap implements IWorldMap {
    private final MapSettings mapSettings;
    private final Modifications modifications;
    private final Boundary boundary;
    private final Boundary jungleBoundary;
    private List<Animal> animalsList;
    private List<Animal> deadAnimals = new ArrayList<>();
    private Map<Vector2D, Grass> mapPlants = new HashMap<>();
    private Set<Vector2D> freePlaces;
    private MapVisualizer mapVisualizer;
    public AbstractMap(MapSettings mapSettings,Modifications modifications, MapInit mapInitialize) {

        this.mapSettings = mapSettings;
        this.modifications = modifications;
        this.boundary = new Boundary(new Vector2D(0, 0), new Vector2D(mapSettings.width()-1, mapSettings.height()-1));

        this.jungleBoundary = Boundary.computeJungleBounds(mapSettings, boundary);
        animalsList = mapInitialize.randomlyPlaceAnimals(mapSettings, boundary);


        freePlaces = mapInitialize.computeFreePlacesForPlants(mapPlants, mapSettings.width(), mapSettings.height());
        modifications.spawningPlants().spawnAllPlants(this, mapPlants, mapSettings.startPlants(), mapSettings.grassEnergy());


        mapVisualizer = new MapVisualizer(this);

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

    @Override
    public Set<Vector2D> getFreePlaces() {
        return Collections.unmodifiableSet(freePlaces);
    }

    @Override
    public List<Animal> getDeadAnimals() {
        return deadAnimals;
    }

    @Override
    public int freePlacesOnMap(){
        int animalsNotOnPlant = 0;
        for (Animal animal: animalsList) {
            if (freePlaces.contains(animal.getPosition())){
                animalsNotOnPlant++;
            }
        }
        return freePlaces.size() - animalsNotOnPlant;
    }

    @Override
    public void eatPlants(){
        List<Grass> grassToRemove = EatPlants.eatPlants(mapPlants, animalsList);

        for (Grass grass : grassToRemove){ // usun trawe z mapPlant po gdy zostala zjedzona i dodaje do wolnych miejsc
            mapPlants.remove(grass.getPosition());
            MapAreaType type = (grass.getPosition().precedes(jungleBoundary.upperRightCorner()) && grass.getPosition().follows(jungleBoundary.lowerLeftCorner())) ? MapAreaType.JUNGLE : MapAreaType.NORMAL;
            freePlaces.add(grass.getPosition());
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
        if (animal.getEnergy() >= mapSettings.moveEnergy()){
        animal.move(this, modifications.animalBehavior());}
    }

    @Override
    public void deleteDeadAnimals() {
        List<Animal> newDeadAnimals = animalsList.stream()
                .filter(animal -> animal.getEnergy() <= 0)
                .toList();

        animalsList.removeIf(newDeadAnimals::contains);
        //animalsList.removeIf(animal -> newDeadAnimals.contains(animal)); to jest to samo co wyzej
        this.deadAnimals.addAll(newDeadAnimals);
    }

    @Override
    public abstract boolean canMoveTo(Vector2D position);

    @Override
    public abstract Vector2D getNextPosition(Vector2D newPositnion);


    @Override
    public void spawnPlants(){
        modifications.spawningPlants().spawnAllPlants(this, mapPlants, mapSettings.plantsPerDay(), mapSettings.grassEnergy());
    }

    @Override
    public void breeding(Modifications modification){
      this.animalsList = modification.breeding().breed(animalsList, mapSettings.breedEnergy(), mapSettings.readyToBreedEnergy(),modification.animalMutation());
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

    @Override
    public void updateDailyAnimalStats(){
        for (Animal animal : animalsList){
            animal.updateDailyStatsOnAnimal();
        }
    }
    //pomocnicze metody dla MapVisualizer
    public boolean isOccupied(Vector2D position){
        return animalsList.stream().anyMatch(animal -> animal.getPosition().equals(position)) || mapPlants.containsKey(position);
    }

    public Object objectAt(Vector2D position){
      for (Animal animal : animalsList){
          if (animal.getPosition().equals(position)){
              return animal;
          }
      }
        return mapPlants.get(position);
    }

    public String toString() {
        return mapVisualizer.draw(this.boundary.lowerLeftCorner(), this.boundary.upperRightCorner());
    }
}
