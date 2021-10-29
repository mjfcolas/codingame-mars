package fr.li212.codingame.mars.domain.entities.lander;

import fr.li212.codingame.mars.domain.entities.Coordinate;

public class Vector {
    private final double x;
    private final double y;

    public Vector(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public Vector(final Coordinate start, final Coordinate end){
        this.x = end.getX() - start.getX();
        this.y = end.getY() - start.getY();
    }

    public Vector(final Coordinate absolute){
        this.x = absolute.getX();
        this.y = absolute.getY();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double squaredNorm(){
        return x * x + y * y;
    }

    public Vector add(final Vector toAdd){
        return new Vector(this.x + toAdd.getX(), this.y + toAdd.getY());
    }

    public Vector multiply(final int coefficient){
        return new Vector(this.x * coefficient, this.y * coefficient);
    }

    public Vector unitary(){
        return new Vector(x/Math.sqrt(squaredNorm()), y/Math.sqrt(squaredNorm()));
    }

    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
