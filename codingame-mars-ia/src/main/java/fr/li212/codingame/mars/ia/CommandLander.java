package fr.li212.codingame.mars.ia;

import fr.li212.codingame.mars.domain.entities.IaComputation;
import fr.li212.codingame.mars.domain.entities.ground.Ground;
import fr.li212.codingame.mars.domain.entities.lander.LanderState;
import fr.li212.codingame.mars.domain.entities.trajectory.ParametricCurve;
import fr.li212.codingame.mars.ia.command.PredictCommand;
import fr.li212.codingame.mars.ia.trajectory.ComputeNewtonTrajectory;

public class CommandLander {

    private final PredictCommand predictCommand;
    private final ComputeNewtonTrajectory computeNewtonTrajectory = new ComputeNewtonTrajectory();
    private final Ground ground;

    public CommandLander(
            final PredictCommand predictCommand,
            final Ground ground) {
        this.predictCommand = predictCommand;
        this.ground = ground;
    }

    public IaComputation command(final LanderState landerState) {
        final ParametricCurve trajectory = this.computeNewtonTrajectory.compute(landerState);
        return new IaComputation(
                trajectory,
                predictCommand.command(trajectory, landerState, ground));
    }
}
