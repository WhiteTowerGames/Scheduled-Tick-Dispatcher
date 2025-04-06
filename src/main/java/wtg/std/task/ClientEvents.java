package wtg.std.task;

import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.Nullable;
import wtg.std.BackgroundLifecycleOperationsWrapper;
import wtg.std.ScheduledTickDispatcher;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

@SuppressWarnings({"UnusedReturnValue", "unused"})
public enum ClientEvents {
    /**
     * Enum representing different client event phases.
     */
    START_CLIENT_TICK(BackgroundLifecycleOperationsWrapper::getStartClientTickTasks,
            BackgroundLifecycleOperationsWrapper::getStartClientTickTasksDelayed),
    END_CLIENT_TICK(BackgroundLifecycleOperationsWrapper::getEndClientTickTasks,
            BackgroundLifecycleOperationsWrapper::getEndClientTickTasksDelayed),
    START_CLIENT_WORLD_TICK(BackgroundLifecycleOperationsWrapper::getStartClientWorldTickTasks,
            BackgroundLifecycleOperationsWrapper::getStartClientWorldTickTasksDelayed),
    END_CLIENT_WORLD_TICK(BackgroundLifecycleOperationsWrapper::getEndClientWorldTickTasks,
            BackgroundLifecycleOperationsWrapper::getEndClientWorldTickTasksDelayed);

    private final Supplier<Set<ClientTickTask>> tasks;
    private final Supplier<Set<DelayedClientTickTask>> delayedTasks;
    private final int MAX_TASKS_PER_PHASE = 30;

    /**
     * Constructor for ClientEvents.
     *
     * @param tasks Supplier for the set of client tick tasks.
     * @param delayedTasks Supplier for the set of delayed client tick tasks.
     */
    ClientEvents(Supplier<Set<ClientTickTask>> tasks, Supplier<Set<DelayedClientTickTask>> delayedTasks) {
        this.tasks = tasks;
        this.delayedTasks = delayedTasks;
    }

    /**
     * Registers a client tick task.
     *
     * @param task The client tick task to register.
     */
    private void register(ClientTickTask task) {
        tasks.get().add(task);
    }

    /**
     * Registers a delayed client tick task.
     *
     * @param task The delayed client tick task to register.
     */
    private void register(DelayedClientTickTask task) {
        delayedTasks.get().add(task);
    }

    /**
     * Registers a client tick task at a specific phase.
     *
     * @param phase The client event phase.
     * @param action The action to be performed.
     * @param duration The duration of the task.
     * @return The registered client tick task.
     */
    public static @Nullable ClientTickTask registerAt(ClientEvents phase, Consumer<Minecraft> action, int duration) {
        if (phase.tasks.get().size() >= phase.MAX_TASKS_PER_PHASE) {
            phaseOverflow(phase);
            return null;
        }
        ClientTickTask task = new ClientTickTask(duration, action);
        phase.register(task);
        return task;
    }

    /**
     * Registers a delayed client tick task at a specific phase.
     *
     * @param phase The client event phase.
     * @param action The action to be performed.
     * @param duration The duration of the task.
     * @param delay The delay before the task starts.
     * @return The registered delayed client tick task.
     */
    public static DelayedClientTickTask registerAt(ClientEvents phase, Consumer<Minecraft> action, int duration, int delay) {
        if (phase.delayedTasks.get().size() >= phase.MAX_TASKS_PER_PHASE) {
            phaseOverflow(phase);
            return null;
        }
        DelayedClientTickTask task = new DelayedClientTickTask(duration, action, delay);
        phase.register(task);
        return task;
    }

    /**
     * Registers an existing client tick task at a specific phase.
     *
     * @param phase The client event phase.
     * @param task The client tick task to register.
     * @return The registered client tick task.
     */
    public static ClientTickTask registerAt(ClientEvents phase, ClientTickTask task) {
        if (phase.tasks.get().size() >= phase.MAX_TASKS_PER_PHASE) {
            phaseOverflow(phase);
            return null;
        }
        phase.register(task);
        return task;
    }

    /**
     * Registers an existing delayed client tick task at a specific phase.
     *
     * @param phase The client event phase.
     * @param task The delayed client tick task to register.
     * @return The registered delayed client tick task.
     */
    public static DelayedClientTickTask registerAt(ClientEvents phase, DelayedClientTickTask task) {
        if (phase.delayedTasks.get().size() >= phase.MAX_TASKS_PER_PHASE) {
            phaseOverflow(phase);
            return null;
        }
        phase.register(task);
        return task;
    }

    /**
     * Unregisters a client tick task from a specific phase.
     *
     * @param phase The client event phase.
     * @param task The client tick task to unregister.
     */
    public static void unregisterFromPhase(ClientEvents phase, ClientTickTask task) {
        phase.tasks.get().remove(task);
    }

    /**
     * Unregisters a delayed client tick task from a specific phase.
     *
     * @param phase The client event phase.
     * @param task The delayed client tick task to unregister.
     */
    public static void unregisterFromPhase(ClientEvents phase, DelayedClientTickTask task) {
        phase.delayedTasks.get().remove(task);
    }

    /**
     * Unregisters completed tasks from a specific phase.
     *
     * @param phase The client event phase.
     */
    public static void unregisterCompletedTasks(ClientEvents phase) {
        // For client tick tasks
        phase.tasks.get().removeIf(Predicate.not(ClientTickTask::isIncomplete));

        // For delayed client tick tasks
        phase.delayedTasks.get().removeIf(Predicate.not(DelayedClientTickTask::isIncomplete));
    }

    /**
     * Unregisters a client tick task from a specific phase, with error logging if not found.
     *
     * @param task The client tick task to unregister.
     * @param phase The client event phase.
     */
    public static void unregister(ClientTickTask task, ClientEvents phase) {
        if (phase.tasks.get().contains(task)) {
            phase.tasks.get().remove(task);
        } else {
            logError(task, phase);
        }
    }

    /**
     * Unregisters a delayed client tick task from a specific phase, with error logging if not found.
     *
     * @param task The delayed client tick task to unregister.
     * @param phase The client event phase.
     */
    public static void unregister(DelayedClientTickTask task, ClientEvents phase) {
        if (phase.delayedTasks.get().contains(task)) {
            phase.delayedTasks.get().remove(task);
        } else {
            logError(task, phase);
        }
    }

    /**
     * Logs an error if a task is not found in a specific phase.
     *
     * @param task The task that was not found.
     * @param phase The client event phase.
     * @param <T> The type of the task.
     */
    private static <T> void logError(T task, ClientEvents phase) {
        ScheduledTickDispatcher.LOGGER.error("Task not found in {}: {}", phase.name(), task);
    }

    /**
     * Logs an error if the maximum number of tasks is reached for a specific phase.
     *
     * @param phase The client event phase.
     */
    private static void phaseOverflow(ClientEvents phase) {
        ScheduledTickDispatcher.LOGGER.error("Max tasks reached for {}. Task not registered.", phase.name());
    }

}
