package fr.li212.codingame.mars.domain.entities.trajectory;

import fr.li212.codingame.mars.domain.entities.Coordinate;

public class ParametricPoint {
    private final double t;
    private final Coordinate coordinate;

    public ParametricPoint(final double t, final Coordinate coordinate) {
        this.t = t;
        this.coordinate = coordinate;
    }

    public double getT() {
        return t;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
