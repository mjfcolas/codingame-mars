package fr.li212.codingame.mars.ia.command;

import fr.li212.codingame.mars.domain.entities.lander.LanderState;
import fr.li212.codingame.mars.domain.entities.trajectory.ParametricCurve;
import fr.li212.codingame.mars.domain.entities.lander.LanderCommand;

public interface CommandFromTrajectoryAndLanderState {
    LanderCommand command(final ParametricCurve trajectory, final LanderState landerState);
}
