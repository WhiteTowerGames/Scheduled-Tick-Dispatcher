package wtg.std.task;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal"})
/// # AbstractTickTask
/// A base class for tasks that execute an action on a specified object after a certain duration.
/// This class implements the Consumer interface, allowing it to be used as a callback.
/// The task automatically marks itself as complete and removes itself completely once it does so.
/// The task can be manually marked as complete by calling the complete() method.
public abstract class AbstractTickTask<T> implements TickTaskUtils{

    /**
     * Indicates whether the task is complete.
     */
    private boolean isComplete = false;

    /**
     * The duration of the task in ticks.
     */
    public AtomicInteger duration;

    /**
     * The initial duration of the task in ticks.
     */
    public int initialDuration;
    /**
     * The action to be performed by the task.
     */
    private final Consumer<T> action;

    /**
     * Constructs an AbstractTickTask with the specified duration and action.
     *
     * @param duration the duration of the task in ticks
     * @param action   the action to be performed by the task
     */
    public AbstractTickTask(int duration, Consumer<T> action) {
        if (duration <= 0) {
            throw new IllegalArgumentException("Duration must be greater than 0!");
        }
        this.duration = new AtomicInteger(duration);
        this.initialDuration = duration;
        this.action = action;
    }

    /**
     * Marks the task as complete.
     */
    public final void complete() {
        this.isComplete = true;
    }

    /**
     * Checks if the task is incomplete.
     *
     * @return true if the task is incomplete, false otherwise
     */
    public final boolean isIncomplete() {
        return !this.isComplete;
    }

    /**
     * Gets the action to be performed by the task.
     *
     * @return the action to be performed by the task
     */
    public Consumer<T> getAction() {
        return this.action;
    }

    public void accept(T t) {
        if (isIncomplete()) {
            getAction().accept(t);
            if (duration.decrementAndGet() > 0) {
                return;
            }
            complete();
        }
    }
}
