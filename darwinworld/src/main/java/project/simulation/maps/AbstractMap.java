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


//    private final VegetationDynamicsType vegetationDynamicsType;
//    private final MutationType mutationType;
//    private final AnimalBehavior animalBehavior;
//
//    private Vector2D jungleLowerleft = new Vector2D(0, 0);
//    private Vector2D jungleUpperRight = new Vector2D(0, 0);
//
    private long plantsCount = 0;
    private long dayNum = 0;
    private long animalsCount;
    private long diedAnimalsCount = 0;


//    private final SpawningPlants spawnPlants = SpawnPlantWithJungle;



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

    public Map<Vector2D, Animal> getMapAnimals() {
        return Collections.unmodifiableMap(mapAnimals);
    }
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


    public void spawnPlants() {
        int fieldsNumber = (boundary.upperRightCorner().getX() - boundary.lowerLeftCorner().getX()) * (boundary.upperRightCorner().getY() - boundary.lowerLeftCorner().getY());

        for (int i = 0; i < (int) Math.sqrt(fieldsNumber) & plantsCount < fieldsNumber; i++) {

            Vector2D position = mapSettings.spawningPlants().spawnPlant();

            Grass grass = new Grass(position, mapSettings.grassEnergy());
            mapPlants.put(position, grass);
            plantsCount += 1;
        }
    }




    public void moveAnimals(Animal animal) {
        if (animal.getEnergy() > mapSettings.moveEnergy())
            animal.move(this);
    }

    public List<Animal> deleteDeadAnimals() {
        int allAnimals = mapAnimals.size();

        List<Animal> aliveAnimals = mapAnimals.values().stream()
                .filter(animal -> animal.getEnergy() >= mapSettings.moveEnergy())
                .collect(Collectors.toList());

        mapAnimals = aliveAnimals.stream()
                .collect(Collectors.toMap(Animal::getPosition, Function.identity()));


        int alivedAnimals = mapAnimals.size();
        diedAnimalsCount += (allAnimals - alivedAnimals);
        animalsCount = alivedAnimals;

        return aliveAnimals;
    }



    public void breeding() {
        for (Vector2D position : mapAnimals.keySet()) {
            List<Animal> animalsOnField = mapAnimals.values().stream()
                    .filter(animal -> animal.getPosition().equals(position))
                    .collect(Collectors.toList());

            if (animalsOnField.size() >= 2) { // szukamy pola w mapie gdie mamy 2 zwierzęta
                List<Animal> sortedAnimals = animalsOnField.stream() // sortujemy by wybrać tylko 2
                        .sorted(Comparator.comparingInt(Animal::getEnergy).reversed()
                                .thenComparing(Comparator.comparingLong(Animal::getAge).reversed())
                                .thenComparing(Comparator.comparingInt(Animal::getChildrenCounter).reversed())
                                .thenComparing(animal -> Math.random()))
                        .collect(Collectors.toList());

                Animal parent1 = sortedAnimals.get(0);
                Animal parent2 = sortedAnimals.get(1);

                // warunki na rozmnażanie
                int startEnergy = mapSettings.startEnergy();
                if (parent1.getEnergy() >= startEnergy/2 && parent2.getEnergy() >= startEnergy/2) {

                    List<Integer> childGenotype = parent1.reproduce(parent2); // tworzymy genotyp dziecka
                    Animal child = new Animal(mapSettings, parent1.getPosition(), MapDirection.NORTHEAST.rotate(RandomGen.randInt(0, 7)), startEnergy, childGenotype);
                    mapAnimals.put(child.getPosition(), child);
                    animalsCount += 1;

                    double energyRatio = (double) parent1.getEnergy() / (parent1.getEnergy() + parent2.getEnergy());
                    parent1.changeStatsAfterBreeding((int) energyRatio * parent1.getEnergy());
                    parent1.changeStatsAfterBreeding(startEnergy - (int) energyRatio * parent2.getEnergy());
                }
            }
        }
    }
}