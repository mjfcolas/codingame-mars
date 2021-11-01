package fr.li212.codingame.mars.ia.command.implementations;

import fr.li212.codingame.mars.domain.entities.GlobalParameters;
import fr.li212.codingame.mars.domain.entities.IaComputation;
import fr.li212.codingame.mars.domain.entities.ground.Ground;
import fr.li212.codingame.mars.domain.entities.lander.LanderCommand;
import fr.li212.codingame.mars.domain.entities.lander.LanderState;
import fr.li212.codingame.mars.domain.entities.trajectory.ParametricCurve;
import fr.li212.codingame.mars.ia.command.PredictCommand;
import fr.li212.codingame.mars.ia.trajectory.ComputeTrajectory;

public class PredictLateralCommand implements PredictCommand {

    private static final int HORIZONTAL_SPEED_MARGIN = 1;
    private static final int VERTICAL_SPEED_MARGIN = 10;

    private static final int MAX_TILT_TO_FIGHT_GRAVITY = (int) Math.toDegrees(Math.acos(GlobalParameters.GRAVITY_ACCELERATION / GlobalParameters.MAX_THRUST));
    private static final double LATERAL_ACCELERATION = Math.sin(Math.toRadians(MAX_TILT_TO_FIGHT_GRAVITY)) * GlobalParameters.MAX_THRUST;

    private final LanderState landerState;
    private final int targetLandingCoordinate;
    private final int minLandingCoordinate;
    private final int maxLandingCoordinate;
    private final ParametricCurve trajectory;

    public PredictLateralCommand(
            final ComputeTrajectory computeTrajectory,
            final LanderState landerState,
            final Ground ground) {
        System.err.println("LATERAL COMMAND IMPLEMENTATION");
        this.trajectory = computeTrajectory.compute(landerState);
        this.landerState = landerState;

        this.minLandingCoordinate = ground.getLandingSurface().getStartCoordinate().getX();
        this.maxLandingCoordinate = ground.getLandingSurface().getEndCoordinate().getX();

        if (landerState.getCoordinates().getX() > ground.getLandingSurface().getEndCoordinate().getX()) {
            targetLandingCoordinate = ground.getLandingSurface().getEndCoordinate().getX();
        } else if (landerState.getCoordinates().getX() < ground.getLandingSurface().getStartCoordinate().getX()) {
            targetLandingCoordinate = ground.getLandingSurface().getStartCoordinate().getX();
        }else{
            targetLandingCoordinate = landerState.getCoordinates().getX();
        }
    }

    @Override
    public IaComputation command() {
        final double maximumSpeedBeforeBraking = GlobalParameters.MAX_HORIZONTAL_SPEED_AT_LANDING
                - HORIZONTAL_SPEED_MARGIN
                + computeMaximumLoseOfSpeedBeforeReachingLandingSurface(targetLandingCoordinate);
        final int currentSpeed = (int)Math.abs(landerState.getSpeed().getX());

        final int directionCoefficient = landerState.getCoordinates().getX() > targetLandingCoordinate ? 1 : -1;
        if(landerState.getCoordinates().getX() > minLandingCoordinate && landerState.getCoordinates().getX() < maxLandingCoordinate){
            final boolean tooMuchVerticalSpeed = Math.abs(landerState.getSpeed().getY()) > GlobalParameters.MAX_VERTICAL_SPEED_AT_LANDING - VERTICAL_SPEED_MARGIN;
            return output(new LanderCommand(0, tooMuchVerticalSpeed? GlobalParameters.MAX_THRUST : 0));
        } else if(currentSpeed < maximumSpeedBeforeBraking){
            return output(new LanderCommand(directionCoefficient * MAX_TILT_TO_FIGHT_GRAVITY, GlobalParameters.MAX_THRUST));
        }else if(currentSpeed >= maximumSpeedBeforeBraking){
            return output(new LanderCommand(-directionCoefficient * MAX_TILT_TO_FIGHT_GRAVITY, GlobalParameters.MAX_THRUST));
        }else{
            return output(new LanderCommand(0,0));
        }
    }

    private IaComputation output(final LanderCommand command){
        return new IaComputation(this.trajectory, command);
    }


    private double computeMaximumLoseOfSpeedBeforeReachingLandingSurface(final int targetPointX) {
        final int distance = Math.abs(landerState.getCoordinates().getX() - targetPointX);
        double timeToReachLandingSurface = Math.abs((double) distance / landerState.getSpeed().getX());

        double maxLoseOfSpeedFromNowToImpact = 0;
        for (int i = 1; i <= timeToReachLandingSurface; i++) {
            maxLoseOfSpeedFromNowToImpact += LATERAL_ACCELERATION;
        }
        return maxLoseOfSpeedFromNowToImpact;
    }
}
