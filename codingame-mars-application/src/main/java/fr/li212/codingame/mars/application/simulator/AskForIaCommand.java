package fr.li212.codingame.mars.application.simulator;

import fr.li212.codingame.mars.domain.entities.ground.Ground;
import fr.li212.codingame.mars.domain.entities.lander.LanderCommand;
import fr.li212.codingame.mars.domain.entities.lander.LanderState;
import fr.li212.codingame.mars.ia.CommandLander;
import fr.li212.codingame.mars.ia.command.CommandFromTrajectoryAndLanderState;
import fr.li212.codingame.mars.ia.trajectory.ComputeTrajectory;
import fr.li212.codingame.mars.simulator.engine.AskForLanderCommand;

public class AskForIaCommand implements AskForLanderCommand {

    private final ComputeTrajectory computeTrajectory;
    private final CommandFromTrajectoryAndLanderState commandFromTrajectoryAndLanderState;
    private final Ground ground;

    public AskForIaCommand(final ComputeTrajectory computeTrajectory, final CommandFromTrajectoryAndLanderState commandFromTrajectoryAndLanderState, final Ground ground) {
        this.computeTrajectory = computeTrajectory;
        this.commandFromTrajectoryAndLanderState = commandFromTrajectoryAndLanderState;
        this.ground = ground;
    }

    @Override
    public LanderCommand command(final LanderState landerState) {
        return new CommandLander(
                computeTrajectory,
                commandFromTrajectoryAndLanderState,
                ground,
                landerState).command();
    }
}
