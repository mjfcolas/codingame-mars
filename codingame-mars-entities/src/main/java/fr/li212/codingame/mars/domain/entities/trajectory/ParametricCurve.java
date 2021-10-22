package fr.li212.codingame.mars.domain.entities.trajectory;

import java.util.List;

public class ParametricCurve {
    private final List<ParametricPoint> parametricPoints;


    public ParametricCurve(final List<ParametricPoint> parametricPoints) {
        this.parametricPoints = parametricPoints;
    }

    public List<ParametricPoint> getParametricPoints() {
        return parametricPoints;
    }
}
