package fr.li212.codingame.mars.ia.trajectory;

import fr.li212.codingame.mars.domain.entities.lander.LanderState;
import fr.li212.codingame.mars.domain.entities.trajectory.ParametricCurve;

public interface ComputeTrajectory {
    ParametricCurve compute(final LanderState landerState);
}
