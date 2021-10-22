package fr.li212.codingame.mars.domain.entities.trajectory;

import fr.li212.codingame.mars.domain.entities.Coordinate;

public class ParametricPoint {
    public static final int MAX_T = 1000;

    private final int t;
    private final Coordinate coordinate;

    public ParametricPoint(final int t, final Coordinate coordinate) {
        this.t = t;
        this.coordinate = coordinate;
    }

    public int getT() {
        return t;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
