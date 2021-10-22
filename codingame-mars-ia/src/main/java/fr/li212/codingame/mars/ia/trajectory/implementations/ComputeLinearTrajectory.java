package fr.li212.codingame.mars.ia.trajectory.implementations;

import fr.li212.codingame.mars.domain.entities.Coordinate;
import fr.li212.codingame.mars.domain.entities.ground.Ground;
import fr.li212.codingame.mars.domain.entities.trajectory.ParametricCurve;
import fr.li212.codingame.mars.domain.entities.trajectory.ParametricPoint;
import fr.li212.codingame.mars.ia.trajectory.ComputeTrajectory;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ComputeLinearTrajectory implements ComputeTrajectory {

    @Override
    public ParametricCurve compute(final Coordinate startCoordinate, final Coordinate endCoordinate, final Ground ground) {
        return new ParametricCurve(IntStream.range(0, ParametricPoint.MAX_T).boxed().map(t -> {
            final int x = startCoordinate.getX() + t * (endCoordinate.getX() - startCoordinate.getX()) / ParametricPoint.MAX_T;
            final int y = startCoordinate.getY() + t * (endCoordinate.getY() - startCoordinate.getY()) / ParametricPoint.MAX_T;
            final Coordinate currentCoordinate = new Coordinate(x, y);
            return new ParametricPoint(t, currentCoordinate);
        }).collect(Collectors.toList()));
    }
}
