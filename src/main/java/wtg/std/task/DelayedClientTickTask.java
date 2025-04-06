package wtg.std.task;

import net.minecraft.client.Minecraft;

import java.util.function.Consumer;

/**
 * A task that executes an action on the Minecraft client after a specified delay and duration.
 */
@SuppressWarnings("unused")
public class DelayedClientTickTask extends AbstractDelayedTickTask<Minecraft> {

    /**
     * Constructs a new DelayedClientTickTask.
     *
     * @param duration the duration for which the task should run
     * @param action   the action to be executed on the Minecraft client
     * @param delay    the delay before the task starts executing
     */
    public DelayedClientTickTask(int duration, Consumer<Minecraft> action, int delay) {
        super(duration, action, delay);
    }

    /**
     * Constructs a new DelayedClientTickTask from an existing ClientTickTask.
     *
     * @param clientTickTask the existing ClientTickTask
     * @param delay          the delay before the task starts executing
     */
    public DelayedClientTickTask(ClientTickTask clientTickTask, int delay) {
        this(clientTickTask.duration.get(), clientTickTask.getAction(), delay);
    }

    /**
     * Executes the action on the Minecraft client if the delay has passed and the task is incomplete.
     *
     * @param minecraft the Minecraft client instance
     */
    @Override
    public void accept(Minecraft minecraft) {
        if (delay.decrementAndGet() > 0) {
            return;
        }
        if (isIncomplete()) {
            getAction().accept(minecraft);
            if (duration.decrementAndGet() > 0) {
                return;
            }
            complete();
        }
    }

}