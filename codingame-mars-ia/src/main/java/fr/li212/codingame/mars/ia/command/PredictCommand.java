package fr.li212.codingame.mars.ia.command;

import fr.li212.codingame.mars.domain.entities.IaComputation;
import fr.li212.codingame.mars.domain.entities.lander.LanderCommand;

public interface PredictCommand {
    IaComputation command();
}
