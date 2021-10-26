package fr.li212.codingame.mars.simulator.engine;

import fr.li212.codingame.mars.domain.entities.lander.LanderState;
import fr.li212.codingame.mars.domain.entities.lander.Vector;
import fr.li212.codingame.mars.domain.entities.mechanics.LanderMechanicState;
import lombok.Value;

@Value
public class AugmentedLanderState {
    LanderState landerState;
    LanderMechanicState landerMechanicState;
}
