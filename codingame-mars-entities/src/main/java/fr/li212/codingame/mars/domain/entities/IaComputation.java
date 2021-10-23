package fr.li212.codingame.mars.domain.entities;

import fr.li212.codingame.mars.domain.entities.lander.LanderCommand;
import fr.li212.codingame.mars.domain.entities.trajectory.ParametricCurve;

public class IaComputation {
    private final ParametricCurve trajectory;
    private final LanderCommand command;

    public IaComputation(final ParametricCurve trajectory, final LanderCommand command) {
        this.trajectory = trajectory;
        this.command = command;
    }

    public ParametricCurve getTrajectory() {
        return trajectory;
    }

    public LanderCommand getCommand() {
        return command;
    }
}
