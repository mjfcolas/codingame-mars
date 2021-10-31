package fr.li212.codingame.mars.ia.command.implementations;

import fr.li212.codingame.mars.domain.entities.Coordinate;
import fr.li212.codingame.mars.domain.entities.GlobalParameters;
import fr.li212.codingame.mars.domain.entities.ground.Ground;
import fr.li212.codingame.mars.domain.entities.lander.LanderCommand;
import fr.li212.codingame.mars.domain.entities.lander.LanderState;
import fr.li212.codingame.mars.domain.entities.trajectory.ParametricCurve;
import fr.li212.codingame.mars.domain.entities.trajectory.ParametricPoint;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PredictCommandWithAdjustNewtonTrajectory {

    private static final int MARGIN_FOR_LANDING = 14;
    private static final int VERTICAL_SPEED_MARGIN = 1;
    private static final int MAX_TILT_TO_FIGHT_GRAVITY = (int) Math.toDegrees(Math.acos(GlobalParameters.GRAVITY_ACCELERATION / GlobalParameters.MAX_THRUST));
    private static final int ALTITUDE_MARGIN_FOR_SAFE_TRAJECTORY = 5;


    private final LanderState landerState;
    private final Ground ground;
    private final ParametricCurve trajectory;
    private final int landingSurfaceHeight;
    private final int minXForLanding;
    private final int maxXForLanding;
    private final int minTargetXForLanding;
    private final int maxTargetXForLanding;
    private final ParametricPoint pointClosestToLanding;

    public PredictCommandWithAdjustNewtonTrajectory(
            final LanderState landerState,
            final Ground ground,
            final ParametricCurve trajectory
    ) {
        this.landerState = landerState;
        this.ground = ground;
        this.trajectory = trajectory;
        this.landingSurfaceHeight = ground.getLandingSurface().getStartCoordinate().getY();
        this.minXForLanding = ground.getLandingSurface().getStartCoordinate().getX();
        this.maxXForLanding = ground.getLandingSurface().getEndCoordinate().getX();
        this.minTargetXForLanding = minXForLanding + MARGIN_FOR_LANDING;
        this.maxTargetXForLanding = maxXForLanding - MARGIN_FOR_LANDING;
        this.pointClosestToLanding = trajectory.getParametricPoints().stream()
                .sorted(Comparator.comparingInt(p -> Math.abs(p.getCoordinate().getY() - landingSurfaceHeight)))
                .findFirst().get();
    }

    public LanderCommand command() {

        final double maxLoseOfSpeedFromNowToImpact = computeMaximumLoseOfSpeedBeforeImpact(pointClosestToLanding, 0);
        final double maxSpeedAtCurrentPoint = GlobalParameters.MAX_VERTICAL_SPEED_AT_LANDING - VERTICAL_SPEED_MARGIN + maxLoseOfSpeedFromNowToImpact;

        //Final Phase
        if (landerState.getCoordinates().getY() - landingSurfaceHeight < GlobalParameters.MAX_VERTICAL_SPEED_AT_LANDING * 3) {
            return new LanderCommand(0, GlobalParameters.MAX_THRUST);
        }

        //Horizontal/Vertical ratio too big: keep calm and stabilize
        final Coordinate targetCoordinates = new Coordinate(minXForLanding + maxXForLanding / 2, landingSurfaceHeight);
        if (Math.abs((targetCoordinates.getX() - landerState.getCoordinates().getX()) / (targetCoordinates.getY() - landerState.getCoordinates().getY())) > 2
                && Math.abs(landerState.getSpeed().getX()) > 2 * GlobalParameters.MAX_HORIZONTAL_SPEED_AT_LANDING) {
            if (landerState.getSpeed().getX() > 0) {
                return new LanderCommand(MAX_TILT_TO_FIGHT_GRAVITY / 4, GlobalParameters.MAX_THRUST);
            } else {
                return new LanderCommand(-MAX_TILT_TO_FIGHT_GRAVITY / 4, GlobalParameters.MAX_THRUST);
            }
        }

        final int heightAboveLandingPoint = landerState.getCoordinates().getY() - landingSurfaceHeight;

        // Correct trajectory phase
        if (pointClosestToLanding.getCoordinate().getX() < minTargetXForLanding) {
            System.err.println("A");
            if(heightAboveLandingPoint > 700) {
                return computeCommand(-GlobalParameters.MAX_TILT / 2);
            }else{
                return computeCommand(-GlobalParameters.MAX_TILT / 8);
            }
        } else if (pointClosestToLanding.getCoordinate().getX() > maxTargetXForLanding) {
            System.err.println("B");
            if(heightAboveLandingPoint > 700) {
                return computeCommand(GlobalParameters.MAX_TILT / 2);
            }else{
                return computeCommand(GlobalParameters.MAX_TILT / 8);
            }
        } else if (Math.abs(landerState.getSpeed().getX()) > (GlobalParameters.MAX_HORIZONTAL_SPEED_AT_LANDING) && landerState.getSpeed().getX() > 0) {
            System.err.println("C");
            if(heightAboveLandingPoint > 600) {
                return computeCommand(MAX_TILT_TO_FIGHT_GRAVITY);
            }else{
                return computeCommand(2*MAX_TILT_TO_FIGHT_GRAVITY);
            }
        } else if (Math.abs(landerState.getSpeed().getX()) > (GlobalParameters.MAX_HORIZONTAL_SPEED_AT_LANDING) && landerState.getSpeed().getX() < 0) {
            System.err.println("D");
            if(heightAboveLandingPoint > 600) {
                return computeCommand(-MAX_TILT_TO_FIGHT_GRAVITY);
            }else{
                return computeCommand(-2*MAX_TILT_TO_FIGHT_GRAVITY);
            }
        } else if (Math.abs(landerState.getSpeed().getY()) > maxSpeedAtCurrentPoint) {
            System.err.println("E");
            return computeCommand(0);
        } else {
            System.err.println("F");
            return new LanderCommand(0, 0);
        }
    }

    private LanderCommand computeCommand(final int targetTilt) {
        if (Math.abs(landerState.getAngle() - targetTilt) < 60) {
            final boolean trajectorySafe = isTrajectorySafe();
            final int thrust;
            if (!trajectorySafe) {
                thrust = GlobalParameters.MAX_THRUST;
            } else {
                final int maxSpeedAtCurrentPoint = (int) (GlobalParameters.MAX_VERTICAL_SPEED_AT_LANDING - VERTICAL_SPEED_MARGIN + computeMaximumLoseOfSpeedBeforeImpact(pointClosestToLanding, targetTilt));
                thrust = Math.abs(landerState.getSpeed().getY()) > maxSpeedAtCurrentPoint ? GlobalParameters.MAX_THRUST : GlobalParameters.MAX_THRUST - 1;
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
        for (int i = 0; i < safeStatusForTrajectoryPoints.size(); i++) {
            if (!safeStatusForTrajectoryPoints.get(i)) {
                potentiallyUnsafe = true;
            }
            if (potentiallyUnsafe && safeStatusForTrajectoryPoints.get(i)) {
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
