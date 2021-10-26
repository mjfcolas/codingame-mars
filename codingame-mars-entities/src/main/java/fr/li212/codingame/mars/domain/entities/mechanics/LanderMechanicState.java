package fr.li212.codingame.mars.domain.entities.mechanics;

import fr.li212.codingame.mars.domain.entities.lander.Vector;

public class LanderMechanicState {
    private final Vector thrustVector;
    private final Vector acceleration;
    private final Vector speed;
    private final Vector position;

    public LanderMechanicState(final Vector thrustVector, final Vector acceleration, final Vector speed, final Vector position) {
        this.thrustVector = thrustVector;
        this.acceleration = acceleration;
        this.speed = speed;
        this.position = position;
    }

    public Vector getThrustVector() {
        return thrustVector;
    }

    public Vector getAcceleration() {
        return acceleration;
    }

    public Vector getSpeed() {
        return speed;
    }

    public Vector getPosition() {
        return position;
    }
}
