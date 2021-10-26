package fr.li212.codingame.mars.application.simulator;

import fr.li212.codingame.mars.domain.entities.ground.Ground;
import fr.li212.codingame.mars.domain.entities.lander.LanderCommand;
import fr.li212.codingame.mars.simulator.engine.AugmentedLanderState;

public class Level {
    private final Ground ground;
    private final LanderCommand initialCommand;
    private final AugmentedLanderState augmentedLanderState;

    public Level(final Ground ground, final LanderCommand initialCommand, final AugmentedLanderState augmentedLanderState) {
        this.ground = ground;
        this.initialCommand = initialCommand;
        this.augmentedLanderState = augmentedLanderState;
    }

    public Ground getGround() {
        return ground;
    }

    public LanderCommand getInitialCommand() {
        return initialCommand;
    }

    public AugmentedLanderState getAugmentedLanderState() {
        return augmentedLanderState;
    }
}
