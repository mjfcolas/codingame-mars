package fr.li212.codingame.mars.simulator.engine;

import fr.li212.codingame.mars.domain.entities.trajectory.ParametricCurve;
import fr.li212.codingame.mars.simulator.engine.mechanics.AugmentedLanderState;

import java.util.EventListener;

public interface UniverseListener extends EventListener {
    void newState(final AugmentedLanderState landerState);

    void newTrajectory(final ParametricCurve parametricCurve);
}
