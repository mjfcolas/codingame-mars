package fr.li212.codingame.mars.domain.entities.lander;

public class Vector {
    private final double x;
    private final double y;

    public Vector(final double x, final double y) {
        this.x = x;
        this.y = y;
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

    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
