package fr.li212.codingame.mars.domain.entities.lander;

public class LanderCommand {
    private final int angle;
    private final int thrust;

    public LanderCommand(final int angle, final int thrust) {
        this.angle = angle;
        this.thrust = thrust;
    }

    public int getAngle() {
        return angle;
    }

    public int getThrust() {
        return thrust;
    }

    @Override
    public String toString() {
        return "LanderCommand{" +
                "angle=" + angle +
                ", thrust=" + thrust +
                '}';
    }
}
