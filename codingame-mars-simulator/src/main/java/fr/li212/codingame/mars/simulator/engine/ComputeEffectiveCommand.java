package fr.li212.codingame.mars.simulator.engine;

import fr.li212.codingame.mars.domain.entities.lander.LanderCommand;
import fr.li212.codingame.mars.domain.entities.lander.LanderState;
import fr.li212.codingame.mars.simulator.SimulatorParameters;

public class ComputeEffectiveCommand {
    public static LanderCommand get(final LanderCommand askedCommand, final LanderState currentState) {

        final int unboundedEffectiveAngle;
        if (Math.abs(currentState.getAngle() - askedCommand.getAngle()) <= SimulatorParameters.MAXIMUM_ROTATION_CHANGE_IN_ONE_TURN) {
            unboundedEffectiveAngle = askedCommand.getAngle();
        } else if (askedCommand.getAngle() < currentState.getAngle()) {
            unboundedEffectiveAngle = currentState.getAngle() - SimulatorParameters.MAXIMUM_ROTATION_CHANGE_IN_ONE_TURN;
        } else {
            unboundedEffectiveAngle = currentState.getAngle() + SimulatorParameters.MAXIMUM_ROTATION_CHANGE_IN_ONE_TURN;
        }

        final int effectiveAngle = unboundedEffectiveAngle < 0
                ? Math.max(-SimulatorParameters.MAXIMUM_ROTATION, unboundedEffectiveAngle)
                : Math.min(SimulatorParameters.MAXIMUM_ROTATION, unboundedEffectiveAngle);

        final int unboundedEffectiveThrust;
        if (askedCommand.getThrust() > currentState.getThrustPower()) {
            unboundedEffectiveThrust = currentState.getThrustPower() + 1;
        } else if (askedCommand.getThrust() < currentState.getThrustPower()) {
            unboundedEffectiveThrust = currentState.getThrustPower() - 1;
        } else {
            unboundedEffectiveThrust = currentState.getThrustPower();
        }

        final int effectiveThrust = Math.max(0, Math.min(SimulatorParameters.MAXIMUM_THRUST, unboundedEffectiveThrust));

        return new LanderCommand(effectiveAngle, effectiveThrust);
    }
}
