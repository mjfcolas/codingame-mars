package fr.li212.codingame.mars.application.simulator;

import fr.li212.codingame.mars.application.simulator.levels.Level2;
import fr.li212.codingame.mars.ia.command.PredictCommand;
import fr.li212.codingame.mars.ia.command.implementations.PredictCommandWithAdjustNewtonTrajectory;
import fr.li212.codingame.mars.simulator.StartSimulator;
import fr.li212.codingame.mars.simulator.engine.AskForIaComputation;
import javafx.application.Application;
import javafx.stage.Stage;

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
