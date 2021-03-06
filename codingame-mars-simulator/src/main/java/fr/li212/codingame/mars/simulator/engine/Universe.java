package fr.li212.codingame.mars.simulator.engine;

import fr.li212.codingame.mars.domain.entities.Coordinate;
import fr.li212.codingame.mars.domain.entities.IaComputation;
import fr.li212.codingame.mars.domain.entities.ground.Ground;
import fr.li212.codingame.mars.domain.entities.lander.LanderCommand;
import fr.li212.codingame.mars.domain.entities.lander.LanderState;
import fr.li212.codingame.mars.domain.entities.mechanics.LanderMechanicState;
import fr.li212.codingame.mars.domain.entities.trajectory.ParametricCurve;
import fr.li212.codingame.mars.simulator.SimulatorParameters;
import fr.li212.codingame.mars.domain.entities.mechanics.ComputeNewState;

import javax.swing.event.EventListenerList;
import java.util.Timer;
import java.util.TimerTask;

public class Universe {
    private final AskForIaComputation askForIaComputation;

    private final EventListenerList listeners = new EventListenerList();

    private final Ground ground;
    private LanderCommand currentCommand;
    private AugmentedLanderState landerState;

    public Universe(
            final AskForIaComputation askForIaComputation,
            final Ground ground,
            final LanderCommand initialCommand,
            final AugmentedLanderState initialState) {
        this.askForIaComputation = askForIaComputation;
        this.ground = ground;
        this.currentCommand = initialCommand;
        this.landerState = initialState;
        triggerNewState(initialState.getLanderMechanicState());

        final Timer timer = new Timer();
        final TickTask tickTask = new TickTask();
        timer.schedule(tickTask, 0, SimulatorParameters.TICKS_DURATION_IN_MS);
    }

    private class TickTask extends TimerTask {
        @Override
        public void run() {
            final IaComputation iaComputation = askForIaComputation.compute(landerState.getLanderState());
            triggerNewTrajectory(iaComputation.getTrajectory());
            currentCommand = ComputeEffectiveCommand.get(iaComputation.getCommand(), landerState.getLanderState());
            final LanderMechanicState landerMechanicState = ComputeNewState.compute(landerState.getLanderState(), currentCommand, SimulatorParameters.TICK_SIMULATED_DURATION_IN_SECONDS);
            landerState = new AugmentedLanderState(
                    new LanderState(
                            new Coordinate((int) landerMechanicState.getPosition().getX(), (int) landerMechanicState.getPosition().getY()),
                            landerMechanicState.getSpeed(),
                            landerState.getLanderState().getRemainingFuel(),
                            currentCommand.getAngle(),
                            currentCommand.getThrust()
                    ),
                    landerMechanicState);
            triggerNewState(landerMechanicState);
        }
    }

    public void subscribe(final UniverseListener listener) {
        this.listeners.add(UniverseListener.class, listener);
    }

    public Ground getGround() {
        return ground;
    }

    private void triggerNewState(final LanderMechanicState landerMechanicState) {
        for (final UniverseListener listener : listeners.getListeners(UniverseListener.class)) {
            listener.newState(landerMechanicState);
        }
    }

    private void triggerNewTrajectory(final ParametricCurve parametricCurve) {
        for (final UniverseListener listener : listeners.getListeners(UniverseListener.class)) {
            listener.newTrajectory(parametricCurve);
        }
    }
}
