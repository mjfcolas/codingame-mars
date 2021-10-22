package fr.li212.codingame.mars.application.codingame.io;

import fr.li212.codingame.mars.ia.io.TurnOutput;
import fr.li212.codingame.mars.domain.entities.lander.LanderCommand;

public class CodingameTurnOutput implements TurnOutput {
    @Override
    public void output(final LanderCommand command) {
        System.out.println(command.getAngle() + " " + command.getThrust());
    }
}
