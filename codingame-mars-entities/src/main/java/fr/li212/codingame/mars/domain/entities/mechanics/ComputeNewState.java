package fr.li212.codingame.mars.domain.entities.mechanics;

import fr.li212.codingame.mars.domain.entities.GlobalParameters;
import fr.li212.codingame.mars.domain.entities.lander.LanderCommand;
import fr.li212.codingame.mars.domain.entities.lander.LanderState;
import fr.li212.codingame.mars.domain.entities.lander.Vector;

public class ComputeNewState {
    public static LanderMechanicState compute(final LanderState initialLanderState, final LanderCommand landerCommand, final int timeToCompute) {
        //System.err.println(landerCommand);

        final Vector thrustVector = new Vector(
                -landerCommand.getThrust() * Math.sin(Math.toRadians(landerCommand.getAngle())),
                landerCommand.getThrust() * Math.cos(Math.toRadians(landerCommand.getAngle())));
        final Vector gravityVector = new Vector(0, -GlobalParameters.GRAVITY_ACCELERATION);
        final Vector accelerationVector = thrustVector.add(gravityVector);

        final Vector newSpeed = new Vector(
                accelerationVector.getX() * timeToCompute + initialLanderState.getSpeed().getX(),
                accelerationVector.getY() * timeToCompute + initialLanderState.getSpeed().getY()
        );

        final Vector newPosition = new Vector(
                0.5 * accelerationVector.getX() * Math.pow(timeToCompute, 2) + initialLanderState.getSpeed().getX() * timeToCompute + initialLanderState.getCoordinates().getX(),
                0.5 * accelerationVector.getY() * Math.pow(timeToCompute, 2) + initialLanderState.getSpeed().getY() * timeToCompute + initialLanderState.getCoordinates().getY()
        );

//        System.err.println("ACCELERATION VECTOR X:" + accelerationVector.getX() + " Y:" + accelerationVector.getY());
//        System.err.println("SPEED               X:" + newSpeed.getX() + " Y:" + newSpeed.getY());
//        System.err.println("POSITION            X:" + newPosition.getX() + " Y:" + newPosition.getY());
//        System.err.println();

        return new LanderMechanicState(
                thrustVector,
                accelerationVector,
                newSpeed,
                newPosition
        );
    }
}
