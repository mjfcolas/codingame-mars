package fr.li212.codingame.mars.ia.command.implementations;

import fr.li212.codingame.mars.domain.entities.Coordinate;
import fr.li212.codingame.mars.domain.entities.GlobalParameters;
import fr.li212.codingame.mars.domain.entities.ground.Ground;
import fr.li212.codingame.mars.domain.entities.ground.Surface;
import fr.li212.codingame.mars.domain.entities.lander.LanderCommand;
import fr.li212.codingame.mars.domain.entities.lander.LanderState;
import fr.li212.codingame.mars.domain.entities.trajectory.ParametricCurve;
import fr.li212.codingame.mars.domain.entities.trajectory.ParametricPoint;
import fr.li212.codingame.mars.ia.command.PredictCommand;

import java.util.Comparator;

public class PredictCommandWithAdjustNewtonTrajectory implements PredictCommand {

    private static final double MAXIMUM_VERTICAL_ACCELERATION = GlobalParameters.MAX_THRUST - GlobalParameters.GRAVITY_ACCELERATION;

    private static final int MARGIN_FOR_LANDING = 10;
    private static final int HORIZONTAL_SPEED_MARGIN = 0;
    private static final int VERTICAL_SPEED_MARGIN = 8;
    private static final int MAX_TILT_TO_FIGHT_GRAVITY = (int) Math.toDegrees(Math.acos(GlobalParameters.GRAVITY_ACCELERATION / GlobalParameters.MAX_THRUST));

    @Override
    public LanderCommand command(final ParametricCurve trajectory, final LanderState landerState, final Ground ground) {
        final Surface landingSurface = ground.getLandingSurface();
        final int landingSurfaceHeight = landingSurface.getStartCoordinate().getY();
        final int minXForLanding = landingSurface.getStartCoordinate().getX();
        final int maxXForLanding = landingSurface.getEndCoordinate().getX();

        final int minTargetXForLanding = minXForLanding + MARGIN_FOR_LANDING;
        final int maxTargetXForLanding = maxXForLanding - MARGIN_FOR_LANDING;

        final ParametricPoint pointClosestToLanding = trajectory.getParametricPoints().stream()
                .sorted(Comparator.comparingInt(p -> Math.abs(p.getCoordinate().getY() - landingSurfaceHeight)))
                .findFirst().get();

        final int timeBeforeLanding = (int)(pointClosestToLanding.getT() * ParametricCurve.NUMBER_OF_POINTS);
        final double maxLoseOfSpeedFromNowToImpact = MAXIMUM_VERTICAL_ACCELERATION * timeBeforeLanding;
        final double maxSpeedAtCurrentPoint = GlobalParameters.MAX_VERTICAL_SPEED_AT_LANDING - VERTICAL_SPEED_MARGIN + maxLoseOfSpeedFromNowToImpact;

        //Final Phase
        if (landerState.getCoordinates().getY() - landingSurfaceHeight < GlobalParameters.MAX_VERTICAL_SPEED_AT_LANDING * 3) {
            return new LanderCommand(0, GlobalParameters.MAX_THRUST);
        }

        //Horizontal/Vertical ratio too big: keep calm and stabilize
        final Coordinate targetCoordinates = new Coordinate(minXForLanding + maxXForLanding / 2, landingSurfaceHeight);
        if (Math.abs((targetCoordinates.getX() - landerState.getCoordinates().getX()) / (targetCoordinates.getY() - landerState.getCoordinates().getY())) > 5
                && Math.abs(landerState.getSpeed().getX()) > 2*GlobalParameters.MAX_HORIZONTAL_SPEED_AT_LANDING) {
            if (landerState.getSpeed().getX() > 0) {
                return new LanderCommand(MAX_TILT_TO_FIGHT_GRAVITY / 2, GlobalParameters.MAX_THRUST);
            } else {
                return new LanderCommand(-MAX_TILT_TO_FIGHT_GRAVITY / 2, GlobalParameters.MAX_THRUST);
            }
        }

        //Correct trajectory phase
        if (pointClosestToLanding.getCoordinate().getX() < minTargetXForLanding) {
            return computeCommand(-GlobalParameters.MAX_TILT / 3, GlobalParameters.MAX_THRUST, landerState);
        } else if (pointClosestToLanding.getCoordinate().getX() > maxTargetXForLanding) {
            return computeCommand(GlobalParameters.MAX_TILT / 3, GlobalParameters.MAX_THRUST, landerState);
        } else if (Math.abs(landerState.getSpeed().getX()) > (GlobalParameters.MAX_HORIZONTAL_SPEED_AT_LANDING - HORIZONTAL_SPEED_MARGIN) && landerState.getSpeed().getX() > 0) {
            return computeCommand(MAX_TILT_TO_FIGHT_GRAVITY, GlobalParameters.MAX_THRUST, landerState);
        } else if (Math.abs(landerState.getSpeed().getX()) > (GlobalParameters.MAX_HORIZONTAL_SPEED_AT_LANDING - HORIZONTAL_SPEED_MARGIN) && landerState.getSpeed().getX() < 0) {
            return computeCommand(-MAX_TILT_TO_FIGHT_GRAVITY, GlobalParameters.MAX_THRUST, landerState);
        } else if (Math.abs(landerState.getSpeed().getY()) > maxSpeedAtCurrentPoint) {
            return computeCommand(0, GlobalParameters.MAX_THRUST, landerState);
        } else {
            return computeCommand(0, 0, landerState);
        }
    }

    private LanderCommand computeCommand(final int targetTilt, final int targetThrust, final LanderState landerState){
        if(Math.abs(landerState.getAngle() - targetTilt) < 60){
            return new LanderCommand(targetTilt, targetThrust);
        }else{
            return new LanderCommand(targetTilt, 0);
        }
    }
}
