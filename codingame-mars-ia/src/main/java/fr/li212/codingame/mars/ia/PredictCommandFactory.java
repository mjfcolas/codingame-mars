package fr.li212.codingame.mars.ia;

import fr.li212.codingame.mars.domain.entities.ground.Ground;
import fr.li212.codingame.mars.domain.entities.lander.LanderState;
import fr.li212.codingame.mars.ia.command.PredictCommand;
import fr.li212.codingame.mars.ia.command.implementations.PredictLateralCommand;
import fr.li212.codingame.mars.ia.command.implementations.PredictVerticalCommandAdjustingTrajectory;
import fr.li212.codingame.mars.ia.trajectory.ComputeTrajectory;

public class PredictCommandFactory {

    private final ComputeTrajectory computeTrajectory;
    private final boolean isVerticalApproach;
    private final Ground ground;

    public PredictCommandFactory(
            final ComputeTrajectory computeTrajectory,
            final boolean isVerticalApproach,
            final Ground ground) {
        this.computeTrajectory = computeTrajectory;
        this.isVerticalApproach = isVerticalApproach;
        this.ground = ground;
    }

    public PredictCommand get(final LanderState landerState) {
        if (isVerticalApproach) {
            return new PredictVerticalCommandAdjustingTrajectory(computeTrajectory, landerState, ground);
        } else {
            return new PredictLateralCommand(computeTrajectory, landerState, ground);
        }
    }
}
