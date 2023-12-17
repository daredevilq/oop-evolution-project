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

public abstract class AbstractMap implements IWorldMap {

    private final MapSettings mapSettings;
    private static double JUNGLE_RATIO = 0.2;
    private final int width;
    private final int height;
    private final int grassEnergy;
    private final int moveEnergy;
    private final int startEnergy;
    private final int fieldsNumber;

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

    private Vector2D jungleLowerleft = new Vector2D(-1, -1);
    private Vector2D jungleUpperRight = new Vector2D(-1, -1);


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

        }

    }


    //Metoda umieszcza na mapie poczatkowa liczbe zwierzat i roslin
    public void mapInitialize() {
        randomlyPlaceAnimals(initialAnimalsNumber);
        spawnPlants();
    }



    //Metoda losowa umieszcza rosliny na mapie, roślin jest sqrt(liczba_pol_na_mapie)
    // bo nie bylo powiedziane ile ma ich byc, ewentualnei mozna dodac pole initialPlantsNumber i
    // podawdac na starcie
    // rosliny sa inicjowane losowo, na razie nie uwzglednilem prawdopodobienstwa bo to jest metoda do
    // inicjalizacji

    private void spawnPlants() {
        int minX = mapLowerLeft.getX();
        int maxX = mapUpperRight.getX();
        int minY = mapLowerLeft.getY();
        int maxY = mapUpperRight.getY();

        for (int i = 0; i < (int) Math.sqrt(fieldsNumber); i++) {
            Vector2D randomPosition = Vector2D.randomVector(minX, maxX, minY, maxY);

            while (mapAnimals.containsKey(randomPosition) || mapPlants.containsKey(randomPosition)) {
                randomPosition = Vector2D.randomVector(minX, maxX, minY, maxY);
            }

            List<Integer> randomGenesList = RandomGen.randIntList(0, 7, genomeSize);
            Grass grass = new Grass(randomPosition, grassEnergy);
            mapPlants.put(randomPosition, grass);
        }

    }


    //Metoda losowo umieszcza zwierzeta na mapie i daje im losowy kierunek i genotyp na poczatek
    // tak jak wyzej metoda jest tylko do inicjalizacji mapy, potem sie nie przyda

    private void randomlyPlaceAnimals(int initialAnimalsNumber) {
        int minX = mapLowerLeft.getX();
        int maxX = mapUpperRight.getX();
        int minY = mapLowerLeft.getY();
        int maxY = mapUpperRight.getY();

        for (int i = 0; i < initialAnimalsNumber; i++) {
            Vector2D randomPosition = Vector2D.randomVector(minX, maxX, minY, maxY);

            while (mapAnimals.containsKey(randomPosition)) {
                randomPosition = Vector2D.randomVector(minX, maxX, minY, maxY);
            }

            Animal animal = new Animal(this, randomPosition, MapDirection.NORTHEAST.rotate((int) (Math.random() * 8)), startEnergy, RandomGen.randIntList(0, 7, genomeSize));
            mapAnimals.put(randomPosition, animal);
        }
    }

    //Metoda fla kazdego zwierzecia w mapAnimals uruchamia metode move()
    //Metoda move() sprawdza czy zwierze ma wystarczajaco energii na ruch, jesli tak to je wykonuje
    // uzylem tutaj Iteratora zebym mogl usuwac elementy z listy w trakcie iteracji
    // zeby dodac elementy ktore zostaly zmienione musze uzyc tymczasowej mapy tempMap
    // ktorą na koncu przypisuje do mapy mapAnimals
    public void moveAnimals() {
        Iterator<Map.Entry<Vector2D, Animal>> iterator = mapAnimals.entrySet().iterator();
        Map<Vector2D, Animal> tempMap = new HashMap<>();
        while (iterator.hasNext()) {
            Map.Entry<Vector2D, Animal> entry = iterator.next();
            Animal animal = entry.getValue();

            if (animal.getEnergy() < moveEnergy) continue;

            Vector2D prevPosition = animal.getPosition();
            animal.move();
            Vector2D newPosition = animal.getPosition();

            if (!prevPosition.equals(newPosition)) {
                iterator.remove(); // Usuń element z mapy przy użyciu iteratora
                tempMap.put(newPosition, animal);
            }
        }
        mapAnimals.putAll(tempMap);
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


}