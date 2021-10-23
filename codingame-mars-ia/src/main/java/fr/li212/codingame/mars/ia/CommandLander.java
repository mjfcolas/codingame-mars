package fr.li212.codingame.mars.ia;

import fr.li212.codingame.mars.domain.entities.ground.Ground;
import fr.li212.codingame.mars.domain.entities.IaComputation;
import fr.li212.codingame.mars.domain.entities.lander.LanderState;
import fr.li212.codingame.mars.domain.entities.trajectory.ParametricCurve;
import fr.li212.codingame.mars.ia.command.CommandFromTrajectoryAndLanderState;
import fr.li212.codingame.mars.ia.trajectory.ComputeTrajectory;

public class CommandLander {

    private final ComputeTrajectory computeTrajectory;
    private final CommandFromTrajectoryAndLanderState commandFromTrajectoryAndLanderState;

    private final Ground ground;
    private final LanderState landerState;

    public CommandLander(
            final ComputeTrajectory computeTrajectory,
            final CommandFromTrajectoryAndLanderState commandFromTrajectoryAndLanderState,
            final Ground ground,
            final LanderState landerState) {
        this.computeTrajectory = computeTrajectory;
        this.commandFromTrajectoryAndLanderState = commandFromTrajectoryAndLanderState;
        this.ground = ground;
        this.landerState = landerState;
    }

    public IaComputation command() {
        final ParametricCurve trajectory = this.computeTrajectory.compute(landerState.getCoordinates(), ground.getTargetLandingCoordinates(), ground);
        return new IaComputation(
                trajectory,
                commandFromTrajectoryAndLanderState.command(trajectory, landerState));
    }
}
