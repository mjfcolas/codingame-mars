package fr.li212.codingame.mars.application.simulator;

import fr.li212.codingame.mars.application.simulator.levels.Level2;
import fr.li212.codingame.mars.application.simulator.levels.Level3;
import fr.li212.codingame.mars.application.simulator.levels.Level4;
import fr.li212.codingame.mars.application.simulator.levels.Level5;
import fr.li212.codingame.mars.domain.entities.Coordinate;
import fr.li212.codingame.mars.domain.entities.ground.Ground;
import fr.li212.codingame.mars.domain.entities.ground.Surface;
import fr.li212.codingame.mars.domain.entities.lander.LanderCommand;
import fr.li212.codingame.mars.domain.entities.lander.LanderState;
import fr.li212.codingame.mars.domain.entities.lander.Vector;
import fr.li212.codingame.mars.domain.entities.mechanics.LanderMechanicState;
import fr.li212.codingame.mars.ia.command.PredictCommand;
import fr.li212.codingame.mars.ia.command.implementations.BasicPredictCommand;
import fr.li212.codingame.mars.ia.command.implementations.PredictCommandWithAdjustNewtonTrajectory;
import fr.li212.codingame.mars.simulator.StartSimulator;
import fr.li212.codingame.mars.simulator.engine.AskForIaComputation;
import fr.li212.codingame.mars.simulator.engine.AugmentedLanderState;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Arrays;

public class SimulatorApplication extends Application {
    private static final PredictCommand COMMAND_FROM_TRAJECTORY_AND_LANDER_STATE = new PredictCommandWithAdjustNewtonTrajectory();

    private final static Level level = Level2.get();
    private final static AskForIaComputation ASK_FOR_IA_COMMAND = new AskForIaComputations(
            COMMAND_FROM_TRAJECTORY_AND_LANDER_STATE,
            level.getGround());

    private final StartSimulator startSimulator = new StartSimulator(
            ASK_FOR_IA_COMMAND,
            level.getGround(),
            level.getInitialCommand(),
            level.getAugmentedLanderState());

    @Override
    public void start(final Stage primaryStage) {
        startSimulator.start(primaryStage);
    }

}
