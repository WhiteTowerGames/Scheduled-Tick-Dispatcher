package wtg.std.task;

import net.minecraft.server.MinecraftServer;
import wtg.std.BackgroundLifecycleOperationsWrapper;
import wtg.std.ScheduledTickDispatcher;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Enum representing different server event phases.
 */
public enum ServerEvents {

    START_SERVER_TICK(BackgroundLifecycleOperationsWrapper::getStartServerTickTasks,
            BackgroundLifecycleOperationsWrapper::getStartServerTickTasksDelayed),
    END_SERVER_TICK(BackgroundLifecycleOperationsWrapper::getEndServerTickTasks,
            BackgroundLifecycleOperationsWrapper::getEndServerTickTasksDelayed),
    START_SERVER_WORLD_TICK(BackgroundLifecycleOperationsWrapper::getStartServerWorldTickTasks,
            BackgroundLifecycleOperationsWrapper::getStartServerWorldTickTasksDelayed),
    END_SERVER_WORLD_TICK(BackgroundLifecycleOperationsWrapper::getEndServerWorldTickTasks,
            BackgroundLifecycleOperationsWrapper::getEndServerWorldTickTasksDelayed);

    private final Supplier<List<ServerTickTask>> tasks;
    private final Supplier<List<DelayedServerTickTask>> delayedTasks;
    private final int MAX_TASKS_PER_PHASE = 30;

    /**
     * Constructor for ServerEvents.
     *
     * @param tasks Supplier for the set of server tick tasks.
     * @param delayedTasks Supplier for the set of delayed server tick tasks.
     */
    ServerEvents(Supplier<List<ServerTickTask>> tasks, Supplier<List<DelayedServerTickTask>> delayedTasks) {
        this.tasks = tasks;
        this.delayedTasks = delayedTasks;
    }

    /**
     * Registers a server tick task.
     *
     * @param task The server tick task to register.
     */
    private void register(ServerTickTask task) {
        tasks.get().add(task);
    }

    /**
     * Registers a delayed server tick task.
     *
     * @param task The delayed server tick task to register.
     */
    private void register(DelayedServerTickTask task) {
        delayedTasks.get().add(task);
    }

    /**
     * Registers a server tick task that has not been previously initialized at a specific phase.
     *
     * @param phase The server event phase.
     * @param action The action to perform.
     * @param duration The duration of the task.
     * @return The registered server tick task.
     */
    public static ServerTickTask registerAt(ServerEvents phase, Consumer<MinecraftServer> action, int duration) {
        if (phase.tasks.get().size() >= phase.MAX_TASKS_PER_PHASE) {
            phaseOverflow(phase);
            return null;
        }
        ServerTickTask task = new ServerTickTask(duration, action);
        phase.register(task);
        return task;
    }

    /**
     * Registers a delayed server tick task that has not been previously initialized at a specific phase.
     *
     * @param phase The server event phase.
     * @param action The action to perform.
     * @param duration The duration of the task.
     * @param delay The delay before the task starts.
     * @return The registered delayed server tick task.
     */
    public static DelayedServerTickTask registerAt(ServerEvents phase, Consumer<MinecraftServer> action, int duration, int delay) {
        if (phase.delayedTasks.get().size() >= phase.MAX_TASKS_PER_PHASE) {
            phaseOverflow(phase);
            return null;
        }
        DelayedServerTickTask task = new DelayedServerTickTask(duration, action, delay);
        phase.register(task);
        return task;
    }

    /**
     * Registers a server tick task that has been previously initialized at a specific phase.
     *
     * @param phase The server event phase.
     * @param task The server tick task to register.
     * @return The registered server tick task.
     */
    public static ServerTickTask registerAt(ServerEvents phase, ServerTickTask task) {
        if (phase.tasks.get().size() >= phase.MAX_TASKS_PER_PHASE) {
            phaseOverflow(phase);
            return null;
        }
        phase.register(task);
        return task;
    }

    /**
     * Registers a delayed server tick task that has been previously initialized at a specific phase.
     *
     * @param phase The server event phase.
     * @param task The delayed server tick task to register.
     * @return The registered delayed server tick task.
     */
    public static DelayedServerTickTask registerAt(ServerEvents phase, DelayedServerTickTask task) {
        if (phase.delayedTasks.get().size() >= phase.MAX_TASKS_PER_PHASE) {
            phaseOverflow(phase);
            return null;
        }
        phase.register(task);
        return task;
    }

    /**
     * Unregisters completed tasks from a specific phase.
     *
     * @param phase The server event phase.
     */
    public static void unregisterCompletedTasks(ServerEvents phase) {
        // For server tick tasks
        phase.tasks.get().removeIf(Predicate.not(ServerTickTask::isIncomplete));

        // For delayed server tick tasks
        phase.delayedTasks.get().removeIf(Predicate.not(DelayedServerTickTask::isIncomplete));
    }

    /**
     * Unregisters a server tick task from a specific phase, with error logging if not found.
     * Used for manual unregistration.
     *
     * @param task The server tick task to unregister.
     * @param phase The server event phase.
     */
    public static void unregister(ServerTickTask task, ServerEvents phase) {
        if (phase.tasks.get().contains(task)) {
            phase.tasks.get().remove(task);
        } else {
            logError(task, phase);
        }
    }

    /**
     * Unregisters a delayed server tick task from a specific phase, with error logging if not found.
     * Used for manual unregistration.
     *
     * @param task The delayed server tick task to unregister.
     * @param phase The server event phase.
     */
    public static void unregister(DelayedServerTickTask task, ServerEvents phase) {
        if (phase.delayedTasks.get().contains(task)) {
            phase.delayedTasks.get().remove(task);
        } else {
            logError(task, phase);
        }
    }

    private static <T> void logError(T task, ServerEvents phase) {
        ScheduledTickDispatcher.LOGGER.error("Task not found in {}: {}", phase.name(), task);
    }

    private static void phaseOverflow(ServerEvents phase) {
        ScheduledTickDispatcher.LOGGER.error("Task limit exceeded in {}. Task not registered.", phase.name());
    }
}
