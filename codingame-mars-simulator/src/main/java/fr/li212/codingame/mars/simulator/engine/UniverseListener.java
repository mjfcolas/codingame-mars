package fr.li212.codingame.mars.simulator.engine;

import fr.li212.codingame.mars.domain.entities.mechanics.LanderMechanicState;
import fr.li212.codingame.mars.domain.entities.trajectory.ParametricCurve;

import java.util.EventListener;

public interface UniverseListener extends EventListener {
    void newState(final LanderMechanicState landerState);

    void newTrajectory(final ParametricCurve parametricCurve);
}
