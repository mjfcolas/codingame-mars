package fr.li212.codingame.mars.application.simulator;

import fr.li212.codingame.mars.application.simulator.levels.Level5;
import fr.li212.codingame.mars.simulator.StartSimulator;
import fr.li212.codingame.mars.simulator.engine.AskForIaComputation;
import javafx.application.Application;
import javafx.stage.Stage;

public class SimulatorApplication extends Application {
    private final static Level level = Level5.get();
    private final static AskForIaComputation ASK_FOR_IA_COMMAND = new AskForIaComputations(
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
