package fr.li212.codingame.mars.domain.entities.lander;

import fr.li212.codingame.mars.domain.entities.Coordinate;

public class LanderState {

    private final Coordinate coordinates;
    private final Vector speedVector;
    private final int remainingFuel;
    private final int angle;
    private final int thrustPower;

    public LanderState(final Coordinate coordinates, final Vector speedVector, final int remainingFuel, final int angle, final int thrustPower) {
        this.coordinates = coordinates;
        this.speedVector = speedVector;
        this.remainingFuel = remainingFuel;
        this.angle = angle;
        this.thrustPower = thrustPower;
    }

    public Coordinate getCoordinates() {
        return coordinates;
    }

    public Vector getSpeed() {
        return speedVector;
    }

    public int getRemainingFuel() {
        return remainingFuel;
    }

    public int getAngle() {
        return angle;
    }

    public int getThrustPower() {
        return thrustPower;
    }

    @Override
    public String toString() {
        return "LanderState{" +
                "coordinates=" + coordinates +
                ", speed=" + speedVector +
                ", remainingFuel=" + remainingFuel +
                ", angle=" + angle +
                ", thrustPower=" + thrustPower +
                '}';
    }
}
