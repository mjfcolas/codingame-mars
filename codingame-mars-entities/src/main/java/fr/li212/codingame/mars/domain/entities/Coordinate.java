package fr.li212.codingame.mars.domain.entities;

import java.util.Objects;

public class Coordinate {

    private final int x;
    private final int y;

    public Coordinate(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordinate changeOrigin() {
        return new Coordinate(x, GlobalParameters.ZONE_HEIGHT - y);
    }

    public int squareDistance(final Coordinate otherCoordinate) {
        return (int)(Math.pow(otherCoordinate.getX() - this.x, 2) + Math.pow(otherCoordinate.getY() -this.y, 2));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
