package wtg.std.task;

import net.minecraft.server.MinecraftServer;

import java.util.function.Consumer;

/**
 * A task that performs an action on the Minecraft server at each tick for a specified duration.
 */
public class ServerTickTask extends AbstractTickTask<MinecraftServer> {

    /**
     * Constructs a ServerTickTask with the specified duration and action.
     *
     * @param duration the number of ticks the task should run
     * @param action the action to perform on the Minecraft server
     */
    public ServerTickTask(int duration, Consumer<MinecraftServer> action) {
        super(duration, action);
    }

    /**
     * Constructs a ServerTickTask from a DelayedServerTickTask.
     *
     * @param delayedServerTickTask the delayed task to convert
     */
    public ServerTickTask(DelayedServerTickTask delayedServerTickTask) {
        this(delayedServerTickTask.duration.get(), delayedServerTickTask.getAction());
    }


    /**
     * Restarts the task with the initial duration and action.
     *
     * @return a new instance of ServerTickTask with the initial duration and action
     */
    public ServerTickTask restart() {
        return new ServerTickTask(this.initialDuration, getAction());
    }

    /**
     * Performs the task's action on the Minecraft server if the task is incomplete.
     * Decrements the duration and completes the task if the duration reaches zero.
     *
     * @param server the Minecraft server to perform the action on
     */
    @Override
    public void accept(MinecraftServer server) {
        super.accept(server);
    }

}
