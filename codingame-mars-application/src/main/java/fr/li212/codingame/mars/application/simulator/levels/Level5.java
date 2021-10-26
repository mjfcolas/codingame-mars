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

public class Level5 {

    private final static Ground GROUND = new Ground(
            Arrays.asList(
                    new Surface(new Coordinate(0, 1000), new Coordinate(500, 2100)),
                    new Surface(new Coordinate(500 , 2100), new Coordinate(1500, 2100)),
                    new Surface(new Coordinate(1500, 2100), new Coordinate(2000, 200)),
                    new Surface(new Coordinate(2000, 200), new Coordinate(6999, 500))));

    private final static Vector LANDER_INITIAL_SPEED = new Vector(-50, 0);
    private final static Coordinate LANDER_INITIAL_POSITION = new Coordinate(6500, 2700);
    private final static AugmentedLanderState INITIAL_LANDER_STATE = new AugmentedLanderState(
            new LanderState(
                    LANDER_INITIAL_POSITION, LANDER_INITIAL_SPEED, 800, 90, 0),
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
