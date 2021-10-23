package fr.li212.codingame.mars.application.simulator;

import fr.li212.codingame.mars.domain.entities.Coordinate;
import fr.li212.codingame.mars.domain.entities.ground.Ground;
import fr.li212.codingame.mars.domain.entities.ground.Surface;
import fr.li212.codingame.mars.domain.entities.lander.LanderCommand;
import fr.li212.codingame.mars.domain.entities.lander.LanderState;
import fr.li212.codingame.mars.domain.entities.lander.Vector;
import fr.li212.codingame.mars.ia.command.CommandFromTrajectoryAndLanderState;
import fr.li212.codingame.mars.ia.command.implementations.BasicCommand;
import fr.li212.codingame.mars.ia.trajectory.ComputeTrajectory;
import fr.li212.codingame.mars.ia.trajectory.implementations.ComputeLinearTrajectory;
import fr.li212.codingame.mars.simulator.StartSimulator;
import fr.li212.codingame.mars.simulator.engine.AskForIaComputation;
import fr.li212.codingame.mars.simulator.engine.mechanics.AugmentedLanderState;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Arrays;

public class SimulatorApplication extends Application {
    private static final ComputeTrajectory COMPUTE_TRAJECTORY = new ComputeLinearTrajectory();
    private static final CommandFromTrajectoryAndLanderState COMMAND_FROM_TRAJECTORY_AND_LANDER_STATE = new BasicCommand();

    private final static Ground GROUND = new Ground(
            Arrays.asList(
                    new Surface(new Coordinate(0, 100), new Coordinate(1000, 500)),
                    new Surface(new Coordinate(1000, 500), new Coordinate(1500, 1500)),
                    new Surface(new Coordinate(1500, 1500), new Coordinate(3000, 1000)),
                    new Surface(new Coordinate(3000, 1000), new Coordinate(4000, 150)),
                    new Surface(new Coordinate(4000, 150), new Coordinate(5500, 150)),
                    new Surface(new Coordinate(5500, 150), new Coordinate(6999, 800)))
    );

    private final static AugmentedLanderState INITIAL_LANDER_STATE = new AugmentedLanderState(
            new LanderState(
                    new Coordinate(2500, 2700), new Vector(0, 0), 550, 0, 0),
            new Vector(0, 0),
            new Vector(0, 0)
    );

    private final static LanderCommand INITIAL_LANDER_COMMAND = new LanderCommand(0, 0);

    private final static AskForIaComputation ASK_FOR_IA_COMMAND = new AskForIaComputations(
            COMPUTE_TRAJECTORY,
            COMMAND_FROM_TRAJECTORY_AND_LANDER_STATE,
            GROUND);

    private final StartSimulator startSimulator = new StartSimulator(
            ASK_FOR_IA_COMMAND,
            GROUND,
            INITIAL_LANDER_COMMAND,
            INITIAL_LANDER_STATE);

    @Override
    public void start(final Stage primaryStage) {
        startSimulator.start(primaryStage);
    }

}
