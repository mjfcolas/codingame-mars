package fr.li212.codingame.mars.ia;

import fr.li212.codingame.mars.domain.entities.IaComputation;
import fr.li212.codingame.mars.domain.entities.ground.Ground;
import fr.li212.codingame.mars.domain.entities.lander.LanderState;
import fr.li212.codingame.mars.ia.command.PredictCommand;
import fr.li212.codingame.mars.ia.trajectory.ComputeNewtonTrajectory;

public class CommandLander {

    private final PredictCommandFactory predictCommandFactory;

    public CommandLander(final Ground ground) {
        this.predictCommandFactory = new PredictCommandFactory(
                new ComputeNewtonTrajectory(), ground.getLandingSurface().getEndCoordinate().getY() < 2000,
                ground);
    }

    public IaComputation command(final LanderState landerState) {
        final PredictCommand predictCommand = predictCommandFactory.get(landerState);
        return predictCommand.command();
    }
}
