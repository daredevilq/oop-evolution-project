package project.simulation.observer;

import project.simulation.Simulation;

import java.util.ArrayList;
import java.util.List;

public class SubscribersManager {
    private final List<SimulationChangeListener> subscribers = new ArrayList<>();

    public void addSubscriber(SimulationChangeListener subscriber){
        subscribers.add(subscriber);
    }

    public void removeSubscriber(SimulationChangeListener subscriber){
        subscribers.remove(subscriber);
    }

    public void notifySubscribers(Simulation simulation) {
        for (SimulationChangeListener subscriber : subscribers) {
            subscriber.simulationChanged(simulation);
        }
    }

}
