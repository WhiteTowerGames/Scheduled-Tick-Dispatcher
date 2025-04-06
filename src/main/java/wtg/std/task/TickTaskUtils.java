package wtg.std.task;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public interface TickTaskUtils {

    /**
     * Appends a ClientTickTask to be executed parallel to the given ClientTickTask.
     *
     * @param clientTickTask the task to append
     * @param phase          the phase during which the task should be executed (see {@link ClientEvents})
     * @return the appended ClientTickTask
     */
    default ClientTickTask appendImmediately(ClientTickTask clientTickTask, ClientEvents phase) {
        ClientEvents.registerAt(phase, clientTickTask);
        return clientTickTask;
    }

    /**
     * Appends a DelayedClientTickTask to be executed right after the end of the given Tick Task.
     *
     * @param clientTickTask the task to append after
     * @param phase          the phase during which the task should be executed (see {@link ClientEvents})
     * @return the appended DelayedClientTickTask
     */
    default DelayedClientTickTask appendAfterEnd(ClientTickTask clientTickTask, ClientEvents phase) {
        DelayedClientTickTask taskToAppend = new DelayedClientTickTask(clientTickTask, ((AbstractTickTask<?>) this).duration.get()
        + (this instanceof AbstractDelayedTickTask<?> ? ((DelayedClientTickTask) this).delay.get() : 0));
        ClientEvents.registerAt(phase, taskToAppend);
        return taskToAppend;
    }

    /**
     * Appends a DelayedClientTickTask to be executed after a delay following the end of the given Tick Task.
     *
     * @param delayedClientTickTask the task to append after
     * @param delayAfterEnd         the delay after the end of the given task
     * @param phase                 the phase during which the task should be executed (see {@link ClientEvents})
     * @return the appended DelayedClientTickTask
     */
    default DelayedClientTickTask appendDelayedAfterEnd(DelayedClientTickTask delayedClientTickTask, int delayAfterEnd, ClientEvents phase) {
        DelayedClientTickTask taskToAppend = new DelayedClientTickTask(delayedClientTickTask.duration.get(),
                delayedClientTickTask.getAction(),
                delayedClientTickTask.delay.get() + delayAfterEnd + ((AbstractTickTask<?>) this).duration.get());
        ClientEvents.registerAt(phase, taskToAppend);
        return taskToAppend;
    }

    /**
     * Appends a ServerTickTask to be executed parallel to the given Tick Task.
     *
     * @param serverTickTask the task to append
     * @param phase          the phase during which the task should be executed (see {@link ServerEvents})
     * @return the appended ServerTickTask
     */
    default ServerTickTask appendImmediately(ServerTickTask serverTickTask, ServerEvents phase) {
        ServerEvents.registerAt(phase, serverTickTask);
        return serverTickTask;
    }

    /**
     * Appends a DelayedServerTickTask to be executed right after the end of the given Tick Task.
     *
     * @param serverTickTask the task to append after
     * @param phase          the phase during which the task should be executed (see {@link ServerEvents})
     * @return the appended DelayedServerTickTask
     */
    default DelayedServerTickTask appendAfterEnd(ServerTickTask serverTickTask, ServerEvents phase) {
        DelayedServerTickTask taskToAppend = new DelayedServerTickTask(serverTickTask, ((AbstractTickTask<?>) this).duration.get() +
                ((this instanceof AbstractDelayedTickTask<?>) ? ((AbstractDelayedTickTask<?>) this).delay.get() : 0));
        ServerEvents.registerAt(phase, taskToAppend);
        return taskToAppend;
    }

    /**
     * Appends a DelayedServerTickTask to be executed after a delay following the end of the given Tick Task.
     *
     * @param delayedServerTickTask the task to append after
     * @param delayAfterEnd         the delay after the end of the given task
     * @param phase                 the phase during which the task should be executed (see {@link ServerEvents})
     * @return the appended DelayedServerTickTask
     */
    default DelayedServerTickTask appendDelayedAfterEnd(DelayedServerTickTask delayedServerTickTask, int delayAfterEnd, ServerEvents phase) {
        DelayedServerTickTask taskToAppend = new DelayedServerTickTask(delayedServerTickTask.duration.get(),
                delayedServerTickTask.getAction(),
                delayedServerTickTask.delay.get() + delayAfterEnd + ((AbstractTickTask<?>) this).duration.get());
        ServerEvents.registerAt(phase, taskToAppend);
        return taskToAppend;
    }

    /**
     * Schedules a one-off ClientTickTask to be executed after a delay.
     * @param clientTickTask the task to schedule
     * @param delay the delay in ticks
     * @param phase the phase during which the task should be executed (see {@link ClientEvents})
     * @return the scheduled DelayedClientTickTask
     */
    default DelayedClientTickTask scheduleOneOff(ClientTickTask clientTickTask, int delay, ClientEvents phase) {
        DelayedClientTickTask taskToAppend = new DelayedClientTickTask(clientTickTask, delay);
        taskToAppend.duration.set(1);
        ClientEvents.registerAt(phase, taskToAppend);
        return taskToAppend;
    }

    /**
     * Schedules a one-off ServerTickTask to be executed after a delay.
     * @param serverTickTask the task to schedule
     * @param delay the delay in ticks
     * @param phase the phase during which the task should be executed (see {@link ServerEvents})
     * @return the scheduled DelayedServerTickTask
     */
    default DelayedServerTickTask scheduleOneOff(ServerTickTask serverTickTask, int delay, ServerEvents phase) {
        DelayedServerTickTask taskToAppend = new DelayedServerTickTask(serverTickTask, delay);
        taskToAppend.duration.set(1);
        ServerEvents.registerAt(phase, taskToAppend);
        return taskToAppend;
    }

}
