package fr.li212.codingame.mars.ia.trajectory;

import fr.li212.codingame.mars.domain.entities.Coordinate;
import fr.li212.codingame.mars.domain.entities.lander.LanderCommand;
import fr.li212.codingame.mars.domain.entities.lander.LanderState;
import fr.li212.codingame.mars.domain.entities.mechanics.ComputeNewState;
import fr.li212.codingame.mars.domain.entities.mechanics.LanderMechanicState;
import fr.li212.codingame.mars.domain.entities.trajectory.ParametricCurve;
import fr.li212.codingame.mars.domain.entities.trajectory.ParametricPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ComputeNewtonTrajectory implements ComputeTrajectory {

    @Override
    public ParametricCurve compute(final LanderState landerState) {
        final LanderMechanicState landerMechanicStateAfterOneTick = ComputeNewState.compute(landerState, new LanderCommand(landerState.getAngle(), landerState.getThrustPower()), 1);
        final ParametricPoint firstPoint = new ParametricPoint((double) 1 / ParametricCurve.NUMBER_OF_POINTS, new Coordinate((int) landerMechanicStateAfterOneTick.getPosition().getX(), (int) landerMechanicStateAfterOneTick.getPosition().getY()));
        final LanderState landerStateAfterOneTick = new LanderState(
                new Coordinate((int)landerMechanicStateAfterOneTick.getPosition().getX(), (int)landerMechanicStateAfterOneTick.getPosition().getY()),
                landerMechanicStateAfterOneTick.getSpeed(),
                landerState.getRemainingFuel(),
                landerState.getAngle(),
                landerState.getThrustPower()
        );
        final List<ParametricPoint> pointsAfter = IntStream.range(1, ParametricCurve.NUMBER_OF_POINTS).boxed().map(tick -> {
            final LanderMechanicState landerMechanicState = ComputeNewState.compute(landerStateAfterOneTick, new LanderCommand(0, 0), tick);
            return new ParametricPoint((double) tick / ParametricCurve.NUMBER_OF_POINTS, new Coordinate((int) landerMechanicState.getPosition().getX(), (int) landerMechanicState.getPosition().getY()));
        }).collect(Collectors.toList());

        final List<ParametricPoint> allPoints = new ArrayList<>();
        allPoints.add(firstPoint);
        allPoints.addAll(pointsAfter);
        return new ParametricCurve(allPoints);
    }
}
