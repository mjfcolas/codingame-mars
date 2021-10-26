package fr.li212.codingame.mars.ia.command.implementations;

import fr.li212.codingame.mars.domain.entities.GlobalParameters;
import fr.li212.codingame.mars.domain.entities.ground.Ground;
import fr.li212.codingame.mars.domain.entities.lander.LanderState;
import fr.li212.codingame.mars.domain.entities.lander.Vector;
import fr.li212.codingame.mars.domain.entities.trajectory.ParametricCurve;
import fr.li212.codingame.mars.domain.entities.trajectory.ParametricPoint;
import fr.li212.codingame.mars.ia.command.PredictCommand;
import fr.li212.codingame.mars.domain.entities.lander.LanderCommand;

import java.util.Comparator;

public class BasicPredictCommand implements PredictCommand {

    private static final int MAX_TILT = 15;//(int)Math.toDegrees(Math.acos(GlobalParameters.GRAVITY_ACCELERATION/GlobalParameters.MAX_THRUST));
    private static final int MAX_SQUARED_DISTANCE = 10;
    @Override
    public LanderCommand command(final ParametricCurve trajectory, final LanderState landerState, final Ground ground) {

        final int minXForLandingSurface = trajectory.getParametricPoints().get(trajectory.getParametricPoints().size() - 1).getCoordinate().getX() - 500;
        final int maxXForLandingSurface = minXForLandingSurface + 1000;

        if(landerState.getCoordinates().getY() < 250){
            return new LanderCommand(0, GlobalParameters.MAX_THRUST);
        }

        if(landerState.getCoordinates().getX() > minXForLandingSurface && landerState.getCoordinates().getX() < maxXForLandingSurface){
            int thrust;
            int unboundedTilt = (int)landerState.getSpeed().getX();
            int tilt = unboundedTilt < 0 && unboundedTilt < -MAX_TILT ? MAX_TILT : (unboundedTilt > 0 && unboundedTilt > MAX_TILT ? MAX_TILT : unboundedTilt);
            if(-landerState.getSpeed().getY() > 40){
                thrust = GlobalParameters.MAX_THRUST;
            }else{
                thrust = GlobalParameters.MAX_THRUST - 1;
            }
            System.err.println("GO LANDING");
            return new LanderCommand(tilt, thrust);
        }

        final ParametricPoint closestPointInTrajectory = trajectory.getParametricPoints()
                .stream().min(Comparator.comparingInt(point -> point.getCoordinate().squareDistance(landerState.getCoordinates())))
                .orElseThrow(() -> new IllegalStateException("No closest point found"));
        final ParametricPoint pointAfterClosest = trajectory.getParametricPoints().get(trajectory.getIndexForTValue(closestPointInTrajectory.getT()));

        final int squareDistanceToClosestPoint = closestPointInTrajectory.getCoordinate().squareDistance(landerState.getCoordinates());

        final Vector direction;
        int thrust;
        int tilt;
        if(squareDistanceToClosestPoint < MAX_SQUARED_DISTANCE){
             direction = new Vector(closestPointInTrajectory.getCoordinate(), pointAfterClosest.getCoordinate()).unitary();
        }else{
            direction = new Vector(landerState.getCoordinates(), closestPointInTrajectory.getCoordinate()).unitary();
        }
        if(direction.getX() > 0){
            tilt = -MAX_TILT;
        }else if(direction.getX() < 0){
            tilt = MAX_TILT;
        }else {
            tilt = 0;
        }
        if(direction.getY() > 0){
            thrust = GlobalParameters.MAX_THRUST;
        }else {
            thrust= GlobalParameters.MAX_THRUST - 1;
        }

        return new LanderCommand(tilt, thrust);
    }
}
