package project.simulation.maps;
import project.MapDirection;
import project.RandomGen;
import project.Vector2D;
import project.simulation.config.MapInit;
import project.simulation.config.MapSettings;
import project.simulation.worldelements.Animal;
import project.simulation.worldelements.Grass;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractMap implements IWorldMap {

    private final MapSettings mapSettings;
    private final MapInit mapInitialize;



    private final Boundary boundary;
//    private static double JUNGLE_RATIO = 0.2;
//    private final int fieldsNumber;


    private Map<Vector2D, List<Animal>> mapAnimals = new HashMap<>();
    private Map<Vector2D, Grass> mapPlants = new HashMap<>();


//    private long diedAnimalsSumDaysAlive = 0;
//    private final int genomeSize;


//    private Vector2D jungleLowerleft = new Vector2D(0, 0);
//    private Vector2D jungleUpperRight = new Vector2D(0, 0);
//
    private long plantsCount = 0;
    private long dayNum = 0;
    private long animalsCount;
    private long diedAnimalsCount = 0;



    public AbstractMap(MapSettings mapSettings, MapInit mapInitialize) {
        this.mapSettings = mapSettings;
        this.mapInitialize = mapInitialize;

        this.boundary = new Boundary(new Vector2D(0, 0), new Vector2D(mapSettings.width()-1, mapSettings.height()-1));

//        randomlyPlaceAnimals(mapInitialize.initialAnimalsNumber());
        mapAnimals = this.mapInitialize.randomlyPlaceAnimals(mapSettings, boundary);
        spawnPlants();

//        this.vegetationDynamicsType = mapSettings.vegetationDynamicsType();
//        this.mutationType = mapSettings.mutationType();
//        this.animalBehavior = mapSettings.animalBehavior();
//
//        //Obliczam współrzędne dżungli kiedy mamy opcję z równikiem
//        if (vegetationDynamicsType == VegetationDynamicsType.EQUATOR) {
//            int jungleWidth = (int) Math.round(width * JUNGLE_RATIO);
//            if (jungleWidth % 2 != width % 2) jungleWidth += 1;
//
//            int jungleMiddle = width / 2;
//            this.jungleLowerleft = new Vector2D(mapLowerLeft.getX(), jungleMiddle - jungleWidth / 2 );
//
//            this.jungleUpperRight = new Vector2D(mapUpperRight.getX(), jungleMiddle + jungleWidth / 2 );

//            this.fieldsNumberInJungle = (jungleUpperRight.getX() - jungleLowerleft.getX() + 1)
//                    * (jungleUpperRight.getY() - jungleLowerleft.getY()) + 1;
//        }

    }


    @Override
    public Map<Vector2D, Grass> getMapPlants() {
        return Collections.unmodifiableMap(mapPlants);
    }

    @Override
    public Boundary getBoundary() {
        return boundary;
    }

    @Override
    public MapSettings getMapSettings() {
        return mapSettings;
    }


//    public void spawnPlants() {
//        int fieldsNumber = (boundary.upperRightCorner().getX() - boundary.lowerLeftCorner().getX()) * (boundary.upperRightCorner().getY() - boundary.lowerLeftCorner().getY());
//
//        for (int i = 0; i < (int) Math.sqrt(fieldsNumber) & plantsCount < fieldsNumber; i++) {
//
//            Vector2D position = mapSettings.spawningPlants().spawnPlant();
//
//            Grass grass = new Grass(position, mapSettings.grassEnergy());
//            mapPlants.put(position, grass);
//            plantsCount += 1;
//        }
//    }

    public void placeAnimal(Animal animal){
        if (canMoveTo(animal.getPosition())){
            List<Animal> animalsAtPosition = mapAnimals.computeIfAbsent(animal.getPosition(), k -> new ArrayList<>());
            animalsAtPosition.add(animal);
        }
    }

    public void removeAnimal(Animal animal){
        List<Animal> animalsAtPosition = mapAnimals.computeIfAbsent(animal.getPosition(), k -> new ArrayList<>());
        animalsAtPosition.remove(animal);
    }

    //    public void eatPlants(HashMap <Vector2D, Animal> mapAnimals, HashMap <Vector2D, Grass> mapPlants){
    public int eatPlants(Map<Vector2D, List<Animal>> mapAnimals, Map<Vector2D, Grass> mapPlants){

        int eatedPlants = 0;
        List<Grass> grassToRemove = new ArrayList<>();

        for (Grass grass : mapPlants.values()){

            Vector2D grassPosistion = grass.getPosition();

            if (mapAnimals.containsKey(grassPosistion) & mapAnimals.containsKey(grassPosistion)) {
                List<Animal> animalsOnField = mapAnimals.get(grassPosistion);
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
        }

        for (Grass grass : grassToRemove){ // usun trawe z mapPlant po gdy zostala zjedzona
            mapPlants.remove(grass.getPosition());
            eatedPlants += 1;
        }
        return eatedPlants;
    }


    public void moveAnimal(Animal animal) {
        Vector2D oldPosition = animal.getPosition();

        if (animal.getEnergy() > mapSettings.moveEnergy())
            animal.move(this);

        Vector2D newPositon = animal.getPosition();

        if (!newPositon.equals(oldPosition)){
            removeAnimal(animal);
            placeAnimal(animal);
        }
    }


    public List<Animal> deleteDeadAnimals(List<Animal> animals) {
        int allAnimals = animals.size();

        List<Animal> deadAnimals = animals.stream()
                .filter(animal -> animal.getEnergy() < mapSettings.moveEnergy())
                .collect(Collectors.toList());


        for (Animal deadAnimal : deadAnimals){
            removeAnimal(deadAnimal);
            animals.remove(deadAnimal);
        }




        int alivedAnimals = mapAnimals.size();
        diedAnimalsCount += (allAnimals - alivedAnimals);
        animalsCount = alivedAnimals;

        return aliveAnimals;
    }

    @Override
    public abstract boolean canMoveTo(Vector2D position);

    @Override
    public abstract Vector2D getNextPosition(Vector2D newPositnion);

}