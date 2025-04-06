package wtg.std.task;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * Abstract class representing a tick task that starts after a set amount of ticks.
 *
 * @param <T> the type of the object that the task will operate on: {@link Minecraft} or {@link MinecraftServer}
 */
public abstract class AbstractDelayedTickTask<T> extends AbstractTickTask<T> {

    /**
     * The delay before the task starts, in ticks.
     */
    public AtomicInteger delay;

    /**
     * Constructs a new AbstractDelayedTickTask.
     *
     * @param duration the duration of the task in ticks
     * @param action   the action to be performed by the task
     * @param delay    the delay before the task starts, in ticks
     */
    public AbstractDelayedTickTask(int duration, Consumer<T> action, int delay) {
        super(duration, action);
        if (delay <= 0) {
            throw new IllegalArgumentException("Delay must be greater than 0");
        }
        this.delay = new AtomicInteger(delay);
    }

}