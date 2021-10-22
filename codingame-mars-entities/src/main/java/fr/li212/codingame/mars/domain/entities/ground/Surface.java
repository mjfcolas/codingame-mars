package fr.li212.codingame.mars.domain.entities.ground;

import fr.li212.codingame.mars.domain.entities.Coordinate;

public class Surface {
    private final Coordinate startCoordinate;
    private final Coordinate endCoordinate;

    public Surface(final Coordinate startCoordinate, final Coordinate endCoordinate) {
        this.startCoordinate = startCoordinate;
        this.endCoordinate = endCoordinate;
    }

    public Coordinate getStartCoordinate() {
        return startCoordinate;
    }

    public Coordinate getEndCoordinate() {
        return endCoordinate;
    }

    @Override
    public String toString() {
        return "Surface{" +
                "startCoordinate=" + startCoordinate +
                ", endCoordinate=" + endCoordinate +
                '}';
    }
}
