package fr.li212.codingame.mars.application.codingame.io;

import fr.li212.codingame.mars.ia.io.TurnInput;
import fr.li212.codingame.mars.domain.entities.Coordinate;
import fr.li212.codingame.mars.domain.entities.lander.LanderState;
import fr.li212.codingame.mars.domain.entities.lander.Vector;

import java.util.Scanner;

public class CodingameTurnInput implements TurnInput {

    private final Scanner in;

    public CodingameTurnInput(final Scanner in) {
        this.in = in;
    }

    @Override
    public LanderState get() {
        int X = in.nextInt();
        int Y = in.nextInt();
        int HS = in.nextInt(); // the horizontal speed (in m/s), can be negative.
        int VS = in.nextInt(); // the vertical speed (in m/s), can be negative.
        int F = in.nextInt(); // the quantity of remaining fuel in liters.
        int R = in.nextInt(); // the rotation angle in degrees (-90 to 90).
        int P = in.nextInt(); // the thrust power (0 to 4).
        return new LanderState(
                new Coordinate(X, Y),
                new Vector(HS, VS),
                F,
                R,
                P);
    }
}
