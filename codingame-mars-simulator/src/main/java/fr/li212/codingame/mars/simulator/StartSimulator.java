package fr.li212.codingame.mars.simulator;

import fr.li212.codingame.mars.domain.entities.GlobalParameters;
import fr.li212.codingame.mars.domain.entities.ground.Ground;
import fr.li212.codingame.mars.domain.entities.lander.LanderCommand;
import fr.li212.codingame.mars.domain.entities.lander.LanderState;
import fr.li212.codingame.mars.simulator.display.DisplayedUniverse;
import fr.li212.codingame.mars.simulator.display.PrintParameters;
import fr.li212.codingame.mars.simulator.engine.AskForIaComputation;
import fr.li212.codingame.mars.simulator.engine.Universe;
import fr.li212.codingame.mars.simulator.engine.mechanics.AugmentedLanderState;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class StartSimulator {

    private final Universe universe;
    private final DisplayedUniverse displayedUniverse;

    public StartSimulator(
            final AskForIaComputation askForIaComputation,
            final Ground ground,
            final LanderCommand initialCommand,
            final AugmentedLanderState initialLanderState) {
        this.universe = new Universe(askForIaComputation, ground, initialCommand, initialLanderState);
        this.displayedUniverse = new DisplayedUniverse(universe);
    }

    public void start(final Stage primaryStage) {
        final Group root = new Group();
        universe.subscribe(displayedUniverse);

        root.getChildren().add(displayedUniverse);
        Scene scene = new Scene(root,
                GlobalParameters.ZONE_WIDTH / PrintParameters.REDUCTION_FACTOR,
                GlobalParameters.ZONE_HEIGHT / PrintParameters.REDUCTION_FACTOR, Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
