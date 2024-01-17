package project.simulation.observer;

import project.simulation.Simulation;

import java.io.IOException;

public interface SimulationChangeListener {
        void simulationChanged(Simulation simulation);
}
