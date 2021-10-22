package fr.li212.codingame.mars.ia.command.implementations;

import fr.li212.codingame.mars.domain.entities.lander.LanderState;
import fr.li212.codingame.mars.domain.entities.lander.Vector;
import fr.li212.codingame.mars.domain.entities.trajectory.ParametricCurve;
import fr.li212.codingame.mars.ia.command.CommandFromTrajectoryAndLanderState;
import fr.li212.codingame.mars.domain.entities.lander.LanderCommand;

public class BasicCommand implements CommandFromTrajectoryAndLanderState {

    private static final int TARGETED_SPEED = 30;

    @Override
    public LanderCommand command(final ParametricCurve trajectory, final LanderState landerState) {
        final Vector targetSpeedVector;
        if (trajectory.getParametricPoints().size() > 10) {
            final int deltaX = trajectory.getParametricPoints().get(10).getCoordinate().getX() - trajectory.getParametricPoints().get(0).getCoordinate().getX();
            final int deltaY = trajectory.getParametricPoints().get(10).getCoordinate().getY() - trajectory.getParametricPoints().get(0).getCoordinate().getY();
            final float ratio = (float) deltaY / deltaX;
            final double normalizeXLength = Math.sqrt(1 / (1 + ratio * ratio));
            final double normalizeYLength = (normalizeXLength * ratio);
            targetSpeedVector = new Vector((int) (normalizeXLength * TARGETED_SPEED), (int) (normalizeYLength * TARGETED_SPEED));
        } else {
            targetSpeedVector = landerState.getSpeed();
        }
        final double cosinusBetweenVerticalAndTargetedSpeed = targetSpeedVector.getY() * targetSpeedVector.getY() / targetSpeedVector.squaredNorm();
        final int sign = targetSpeedVector.getX() > 0 ? -1 : 1;
        final int angle = (int) Math.toDegrees(Math.acos(cosinusBetweenVerticalAndTargetedSpeed)) * sign;
        return new LanderCommand(angle, 3);
    }
}
