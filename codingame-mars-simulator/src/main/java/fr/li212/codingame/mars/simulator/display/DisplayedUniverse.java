package fr.li212.codingame.mars.simulator.display;

import fr.li212.codingame.mars.domain.entities.Coordinate;
import fr.li212.codingame.mars.domain.entities.lander.Vector;
import fr.li212.codingame.mars.domain.entities.mechanics.LanderMechanicState;
import fr.li212.codingame.mars.domain.entities.trajectory.ParametricCurve;
import fr.li212.codingame.mars.simulator.engine.Universe;
import fr.li212.codingame.mars.simulator.engine.UniverseListener;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class DisplayedUniverse extends Parent implements UniverseListener {

    private final List<Shape> shapesToClean = new ArrayList<>();

    public DisplayedUniverse(final Universe universe) {
        universe.getGround().getSurfaces().forEach(surface -> {
            Line line = new Line();
            line.setStartX(surface.getStartCoordinate().changeOrigin().getX() / PrintParameters.REDUCTION_FACTOR);
            line.setStartY(surface.getStartCoordinate().changeOrigin().getY() / PrintParameters.REDUCTION_FACTOR);
            line.setEndX(surface.getEndCoordinate().changeOrigin().getX() / PrintParameters.REDUCTION_FACTOR);
            line.setEndY(surface.getEndCoordinate().changeOrigin().getY() / PrintParameters.REDUCTION_FACTOR);
            line.setStroke(Color.RED);
            line.setStrokeWidth(1);
            this.getChildren().add(line);
        });
    }

    @Override
    public void newState(final LanderMechanicState landerMechanicState) {
        Platform.runLater(() -> {
            this.printVector(
                    new Coordinate((int)landerMechanicState.getPosition().getX(), (int)landerMechanicState.getPosition().getY()),
                    landerMechanicState.getThrustVector(),
                    Color.YELLOW);
            this.printVector(
                    new Coordinate((int)landerMechanicState.getPosition().getX(), (int)landerMechanicState.getPosition().getY()),
                    landerMechanicState.getAcceleration(),
                    Color.rgb(154,236,219));
        });

    }

    private void printVector(final Coordinate startPoint, final Vector vector, final Paint color) {
        final Vector endPointVector = vector.multiply(100).add(new Vector(startPoint.getX(), startPoint.getY()));
        final Coordinate endPoint = new Coordinate((int) endPointVector.getX(), (int) endPointVector.getY());
        final Line vectorLine = new Line(
                startPoint.changeOrigin().getX() / PrintParameters.REDUCTION_FACTOR,
                startPoint.changeOrigin().getY() / PrintParameters.REDUCTION_FACTOR,
                endPoint.changeOrigin().getX() / PrintParameters.REDUCTION_FACTOR,
                endPoint.changeOrigin().getY() / PrintParameters.REDUCTION_FACTOR);
        vectorLine.setStroke(color);
        vectorLine.setStrokeWidth(3);
        getChildren().add(vectorLine);
    }

    @Override
    public void newTrajectory(final ParametricCurve parametricCurve) {
        Platform.runLater(() -> {
            getChildren().removeAll(shapesToClean);
            parametricCurve.getParametricPoints().forEach(parametricPoint -> {
                final Circle point = new Circle(
                        parametricPoint.getCoordinate().changeOrigin().getX() / PrintParameters.REDUCTION_FACTOR,
                        parametricPoint.getCoordinate().changeOrigin().getY() / PrintParameters.REDUCTION_FACTOR,
                        1,
                        Color.WHITE);
                shapesToClean.add(point);
                getChildren().add(point);
            });
        });
    }
}
