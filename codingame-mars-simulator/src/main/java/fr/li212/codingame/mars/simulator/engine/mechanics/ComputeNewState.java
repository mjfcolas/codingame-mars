package fr.li212.codingame.mars.simulator.engine.mechanics;

import fr.li212.codingame.mars.domain.entities.Coordinate;
import fr.li212.codingame.mars.domain.entities.GlobalParameters;
import fr.li212.codingame.mars.domain.entities.lander.LanderCommand;
import fr.li212.codingame.mars.domain.entities.lander.LanderState;
import fr.li212.codingame.mars.domain.entities.lander.Vector;
import fr.li212.codingame.mars.simulator.SimulatorParameters;

public class ComputeNewState {
    public static AugmentedLanderState compute(final LanderState initialLanderState, final LanderCommand landerCommand) {
        System.err.println(landerCommand);

        final Vector thrustVector = new Vector(
                -landerCommand.getThrust() * Math.sin(Math.toRadians(landerCommand.getAngle())),
                landerCommand.getThrust() * Math.cos(Math.toRadians(landerCommand.getAngle())));
        final Vector gravityVector = new Vector(0, -GlobalParameters.GRAVITY_ACCELERATION);
        final Vector accelerationVector = thrustVector.add(gravityVector);

        final Vector newSpeed = new Vector(
                accelerationVector.getX() * SimulatorParameters.TICK_SIMULATED_DURATION_IN_SECONDS + initialLanderState.getSpeed().getX(),
                accelerationVector.getY() * SimulatorParameters.TICK_SIMULATED_DURATION_IN_SECONDS + initialLanderState.getSpeed().getY()
        );

        final Vector newPosition = new Vector(
                0.5 * accelerationVector.getX() * Math.pow(SimulatorParameters.TICK_SIMULATED_DURATION_IN_SECONDS, 2) + initialLanderState.getSpeed().getX() * SimulatorParameters.TICK_SIMULATED_DURATION_IN_SECONDS + initialLanderState.getCoordinates().getX(),
                0.5 * accelerationVector.getY() * Math.pow(SimulatorParameters.TICK_SIMULATED_DURATION_IN_SECONDS, 2) + initialLanderState.getSpeed().getY() * SimulatorParameters.TICK_SIMULATED_DURATION_IN_SECONDS + initialLanderState.getCoordinates().getY()
        );

        System.err.println("ACCELERATION VECTOR X:" + accelerationVector.getX() + " Y:" + accelerationVector.getY());
        System.err.println("SPEED               X:" + newSpeed.getX() + " Y:" + newSpeed.getY());
        System.err.println("POSITION            X:" + newPosition.getX() + " Y:" + newPosition.getY());
        System.err.println();

        final LanderState landerState = new LanderState(
                new Coordinate((int) newPosition.getX(), (int) newPosition.getY()),
                newSpeed,
                initialLanderState.getRemainingFuel(),
                landerCommand.getAngle(),
                landerCommand.getThrust()
        );

        return new AugmentedLanderState(
                landerState,
                thrustVector,
                accelerationVector
        );
    }
}
