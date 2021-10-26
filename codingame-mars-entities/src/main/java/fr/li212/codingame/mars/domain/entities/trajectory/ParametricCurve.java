package fr.li212.codingame.mars.domain.entities.trajectory;

import java.util.List;

public class ParametricCurve {
    public static final int NUMBER_OF_POINTS = 300;
    private final List<ParametricPoint> parametricPoints;


    public ParametricCurve(final List<ParametricPoint> parametricPoints) {
        this.parametricPoints = parametricPoints;
    }

    public List<ParametricPoint> getParametricPoints() {
        return parametricPoints;
    }

    public int getIndexForTValue(final double t){
        return (int)t*NUMBER_OF_POINTS;
    }
}
