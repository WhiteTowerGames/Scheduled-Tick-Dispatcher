package wtg.std.task;

import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ClientTickTask extends AbstractTickTask<Minecraft> {

    public ClientTickTask(int duration, Consumer<Minecraft> action) {
        super(duration, action);
    }

    public ClientTickTask(@NotNull DelayedClientTickTask delayedClientTickTask){
        this(delayedClientTickTask.duration.get(), delayedClientTickTask.getAction());
    }

    @Override
    public void accept(Minecraft client) {
        if (isIncomplete()) {
            getAction().accept(client);
            if (duration.decrementAndGet() > 0) {
                return;
            }
            complete();
        }
    }

}
