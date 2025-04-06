package wtg.std;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.client.Minecraft;
import wtg.std.task.*;

import java.util.ArrayList;
import java.util.List;

public class BackgroundLifecycleOperationsWrapper {

    // Client start tick tasks
    private static final List<ClientTickTask> START_CLIENT_TICK_TASKS = new ArrayList<>();
    private static final List<DelayedClientTickTask> START_CLIENT_TICK_TASKS_DELAYED = new ArrayList<>();
    
    // Client end tick tasks
    private static final List<ClientTickTask> END_CLIENT_TICK_TASKS = new ArrayList<>();
    private static final List<DelayedClientTickTask> END_CLIENT_TICK_TASKS_DELAYED = new ArrayList<>();
    
    // Start Client world tick tasks
    private static final List<ClientTickTask> START_CLIENT_WORLD_TICK_TASKS = new ArrayList<>();
    private static final List<DelayedClientTickTask> START_CLIENT_WORLD_TICK_TASKS_DELAYED = new ArrayList<>();
    
    // End Client world tick tasks
    private static final List<ClientTickTask> END_CLIENT_WORLD_TICK_TASKS = new ArrayList<>();
    private static final List<DelayedClientTickTask> END_CLIENT_WORLD_TICK_TASKS_DELAYED = new ArrayList<>();
    
    // Server start tick tasks
    private static final List<ServerTickTask> START_SERVER_TICK_TASKS = new ArrayList<>();
    private static final List<DelayedServerTickTask> START_SERVER_TICK_TASKS_DELAYED = new ArrayList<>();
    
    // Server end tick tasks
    private static final List<ServerTickTask> END_SERVER_TICK_TASKS = new ArrayList<>();
    private static final List<DelayedServerTickTask> END_SERVER_TICK_TASKS_DELAYED = new ArrayList<>();
    
    // Start Server world tick tasks
    private static final List<ServerTickTask> START_SERVER_WORLD_TICK_TASKS = new ArrayList<>();
    private static final List<DelayedServerTickTask> START_SERVER_WORLD_TICK_TASKS_DELAYED = new ArrayList<>();
    
    // End Server world tick tasks
    private static final List<ServerTickTask> END_SERVER_WORLD_TICK_TASKS = new ArrayList<>();
    private static final List<DelayedServerTickTask> END_SERVER_WORLD_TICK_TASKS_DELAYED = new ArrayList<>();

    public static List<ClientTickTask> getStartClientTickTasks() {
        return START_CLIENT_TICK_TASKS;
    }
    public static List<DelayedClientTickTask> getStartClientTickTasksDelayed() {
        return START_CLIENT_TICK_TASKS_DELAYED;
    }
    public static List<ClientTickTask> getEndClientTickTasks() {
        return END_CLIENT_TICK_TASKS;
    }
    public static List<DelayedClientTickTask> getEndClientTickTasksDelayed() {
        return END_CLIENT_TICK_TASKS_DELAYED;
    }
    public static List<ClientTickTask> getStartClientWorldTickTasks() {
        return START_CLIENT_WORLD_TICK_TASKS;
    }
    public static List<DelayedClientTickTask> getStartClientWorldTickTasksDelayed() {
        return START_CLIENT_WORLD_TICK_TASKS_DELAYED;
    }
    public static List<ClientTickTask> getEndClientWorldTickTasks() {
        return END_CLIENT_WORLD_TICK_TASKS;
    }
    public static List<DelayedClientTickTask> getEndClientWorldTickTasksDelayed() {
        return END_CLIENT_WORLD_TICK_TASKS_DELAYED;
    }
    public static List<ServerTickTask> getStartServerTickTasks() {
        return START_SERVER_TICK_TASKS;
    }
    public static List<DelayedServerTickTask> getStartServerTickTasksDelayed() {
        return START_SERVER_TICK_TASKS_DELAYED;
    }
    public static List<ServerTickTask> getEndServerTickTasks() {
        return END_SERVER_TICK_TASKS;
    }
    public static List<DelayedServerTickTask> getEndServerTickTasksDelayed() {
        return END_SERVER_TICK_TASKS_DELAYED;
    }
    public static List<ServerTickTask> getStartServerWorldTickTasks() {
        return START_SERVER_WORLD_TICK_TASKS;
    }
    public static List<DelayedServerTickTask> getStartServerWorldTickTasksDelayed() {
        return START_SERVER_WORLD_TICK_TASKS_DELAYED;
    }
    public static List<ServerTickTask> getEndServerWorldTickTasks() {
        return END_SERVER_WORLD_TICK_TASKS;
    }
    public static List<DelayedServerTickTask> getEndServerWorldTickTasksDelayed() {
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
