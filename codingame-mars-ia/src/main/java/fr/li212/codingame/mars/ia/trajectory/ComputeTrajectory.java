package fr.li212.codingame.mars.ia.trajectory;

import fr.li212.codingame.mars.domain.entities.Coordinate;
import fr.li212.codingame.mars.domain.entities.ground.Ground;
import fr.li212.codingame.mars.domain.entities.trajectory.ParametricCurve;

public interface ComputeTrajectory {
    ParametricCurve compute(final Coordinate startCoordinate, final Coordinate endCoordinate, final Ground ground);
}
