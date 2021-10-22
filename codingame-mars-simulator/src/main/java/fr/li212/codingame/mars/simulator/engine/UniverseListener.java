package fr.li212.codingame.mars.simulator.engine;

import fr.li212.codingame.mars.domain.entities.lander.LanderState;

import java.util.EventListener;

public interface UniverseListener extends EventListener {
    void tick(final LanderState landerState);
}
