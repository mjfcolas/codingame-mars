package fr.li212.codingame.mars.ia;

import fr.li212.codingame.mars.domain.entities.IaComputation;
import fr.li212.codingame.mars.domain.entities.ground.Ground;
import fr.li212.codingame.mars.domain.entities.lander.LanderState;
import fr.li212.codingame.mars.domain.entities.trajectory.ParametricCurve;
import fr.li212.codingame.mars.ia.command.implementations.PredictCommandWithAdjustNewtonTrajectory;
import fr.li212.codingame.mars.ia.trajectory.ComputeNewtonTrajectory;

public class CommandLander {

    private final ComputeNewtonTrajectory computeNewtonTrajectory = new ComputeNewtonTrajectory();
    private final Ground ground;

    public CommandLander(final Ground ground) {
        this.ground = ground;
    }

    public IaComputation command(final LanderState landerState) {
        final ParametricCurve trajectory = this.computeNewtonTrajectory.compute(landerState);
        final PredictCommandWithAdjustNewtonTrajectory predictCommand = new PredictCommandWithAdjustNewtonTrajectory(landerState, ground, trajectory);
        return new IaComputation(
                trajectory,
                predictCommand.command());
    }
}
