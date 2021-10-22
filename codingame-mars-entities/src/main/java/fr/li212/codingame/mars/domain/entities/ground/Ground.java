package fr.li212.codingame.mars.domain.entities.ground;

import fr.li212.codingame.mars.domain.entities.Coordinate;

import java.util.List;

public class Ground {

    private final List<Surface> surfaces;

    private final Coordinate targetLandingCoordinates;

    public Ground(final List<Surface> surfaces) {
        this.surfaces = surfaces;
        final Surface landingSurface = surfaces.stream()
                .filter(surface -> surface.getStartCoordinate().getY() == surface.getEndCoordinate().getY())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No landing site available"));
        targetLandingCoordinates = new Coordinate(
                (int) (0.5 * (landingSurface.getEndCoordinate().getX() + landingSurface.getStartCoordinate().getX())),
                landingSurface.getStartCoordinate().getY());
    }

    public List<Surface> getSurfaces() {
        return surfaces;
    }

    public Coordinate getTargetLandingCoordinates() {
        return targetLandingCoordinates;
    }

    @Override
    public String toString() {
        return "Ground{" +
                "surfaces=" + surfaces +
                ", targetLandingCoordinates=" + targetLandingCoordinates +
                '}';
    }
}
