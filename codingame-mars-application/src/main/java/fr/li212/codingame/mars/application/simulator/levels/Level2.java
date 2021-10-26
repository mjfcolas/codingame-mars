package fr.li212.codingame.mars.application.simulator.levels;

import fr.li212.codingame.mars.application.simulator.Level;
import fr.li212.codingame.mars.domain.entities.Coordinate;
import fr.li212.codingame.mars.domain.entities.ground.Ground;
import fr.li212.codingame.mars.domain.entities.ground.Surface;
import fr.li212.codingame.mars.domain.entities.lander.LanderCommand;
import fr.li212.codingame.mars.domain.entities.lander.LanderState;
import fr.li212.codingame.mars.domain.entities.lander.Vector;
import fr.li212.codingame.mars.domain.entities.mechanics.LanderMechanicState;
import fr.li212.codingame.mars.simulator.engine.AugmentedLanderState;

import java.util.Arrays;

public class Level2 {

    private final static Ground GROUND = new Ground(
            Arrays.asList(
                    new Surface(new Coordinate(0, 100), new Coordinate(1000, 500)),
                    new Surface(new Coordinate(1000, 500), new Coordinate(1500, 100)),
                    new Surface(new Coordinate(1500, 100), new Coordinate(3000, 100)),
                    new Surface(new Coordinate(3000, 100), new Coordinate(3500, 500)),
                    new Surface(new Coordinate(3500, 500), new Coordinate(3700, 200)),
                    new Surface(new Coordinate(3700, 200), new Coordinate(5000, 1500)),
                    new Surface(new Coordinate(5000, 1500), new Coordinate(5800, 300)),
                    new Surface(new Coordinate(5800, 300), new Coordinate(6000, 1000)),
                    new Surface(new Coordinate(6000, 1000), new Coordinate(6999, 200)))
    );

    private final static Vector LANDER_INITIAL_SPEED = new Vector(-100, 0);
    private final static Coordinate LANDER_INITIAL_POSITION = new Coordinate(6500, 2800);
    private final static AugmentedLanderState INITIAL_LANDER_STATE = new AugmentedLanderState(
            new LanderState(
                    LANDER_INITIAL_POSITION, LANDER_INITIAL_SPEED, 600, 90, 0),
            new LanderMechanicState(
                    new Vector(0, 0),
                    new Vector(0, 0),
                    LANDER_INITIAL_SPEED,
                    new Vector(LANDER_INITIAL_POSITION)
            ));

    private final static LanderCommand INITIAL_LANDER_COMMAND = new LanderCommand(90, 0);
    public static Level get(){
        return new Level(
                GROUND,
                INITIAL_LANDER_COMMAND,
                INITIAL_LANDER_STATE
        );
    }
}
