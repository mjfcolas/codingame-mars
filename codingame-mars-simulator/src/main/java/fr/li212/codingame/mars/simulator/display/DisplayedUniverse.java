package fr.li212.codingame.mars.simulator.display;

import fr.li212.codingame.mars.domain.entities.lander.LanderState;
import fr.li212.codingame.mars.simulator.engine.Universe;
import fr.li212.codingame.mars.simulator.engine.UniverseListener;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class DisplayedUniverse extends Parent implements UniverseListener {

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
    public void tick(final LanderState landerState) {
        Platform.runLater(() -> {
            final Rectangle lander = new Rectangle(PrintParameters.LANDER_WIDTH, PrintParameters.LANDER_HEIGHT);
            lander.setX(
                    landerState.getCoordinates().changeOrigin().getX() / PrintParameters.REDUCTION_FACTOR - PrintParameters.LANDER_WIDTH / 2);
            lander.setY(
                    landerState.getCoordinates().changeOrigin().getY() / PrintParameters.REDUCTION_FACTOR - PrintParameters.LANDER_HEIGHT
            );
            lander.setStroke(Color.WHITE);
            lander.setStrokeWidth(1);
            getChildren().add(lander);
        });

    }
}
