package fr.li212.codingame.mars.simulator.engine;

import fr.li212.codingame.mars.domain.entities.IaComputation;
import fr.li212.codingame.mars.domain.entities.lander.LanderState;

public interface AskForIaComputation {
    IaComputation compute(final LanderState landerState);
}
