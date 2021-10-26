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

public class Level4 {

    private final static Ground GROUND = new Ground(
            Arrays.asList(
                    new Surface(new Coordinate(0, 100), new Coordinate(300, 1500)),
                    new Surface(new Coordinate(300, 1500), new Coordinate(350, 1400)),
                    new Surface(new Coordinate(350, 1400), new Coordinate(500, 2000)),
                    new Surface(new Coordinate(500, 2000), new Coordinate(800, 1800)),
                    new Surface(new Coordinate(800, 1800), new Coordinate(1000, 2500)),
                    new Surface(new Coordinate(1000, 2500), new Coordinate(1200, 2100)),
                    new Surface(new Coordinate(1200, 2100), new Coordinate(1500, 2400)),
                    new Surface(new Coordinate(1500, 2400), new Coordinate(2000, 1000)),
                    new Surface(new Coordinate(2000, 1000), new Coordinate(2200, 500)),
                    new Surface(new Coordinate(2200, 500), new Coordinate(2500, 100)),
                    new Surface(new Coordinate(2500, 100), new Coordinate(2900, 800)),
                    new Surface(new Coordinate(2900, 800), new Coordinate(3000, 500)),
                    new Surface(new Coordinate(3000, 500), new Coordinate(3200, 1000)),
                    new Surface(new Coordinate(3200, 1000), new Coordinate(3500, 2000)),
                    new Surface(new Coordinate(3500, 2000), new Coordinate(3800, 800)),
                    new Surface(new Coordinate(3800, 800), new Coordinate(4000, 200)),
                    new Surface(new Coordinate(4000, 200), new Coordinate(5000, 200)),
                    new Surface(new Coordinate(5000, 200), new Coordinate(5500, 1500)),
                    new Surface(new Coordinate(5500, 1500), new Coordinate(6999, 2800)))
    );

    private final static Vector LANDER_INITIAL_SPEED = new Vector(100, 0);
    private final static Coordinate LANDER_INITIAL_POSITION = new Coordinate(500, 2700);
    private final static AugmentedLanderState INITIAL_LANDER_STATE = new AugmentedLanderState(
            new LanderState(
                    LANDER_INITIAL_POSITION, LANDER_INITIAL_SPEED, 800, -90, 0),
            new LanderMechanicState(
                    new Vector(0, 0),
                    new Vector(0, 0),
                    LANDER_INITIAL_SPEED,
                    new Vector(LANDER_INITIAL_POSITION)
            ));

    private final static LanderCommand INITIAL_LANDER_COMMAND = new LanderCommand(-90, 0);
    public static Level get(){
        return new Level(
                GROUND,
                INITIAL_LANDER_COMMAND,
                INITIAL_LANDER_STATE
        );
    }
}
