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

    private static final int MARGIN_FOR_LANDING = 200;
    private static final int SPEED_MARGIN = 3;
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
            return new LanderCommand(-GlobalParameters.MAX_TILT / 3, GlobalParameters.MAX_THRUST);
        } else if (pointClosestToLanding.getCoordinate().getX() > maxTargetXForLanding) {
            return new LanderCommand(GlobalParameters.MAX_TILT / 3, GlobalParameters.MAX_THRUST);
        } else if (Math.abs(landerState.getSpeed().getX()) > (GlobalParameters.MAX_HORIZONTAL_SPEED_AT_LANDING - SPEED_MARGIN) && landerState.getSpeed().getX() > 0) {
            return new LanderCommand(MAX_TILT_TO_FIGHT_GRAVITY, GlobalParameters.MAX_THRUST);
        } else if (Math.abs(landerState.getSpeed().getX()) > (GlobalParameters.MAX_HORIZONTAL_SPEED_AT_LANDING - SPEED_MARGIN) && landerState.getSpeed().getX() < 0) {
            return new LanderCommand(-MAX_TILT_TO_FIGHT_GRAVITY, GlobalParameters.MAX_THRUST);
        } else if (landerState.getSpeed().getY() < -GlobalParameters.MAX_VERTICAL_SPEED_AT_LANDING + SPEED_MARGIN) {
            return new LanderCommand(0, GlobalParameters.MAX_THRUST);
        } else {
            return new LanderCommand(0, GlobalParameters.MAX_THRUST - 1);
        }
    }
}
