package fr.li212.codingame.mars.domain.entities.ground;

import fr.li212.codingame.mars.domain.entities.Coordinate;

import java.util.List;

public class Ground {

    private final List<Surface> surfaces;

    private final Surface landingSurface;

    public Ground(final List<Surface> surfaces) {
        this.surfaces = surfaces;
        this.landingSurface = surfaces.stream()
                .filter(surface -> surface.getStartCoordinate().getY() == surface.getEndCoordinate().getY())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No landing site available"));
    }

    public List<Surface> getSurfaces() {
        return surfaces;
    }

    public Surface getLandingSurface() {
        return landingSurface;
    }

    @Override
    public String toString() {
        return "Ground{" +
                "surfaces=" + surfaces +
                ", targetLandingCoordinates=" + landingSurface +
                '}';
    }
}
