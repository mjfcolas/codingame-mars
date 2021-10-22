package fr.li212.codingame.mars.application.codingame.io;

import fr.li212.codingame.mars.domain.entities.Coordinate;
import fr.li212.codingame.mars.domain.entities.ground.Ground;
import fr.li212.codingame.mars.domain.entities.ground.Surface;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InitializationInput {

    private final Scanner in;

    public InitializationInput(final Scanner in) {
        this.in = in;
    }

    public Ground getGround() {
        final List<Surface> surfaces = new ArrayList<>();
        int N = in.nextInt(); // the number of points used to draw the surface of Mars.
        Coordinate lastCoordinate = null;
        for (int i = 0; i < N; i++) {
            int landX = in.nextInt(); // X coordinate of a surface point. (0 to 6999)
            int landY = in.nextInt(); // Y coordinate of a surface point. By linking all the points together in a sequential fashion, you form the surface of Mars.
            final Coordinate newCoordinates = new Coordinate(landX, landY);
            if (lastCoordinate != null) {
                surfaces.add(new Surface(
                        lastCoordinate,
                        newCoordinates
                ));
            }
            lastCoordinate = newCoordinates;
        }
        return new Ground(surfaces);
    }
}
