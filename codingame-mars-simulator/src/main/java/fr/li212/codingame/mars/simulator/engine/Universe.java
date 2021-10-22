package fr.li212.codingame.mars.simulator.engine;

import fr.li212.codingame.mars.domain.entities.ground.Ground;
import fr.li212.codingame.mars.domain.entities.lander.LanderCommand;
import fr.li212.codingame.mars.domain.entities.lander.LanderState;
import fr.li212.codingame.mars.simulator.SimulatorParameters;

import javax.swing.event.EventListenerList;
import java.util.Timer;
import java.util.TimerTask;

public class Universe {
    private final AskForLanderCommand askForLanderCommand;

    private final EventListenerList listeners = new EventListenerList();

    private final Ground ground;
    private LanderCommand currentCommand;
    private LanderState landerState;

    public Universe(
            final AskForLanderCommand askForLanderCommand,
            final Ground ground,
            final LanderCommand initialCommand,
            final LanderState initialState) {
        this.askForLanderCommand = askForLanderCommand;
        this.ground = ground;
        this.currentCommand = initialCommand;
        this.landerState = initialState;
        triggerAllListeners();

        final Timer timer = new Timer();
        final TickTask tickTask = new TickTask();
        timer.schedule(tickTask, 0, SimulatorParameters.TICKS_DURATION_IN_MS);
    }

    private class TickTask extends TimerTask {
        @Override
        public void run() {
            landerState = ComputeNewState.compute(landerState, currentCommand);
            currentCommand = askForLanderCommand.command(landerState);
            triggerAllListeners();
        }
    }

    public void subscribe(final UniverseListener listener) {
        this.listeners.add(UniverseListener.class, listener);
    }

    public Ground getGround() {
        return ground;
    }

    private void triggerAllListeners() {
        for (final UniverseListener listener : listeners.getListeners(UniverseListener.class)) {
            listener.tick(landerState);
        }
    }
}
