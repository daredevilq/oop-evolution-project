package project.model.simulation.maps;

import project.model.simulation.EatPlants;
import project.model.simulation.worldelements.Vector2D;
import project.model.simulation.config.MapInit;
import project.model.simulation.config.MapSettings;
import project.model.simulation.config.Modifications;
import project.model.simulation.worldelements.Animal;
import project.model.simulation.worldelements.Grass;
import project.model.simulation.worldelements.IWorldElement;

import java.util.*;

public abstract class AbstractMap implements IWorldMap {
    private final MapSettings mapSettings;
    private final Modifications modifications;
    private final Boundary boundary;
    private final Boundary jungleBoundary;
    private List<Animal> animalsList;
    private List<Animal> deadAnimals = new ArrayList<>();
    private Map<Vector2D, Grass> mapPlants = new HashMap<>();
    private Set<Vector2D> freePlaces;
    public AbstractMap(MapSettings mapSettings,Modifications modifications, MapInit mapInitialize) {

        this.mapSettings = mapSettings;
        this.modifications = modifications;
        this.boundary = new Boundary(new Vector2D(0, 0), new Vector2D(mapSettings.width()-1, mapSettings.height()-1));

        this.jungleBoundary = Boundary.computeJungleBounds(mapSettings.height(), mapSettings.JUNGLE_MAP_RATIO(), boundary);
        animalsList = mapInitialize.randomlyPlaceAnimals(mapSettings, boundary);


        freePlaces = mapInitialize.computeFreePlacesForPlants(mapPlants, mapSettings.width(), mapSettings.height());
        modifications.spawningPlants().spawnAllPlants(this, mapPlants, mapSettings.startPlants(), mapSettings.grassEnergy());
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
    public Modifications getModifications() {
        return modifications;
    }

    @Override
    public int freePlacesOnMap(){
        Set<Vector2D> animalsPositionsNotOnGrass = new HashSet<>();
        for (Animal animal: animalsList) {
            Vector2D position = animal.getPosition();
            if (freePlaces.contains(position)) {
                animalsPositionsNotOnGrass.add(position);
            }
        }
        return freePlaces.size() - animalsPositionsNotOnGrass.size();
    }

    @Override
    public void eatPlants(){
        List<Grass> grassToRemove = EatPlants.eatPlants(mapPlants, animalsList);

        for (Grass grass : grassToRemove){ // usun trawe z mapPlant po gdy zostala zjedzona i dodaje do wolnych miejsc
            mapPlants.remove(grass.getPosition());
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
        if (animal.getEnergy() >= mapSettings.moveEnergy())
            animal.move(this, modifications.animalBehavior());
    }

    @Override
    public void deleteDeadAnimals() {
        List<Animal> newDeadAnimals = animalsList.stream()
                .filter(animal -> animal.getEnergy() <= 0)
                .toList();

        animalsList.removeIf(newDeadAnimals::contains);
        for (Animal deadAnimal : newDeadAnimals){
            deadAnimal.setAlive(false);
            this.deadAnimals.add(deadAnimal);
        }
    }

    @Override
    public abstract boolean canMoveTo(Vector2D position);

    @Override
    public abstract Vector2D getNextPosition(Vector2D newPosition);


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

    public boolean isOccupied(Vector2D position){
        return animalsList.stream().anyMatch(animal -> animal.getPosition().equals(position)) || mapPlants.containsKey(position);
    }

    public IWorldElement objectAt(Vector2D position){
      for (Animal animal : animalsList){
          if (animal.getPosition().equals(position)){
              return animal;
          }
      }
        return mapPlants.get(position);
    }

}
