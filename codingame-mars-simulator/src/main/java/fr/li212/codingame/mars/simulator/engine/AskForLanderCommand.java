package fr.li212.codingame.mars.simulator.engine;

import fr.li212.codingame.mars.domain.entities.lander.LanderCommand;
import fr.li212.codingame.mars.domain.entities.lander.LanderState;

public interface AskForLanderCommand {
    LanderCommand command(final LanderState landerState);
}
