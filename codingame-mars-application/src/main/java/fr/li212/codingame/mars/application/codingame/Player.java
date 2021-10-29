package fr.li212.codingame.mars.application.codingame;

import fr.li212.codingame.mars.application.codingame.io.CodingameTurnInput;
import fr.li212.codingame.mars.application.codingame.io.CodingameTurnOutput;
import fr.li212.codingame.mars.application.codingame.io.InitializationInput;
import fr.li212.codingame.mars.domain.entities.ground.Ground;
import fr.li212.codingame.mars.domain.entities.lander.LanderState;
import fr.li212.codingame.mars.ia.CommandLander;
import fr.li212.codingame.mars.ia.command.PredictCommand;
import fr.li212.codingame.mars.ia.command.implementations.PredictCommandWithAdjustNewtonTrajectory;
import fr.li212.codingame.mars.ia.io.TurnInput;
import fr.li212.codingame.mars.ia.io.TurnOutput;

import java.util.Scanner;

/**
 * Save the Planet.
 * Use less Fossil Fuel.
 **/
class Player {

    private static final Scanner IN = new Scanner(System.in);
    private static final InitializationInput INITIALIZATION_INPUT = new InitializationInput(IN);
    private static final TurnInput TURN_INPUT = new CodingameTurnInput(IN);
    private static final TurnOutput TURN_OUTPUT = new CodingameTurnOutput();
    private static final PredictCommand COMMAND_FROM_TRAJECTORY_AND_LANDER_STATE = new PredictCommandWithAdjustNewtonTrajectory();

    public static void main(final String[] args) {
        final Ground ground = INITIALIZATION_INPUT.getGround();
        System.err.println(ground);
        final CommandLander commandLander = new CommandLander(
                COMMAND_FROM_TRAJECTORY_AND_LANDER_STATE,
                ground);
        while (true) {
            final LanderState landerState = TURN_INPUT.get();
            System.err.println(landerState);
            TURN_OUTPUT.output(commandLander.command(landerState).getCommand());
        }
    }
}
