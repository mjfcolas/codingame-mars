package fr.li212.codingame.mars.application.simulator;

import fr.li212.codingame.mars.domain.entities.IaComputation;
import fr.li212.codingame.mars.domain.entities.ground.Ground;
import fr.li212.codingame.mars.domain.entities.lander.LanderState;
import fr.li212.codingame.mars.ia.CommandLander;
import fr.li212.codingame.mars.ia.command.PredictCommand;
import fr.li212.codingame.mars.simulator.engine.AskForIaComputation;

public class AskForIaComputations implements AskForIaComputation {
    private final CommandLander commandLander;

    public AskForIaComputations(final PredictCommand predictCommand, final Ground ground) {
        this.commandLander = new CommandLander(
                predictCommand,
                ground);
    }

    @Override
    public IaComputation compute(final LanderState landerState) {
        return commandLander.command(landerState);
    }
}
