package project.simulation.maps;
import project.MapDirection;
import project.RandomGen;
import project.Vector2D;
import project.simulation.config.MapSettings;
import project.simulation.fetures.AnimalBehavior;
import project.simulation.fetures.MapType;
import project.simulation.fetures.MutationType;
import project.simulation.fetures.VegetationDynamicsType;
import project.simulation.worldelements.Animal;
import project.simulation.worldelements.Grass;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractMap implements IWorldMap {

    private final MapSettings mapSettings;
    private static double JUNGLE_RATIO = 0.2;
    private final int width;
    private final int height;
    private final int grassEnergy;
    private final int moveEnergy;
    private final int startEnergy;
    private final int fieldsNumber;

//    private final int fieldsNumberInJungle = 0;

    private final int initialAnimalsNumber;

    private Map<Vector2D, Animal> mapAnimals = new HashMap<>();
    private Map<Vector2D, Grass> mapPlants = new HashMap<>();

    protected Vector2D mapLowerLeft = new Vector2D(0, 0);
    protected Vector2D mapUpperRight;

    private long dayNum = 0;
    private long animalsCount;
    private long diedAnimalsCount = 0;
    private long plantsCount = 0;
    private long diedAnimalsSumDaysAlive = 0;
    private final int genomeSize;
    //fetures
    private final MapType mapType;
    private final VegetationDynamicsType vegetationDynamicsType;
    private final MutationType mutationType;
    private final AnimalBehavior animalBehavior;

    private Vector2D jungleLowerleft = new Vector2D(0, 0);
    private Vector2D jungleUpperRight = new Vector2D(0, 0);


    public AbstractMap(MapSettings mapSettings) {
        this.mapSettings = mapSettings;
        this.width = mapSettings.width();
        this.height = mapSettings.height();
        this.grassEnergy = mapSettings.grassEnergy();
        this.moveEnergy = mapSettings.moveEnergy();
        this.startEnergy = mapSettings.startEnergy();
        this.initialAnimalsNumber = mapSettings.initialAnimalsNumber();
        this.genomeSize = mapSettings.genomeSize();

        this.mapUpperRight = new Vector2D(width - 1, height - 1);

        this.fieldsNumber = (mapUpperRight.getX() - mapLowerLeft.getX() + 1)
                * (mapUpperRight.getY() - mapLowerLeft.getY()) + 1;


        this.mapType = mapSettings.mapType();
        this.vegetationDynamicsType = mapSettings.vegetationDynamicsType();
        this.mutationType = mapSettings.mutationType();
        this.animalBehavior = mapSettings.animalBehavior();

        //Obliczam współrzędne dżungli kiedy mamy opcję z równikiem
        if (vegetationDynamicsType == VegetationDynamicsType.EQUATOR) {
            int jungleWidth = (int) Math.round(width * JUNGLE_RATIO);
            if (jungleWidth % 2 != width % 2) jungleWidth += 1;

            int jungleMiddle = width / 2;
            this.jungleLowerleft = new Vector2D(mapLowerLeft.getX(), jungleMiddle - jungleWidth / 2 );

            this.jungleUpperRight = new Vector2D(mapUpperRight.getX(), jungleMiddle + jungleWidth / 2 );

//            this.fieldsNumberInJungle = (jungleUpperRight.getX() - jungleLowerleft.getX() + 1)
//                    * (jungleUpperRight.getY() - jungleLowerleft.getY()) + 1;
        }

    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public int getMoveEnergy() {
        return moveEnergy;
    }

    public AnimalBehavior getAnimalBehavior() {
        return animalBehavior;
    }

    public Map<Vector2D, Animal> getMapAnimals() {
        return Collections.unmodifiableMap(mapAnimals);
    }
    public Map<Vector2D, Grass> getMapPlants() {
        return Collections.unmodifiableMap(mapPlants);
    }



    //Metoda umieszcza na mapie poczatkowa liczbe zwierzat i roslin
    public void mapInitialize() {
        randomlyPlaceAnimals(initialAnimalsNumber);
        spawnPlants();
    }


    // tu trzeba jeszcze zrobić warunek jak są wolne miejsca to szukaj do momentu aż są jeszcze jakieś wolne miejsca
    // w dodatku trzeba zrobić tak by trawa rosła częściej w jungli
    public void spawnPlants() {

        for (int i = 0; i < (int) Math.sqrt(fieldsNumber) & plantsCount < fieldsNumber; i++) {

            Vector2D position;
            // tu jeszcze trzeba warunek że w jungli są jeszcze wolne pola
            if (new Random().nextBoolean()){
                // losujemy z jungli
                position =  RandomGen.randomFreePlace(mapPlants, jungleLowerleft, jungleUpperRight);
            } else {
                // losujemy z całej mapy - możliwe że wylosuje się jungla
                position = RandomGen.randomFreePlace(mapPlants, mapLowerLeft, mapUpperRight);
            }

            Grass grass = new Grass(position, grassEnergy);
            mapPlants.put(position, grass);
            plantsCount += 1;
        }
    }

    public void randomlyPlaceAnimals(int initialAnimalsNumber) {
        for (int i = 0; i < initialAnimalsNumber; i++) {
            Vector2D position =  RandomGen.randomFreePlace(mapAnimals, mapLowerLeft, mapUpperRight);
            Animal animal = new Animal(this, position, MapDirection.NORTHEAST.rotate((int) (Math.random() * 8)), startEnergy, RandomGen.randIntList(0, 7, genomeSize));
            mapAnimals.put(position, animal);
            animalsCount += 1;
        }
    }


    public void moveAnimals(Animal animal) {
        if (animal.getEnergy() > moveEnergy)
            animal.move();
    }

    public List<Animal> deleteDeadAnimals() {
        int allAnimals = mapAnimals.size();

        List<Animal> aliveAnimals = mapAnimals.values().stream()
                .filter(animal -> animal.getEnergy() >= moveEnergy)
                .collect(Collectors.toList());

        mapAnimals = aliveAnimals.stream()
                .collect(Collectors.toMap(Animal::getPosition, Function.identity()));


        int alivedAnimals = mapAnimals.size();
        diedAnimalsCount += (allAnimals - alivedAnimals);
        animalsCount = alivedAnimals;

        return aliveAnimals;
    }

    public void eatPlants(){
        List<Grass> grassToRemove = new ArrayList<>();

        for (Grass grass : mapPlants.values()){
            if (mapAnimals.containsKey(grass.getPosition())) {
                List<Animal> animalsOnField = mapAnimals.values().stream() // zwróć zwierzęta które stoja na danym polu
                        .filter(animal -> animal.getPosition().equals(grass.getPosition()))
                        .collect(Collectors.toList());

                Animal dominantAnimal = animalsOnField.stream() // zwróć kandydata do zjedzenia rośliny
                        .sorted(Comparator.comparingInt(Animal::getEnergy).reversed()
                                .thenComparing(Comparator.comparingLong(Animal::getAge).reversed())
                                .thenComparing(Comparator.comparingInt(Animal::getChildrenCounter).reversed())
                                .thenComparing(animal -> Math.random()))
                        .findFirst()
                        .orElse(null);

                if (dominantAnimal != null){
                    dominantAnimal.eatPlant(grass.getEnergy());
                    grassToRemove.add(grass);
                }
            }
        }
        for (Grass grass : grassToRemove){ // usun trawe z mapPlant po gdy zostala zjedzona
            mapPlants.remove(grass.getPosition());
            plantsCount -= 1;
        }
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
                if (parent1.getEnergy() >= startEnergy/2 && parent2.getEnergy() >= startEnergy/2) {

                    List<Integer> childGenotype = parent1.reproduce(parent2); // tworzymy genotyp dziecka
                    Animal child = new Animal(this, parent1.getPosition(), MapDirection.NORTHEAST.rotate((int) (Math.random() * 8)), this.startEnergy, childGenotype);
                    mapAnimals.put(child.getPosition(), child);
                    animalsCount += 1;

                    double energyRatio = (double) parent1.getEnergy() / (parent1.getEnergy() + parent2.getEnergy());
                    parent1.changeStatsAfterBreeding((int) energyRatio * parent1.getEnergy());
                    parent1.changeStatsAfterBreeding(this.startEnergy - (int) energyRatio * parent2.getEnergy());
                }
            }
        }
    }
}