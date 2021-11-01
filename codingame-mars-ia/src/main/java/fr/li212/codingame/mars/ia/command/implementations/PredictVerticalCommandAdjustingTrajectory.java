package fr.li212.codingame.mars.ia.command.implementations;

import fr.li212.codingame.mars.domain.entities.GlobalParameters;
import fr.li212.codingame.mars.domain.entities.IaComputation;
import fr.li212.codingame.mars.domain.entities.ground.Ground;
import fr.li212.codingame.mars.domain.entities.lander.LanderCommand;
import fr.li212.codingame.mars.domain.entities.lander.LanderState;
import fr.li212.codingame.mars.domain.entities.trajectory.ParametricCurve;
import fr.li212.codingame.mars.domain.entities.trajectory.ParametricPoint;
import fr.li212.codingame.mars.ia.command.PredictCommand;
import fr.li212.codingame.mars.ia.trajectory.ComputeTrajectory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PredictVerticalCommandAdjustingTrajectory implements PredictCommand {

    private static final int MARGIN_FOR_LANDING = 5;
    private static final int VERTICAL_SPEED_MARGIN = 1;
    private static final int MAX_TILT_TO_FIGHT_GRAVITY = (int) Math.toDegrees(Math.acos(GlobalParameters.GRAVITY_ACCELERATION / GlobalParameters.MAX_THRUST));
    private static final int ALTITUDE_MARGIN_FOR_SAFE_TRAJECTORY = 40;


    private final LanderState landerState;
    private final Ground ground;
    private final ParametricCurve trajectory;
    private final int landingSurfaceHeight;
    private final int minTargetXForLanding;
    private final int maxTargetXForLanding;
    private final ParametricPoint pointClosestToLanding;

    public PredictVerticalCommandAdjustingTrajectory(
            final ComputeTrajectory computeTrajectory,
            final LanderState landerState,
            final Ground ground) {
        System.err.println("VERTICAL COMMAND IMPLEMENTATION");
        this.trajectory = computeTrajectory.compute(landerState);
        this.landerState = landerState;
        this.ground = ground;
        this.landingSurfaceHeight = ground.getLandingSurface().getStartCoordinate().getY();
        this.minTargetXForLanding = ground.getLandingSurface().getStartCoordinate().getX() + MARGIN_FOR_LANDING;
        this.maxTargetXForLanding = ground.getLandingSurface().getEndCoordinate().getX() - MARGIN_FOR_LANDING;
        this.pointClosestToLanding = trajectory.getParametricPoints().stream()
                .min(Comparator.comparingInt(p -> Math.abs(p.getCoordinate().getY() - landingSurfaceHeight)))
                .orElseThrow(IllegalStateException::new);
    }

    @Override
    public IaComputation command() {
        final double maxLoseOfSpeedFromNowToImpact = computeMaximumLoseOfSpeedBeforeImpact(pointClosestToLanding, 0);
        final double maxSpeedAtCurrentPoint = GlobalParameters.MAX_VERTICAL_SPEED_AT_LANDING - VERTICAL_SPEED_MARGIN + maxLoseOfSpeedFromNowToImpact;

        //Final Phase
        if (landerState.getCoordinates().getY() - landingSurfaceHeight < GlobalParameters.MAX_VERTICAL_SPEED_AT_LANDING) {
            return output(new LanderCommand(0, GlobalParameters.MAX_THRUST));
        }

        // Correct trajectory phase
        if (pointClosestToLanding.getCoordinate().getX() < minTargetXForLanding) {
            System.err.println("A");
            return output(computeCommand(-GlobalParameters.MAX_TILT / 2));
        } else if (pointClosestToLanding.getCoordinate().getX() > maxTargetXForLanding) {
            System.err.println("B");
            return output(computeCommand(GlobalParameters.MAX_TILT / 2));
        } else if (Math.abs(landerState.getSpeed().getX()) > (GlobalParameters.MAX_HORIZONTAL_SPEED_AT_LANDING) && landerState.getSpeed().getX() > 0) {
            System.err.println("C");
            return output(computeCommand(MAX_TILT_TO_FIGHT_GRAVITY));
        } else if (Math.abs(landerState.getSpeed().getX()) > (GlobalParameters.MAX_HORIZONTAL_SPEED_AT_LANDING) && landerState.getSpeed().getX() < 0) {
            System.err.println("D");
            return output(computeCommand(-MAX_TILT_TO_FIGHT_GRAVITY));
        } else if (Math.abs(landerState.getSpeed().getY()) > maxSpeedAtCurrentPoint) {
            System.err.println("E");
            return output(computeCommand(0));
        } else {
            System.err.println("F");
            return output(new LanderCommand(0, 0));
        }
    }

    private IaComputation output(final LanderCommand command){
        return new IaComputation(this.trajectory, command);
    }

    private LanderCommand computeCommand(final int targetTilt) {
        if (Math.abs(landerState.getAngle() - targetTilt) < 60) {
            final boolean trajectorySafe = isTrajectorySafe();
            final int thrust;
            if (!trajectorySafe) {
                thrust = GlobalParameters.MAX_THRUST;
            } else {
                final int maxSpeedAtCurrentPoint = (int) (GlobalParameters.MAX_VERTICAL_SPEED_AT_LANDING - VERTICAL_SPEED_MARGIN + computeMaximumLoseOfSpeedBeforeImpact(pointClosestToLanding, targetTilt));
                thrust = Math.abs(landerState.getSpeed().getY()) > maxSpeedAtCurrentPoint ? GlobalParameters.MAX_THRUST : GlobalParameters.MAX_THRUST - 2;
            }
            return new LanderCommand(targetTilt, thrust);
        } else {
            return new LanderCommand(targetTilt, 0);
        }
    }

    private boolean isTrajectorySafe() {
        final List<Boolean> safeStatusForTrajectoryPoints = this.trajectory.getParametricPoints().stream()
                .filter(parametricPoint -> parametricPoint.getCoordinate().getY() > 0)
                .map(currentPoint -> ground.getSurfaces().stream()
                        .filter(surface -> surface.getStartCoordinate().getX() < currentPoint.getCoordinate().getX()
                                && surface.getEndCoordinate().getX() >= currentPoint.getCoordinate().getX())
                        .findAny().map(surface -> {
                            final int surfaceXWidth = surface.getEndCoordinate().getX() - surface.getStartCoordinate().getX();
                            final int currentPointPositionInSurface = currentPoint.getCoordinate().getX() - surface.getStartCoordinate().getX();
                            final int surfaceYHeight = surface.getEndCoordinate().getY() - surface.getStartCoordinate().getY();
                            final double groundHeightAtPoint = surface.getStartCoordinate().getY() + (double) currentPointPositionInSurface / surfaceXWidth * surfaceYHeight;
                            return currentPoint.getCoordinate().getY() > groundHeightAtPoint + ALTITUDE_MARGIN_FOR_SAFE_TRAJECTORY;
                        }).orElse(true)).collect(Collectors.toList());

        boolean potentiallyUnsafe = false;
        boolean unsafe = false;
        for (Boolean safeStatusForTrajectoryPoint : safeStatusForTrajectoryPoints) {
            if (!safeStatusForTrajectoryPoint) {
                potentiallyUnsafe = true;
            }
            if (potentiallyUnsafe && safeStatusForTrajectoryPoint) {
                unsafe = true;
            }
        }

        return !unsafe;
    }

    private double computeMaximumLoseOfSpeedBeforeImpact(final ParametricPoint pointClosestToLanding, final int targetTilt) {
        final int timeBeforeLanding = (int) (pointClosestToLanding.getT() * ParametricCurve.NUMBER_OF_POINTS);
        final double verticalComposantOfAcceleration = Math.cos(Math.toRadians(targetTilt));
        double maxLoseOfSpeedFromNowToImpact = 0;
        for (int i = 1; i <= timeBeforeLanding; i++) {
            maxLoseOfSpeedFromNowToImpact += Math.min(landerState.getThrustPower() + i, GlobalParameters.MAX_THRUST) * verticalComposantOfAcceleration - GlobalParameters.GRAVITY_ACCELERATION;
        }
        System.err.println(timeBeforeLanding);
        System.err.println(maxLoseOfSpeedFromNowToImpact);
        return maxLoseOfSpeedFromNowToImpact;
    }
}
