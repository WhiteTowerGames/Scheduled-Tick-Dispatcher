package wtg.std;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.client.Minecraft;
import wtg.std.task.*;

import java.util.HashSet;
import java.util.Set;

public class BackgroundLifecycleOperationsWrapper {

    // Client start tick tasks
    private static final Set<ClientTickTask> START_CLIENT_TICK_TASKS = new HashSet<>();
    private static final Set<DelayedClientTickTask> START_CLIENT_TICK_TASKS_DELAYED = new HashSet<>();
    
    // Client end tick tasks
    private static final Set<ClientTickTask> END_CLIENT_TICK_TASKS = new HashSet<>();
    private static final Set<DelayedClientTickTask> END_CLIENT_TICK_TASKS_DELAYED = new HashSet<>();
    
    // Start Client world tick tasks
    private static final Set<ClientTickTask> START_CLIENT_WORLD_TICK_TASKS = new HashSet<>();
    private static final Set<DelayedClientTickTask> START_CLIENT_WORLD_TICK_TASKS_DELAYED = new HashSet<>();
    
    // End Client world tick tasks
    private static final Set<ClientTickTask> END_CLIENT_WORLD_TICK_TASKS = new HashSet<>();
    private static final Set<DelayedClientTickTask> END_CLIENT_WORLD_TICK_TASKS_DELAYED = new HashSet<>();
    
    // Server start tick tasks
    private static final Set<ServerTickTask> START_SERVER_TICK_TASKS = new HashSet<>();
    private static final Set<DelayedServerTickTask> START_SERVER_TICK_TASKS_DELAYED = new HashSet<>();
    
    // Server end tick tasks
    private static final Set<ServerTickTask> END_SERVER_TICK_TASKS = new HashSet<>();
    private static final Set<DelayedServerTickTask> END_SERVER_TICK_TASKS_DELAYED = new HashSet<>();
    
    // Start Server world tick tasks
    private static final Set<ServerTickTask> START_SERVER_WORLD_TICK_TASKS = new HashSet<>();
    private static final Set<DelayedServerTickTask> START_SERVER_WORLD_TICK_TASKS_DELAYED = new HashSet<>();
    
    // End Server world tick tasks
    private static final Set<ServerTickTask> END_SERVER_WORLD_TICK_TASKS = new HashSet<>();
    private static final Set<DelayedServerTickTask> END_SERVER_WORLD_TICK_TASKS_DELAYED = new HashSet<>();

    public static Set<ClientTickTask> getStartClientTickTasks() {
        return START_CLIENT_TICK_TASKS;
    }
    public static Set<DelayedClientTickTask> getStartClientTickTasksDelayed() {
        return START_CLIENT_TICK_TASKS_DELAYED;
    }
    public static Set<ClientTickTask> getEndClientTickTasks() {
        return END_CLIENT_TICK_TASKS;
    }
    public static Set<DelayedClientTickTask> getEndClientTickTasksDelayed() {
        return END_CLIENT_TICK_TASKS_DELAYED;
    }
    public static Set<ClientTickTask> getStartClientWorldTickTasks() {
        return START_CLIENT_WORLD_TICK_TASKS;
    }
    public static Set<DelayedClientTickTask> getStartClientWorldTickTasksDelayed() {
        return START_CLIENT_WORLD_TICK_TASKS_DELAYED;
    }
    public static Set<ClientTickTask> getEndClientWorldTickTasks() {
        return END_CLIENT_WORLD_TICK_TASKS;
    }
    public static Set<DelayedClientTickTask> getEndClientWorldTickTasksDelayed() {
        return END_CLIENT_WORLD_TICK_TASKS_DELAYED;
    }
    public static Set<ServerTickTask> getStartServerTickTasks() {
        return START_SERVER_TICK_TASKS;
    }
    public static Set<DelayedServerTickTask> getStartServerTickTasksDelayed() {
        return START_SERVER_TICK_TASKS_DELAYED;
    }
    public static Set<ServerTickTask> getEndServerTickTasks() {
        return END_SERVER_TICK_TASKS;
    }
    public static Set<DelayedServerTickTask> getEndServerTickTasksDelayed() {
        return END_SERVER_TICK_TASKS_DELAYED;
    }
    public static Set<ServerTickTask> getStartServerWorldTickTasks() {
        return START_SERVER_WORLD_TICK_TASKS;
    }
    public static Set<DelayedServerTickTask> getStartServerWorldTickTasksDelayed() {
        return START_SERVER_WORLD_TICK_TASKS_DELAYED;
    }
    public static Set<ServerTickTask> getEndServerWorldTickTasks() {
        return END_SERVER_WORLD_TICK_TASKS;
    }
    public static Set<DelayedServerTickTask> getEndServerWorldTickTasksDelayed() {
        return END_SERVER_WORLD_TICK_TASKS_DELAYED;
    }

    public static void init() {
        // Initialize the wrapper

        ClientTickEvents.START_CLIENT_TICK.register(minecraft -> {
            START_CLIENT_TICK_TASKS.forEach(task -> task.accept(minecraft));
            START_CLIENT_TICK_TASKS_DELAYED.forEach(task -> task.accept(minecraft));
            ClientEvents.unregisterCompletedTasks(ClientEvents.START_CLIENT_TICK);
        });

        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            END_CLIENT_TICK_TASKS.forEach(task -> task.accept(minecraft));
            END_CLIENT_TICK_TASKS_DELAYED.forEach(task -> task.accept(minecraft));
            ClientEvents.unregisterCompletedTasks(ClientEvents.END_CLIENT_TICK);
        });

        ClientTickEvents.START_WORLD_TICK.register(clientLevel -> {
            START_CLIENT_WORLD_TICK_TASKS.forEach(task -> task.accept(Minecraft.getInstance()));
            START_CLIENT_WORLD_TICK_TASKS_DELAYED.forEach(task -> task.accept(Minecraft.getInstance()));
            ClientEvents.unregisterCompletedTasks(ClientEvents.START_CLIENT_WORLD_TICK);
        });

        ClientTickEvents.END_WORLD_TICK.register(clientLevel -> {
            END_CLIENT_WORLD_TICK_TASKS.forEach(task -> task.accept(Minecraft.getInstance()));
            END_CLIENT_WORLD_TICK_TASKS_DELAYED.forEach(task -> task.accept(Minecraft.getInstance()));
            ClientEvents.unregisterCompletedTasks(ClientEvents.END_CLIENT_WORLD_TICK);
        });

        ServerTickEvents.START_SERVER_TICK.register(server -> {
            START_SERVER_TICK_TASKS.forEach(task -> task.accept(server));
            START_SERVER_TICK_TASKS_DELAYED.forEach(task -> task.accept(server));
            ServerEvents.unregisterCompletedTasks(ServerEvents.START_SERVER_TICK);
        });

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            END_SERVER_TICK_TASKS.forEach(task -> task.accept(server));
            END_SERVER_TICK_TASKS_DELAYED.forEach(task -> task.accept(server));
            ServerEvents.unregisterCompletedTasks(ServerEvents.END_SERVER_TICK);
        });

        ServerTickEvents.START_WORLD_TICK.register(serverWorld -> {
            START_SERVER_WORLD_TICK_TASKS.forEach(task -> task.accept(serverWorld.getServer()));
            START_SERVER_WORLD_TICK_TASKS_DELAYED.forEach(task -> task.accept(serverWorld.getServer()));
            ServerEvents.unregisterCompletedTasks(ServerEvents.START_SERVER_WORLD_TICK);
        });

        ServerTickEvents.END_WORLD_TICK.register(serverWorld -> {
            END_SERVER_WORLD_TICK_TASKS.forEach(task -> task.accept(serverWorld.getServer()));
            END_SERVER_WORLD_TICK_TASKS_DELAYED.forEach(task -> task.accept(serverWorld.getServer()));
            ServerEvents.unregisterCompletedTasks(ServerEvents.END_SERVER_WORLD_TICK);
        });
        
    }

}
