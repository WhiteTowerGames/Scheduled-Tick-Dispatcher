package wtg.std.task;

    import net.minecraft.server.MinecraftServer;

    import java.util.function.Consumer;

    /**
     * A task that executes an action on the Minecraft server after a specified delay, for a set amount of ticks.
     */
    public class DelayedServerTickTask extends AbstractDelayedTickTask<MinecraftServer> {

        /**
         * Constructs a new DelayedServerTickTask.
         *
         * @param duration the duration for which the task should run
         * @param action the action to be executed on the server
         * @param delay the delay before the task starts
         */
        public DelayedServerTickTask(int duration, Consumer<MinecraftServer> action, int delay) {
            super(duration, action, delay);
        }

        /**
         * Constructs a new DelayedServerTickTask from an existing ServerTickTask.
         *
         * @param serverTickTask the existing ServerTickTask
         * @param delay the delay before the task starts
         */
        public DelayedServerTickTask(ServerTickTask serverTickTask, int delay) {
            this(serverTickTask.duration.get(), serverTickTask.getAction(), delay);
        }

        public static DelayedServerTickTask restart(DelayedServerTickTask serverTickTask) {
            return new DelayedServerTickTask(serverTickTask.initialDuration, serverTickTask.getAction(), serverTickTask.delay.get());
        }

        /**
         * Attempts to execute the task on the given Minecraft server.
         * If the delay has not expired, the task will not execute.
         *
         * @param server the Minecraft server
         */
        @Override
        public void accept(MinecraftServer server) {
            if (delay.decrementAndGet() > 0) {
                return;
            }
            if (isIncomplete()) {
                getAction().accept(server);
                if (duration.decrementAndGet() > 0) {
                    return;
                }
                complete();
            }
        }
    }