package fr.li212.codingame.mars.simulator.engine.mechanics;

import fr.li212.codingame.mars.domain.entities.lander.LanderState;
import fr.li212.codingame.mars.domain.entities.lander.Vector;
import lombok.Value;

@Value
public class AugmentedLanderState {
    LanderState landerState;
    Vector thrustVector;
    Vector accelerationVector;
}
