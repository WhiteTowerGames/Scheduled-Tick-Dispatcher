package wtg.std.task;

import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * A task that executes an action on the Minecraft client every tick for a specified duration.
 */
public class ClientTickTask extends AbstractTickTask<Minecraft> {

    /**
     * Constructs a new ClientTickTask.
     *
     * @param duration the duration for which the task should run
     * @param action the action to be executed on each tick
     */
    public ClientTickTask(int duration, Consumer<Minecraft> action) {
        super(duration, action);
    }

    /**
     * Constructs a new ClientTickTask from a DelayedClientTickTask.
     *
     * @param delayedClientTickTask the delayed task to convert
     */
    public ClientTickTask(@NotNull DelayedClientTickTask delayedClientTickTask){
        this(delayedClientTickTask.duration.get(), delayedClientTickTask.getAction());
    }


    public ClientTickTask restart() {
        return new ClientTickTask(this.initialDuration, getAction());
    }

    /**
     * Accepts the Minecraft client and executes the action.
     *
     * @param client the Minecraft client
     */
    @Override
    public void accept(Minecraft client) {
        super.accept(client);
    }

}
