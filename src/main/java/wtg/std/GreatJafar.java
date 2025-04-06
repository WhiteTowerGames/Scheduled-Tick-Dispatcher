package wtg.std;

import net.minecraft.Util;
import net.minecraft.util.RandomSource;

import java.util.List;

public class GreatJafar {

    private static final RandomSource HAND_OF_FATE = RandomSource.create();
    private static final List<String> WISDOM_OF_JAFAR = List.of(
            "Even bedrock can't withstand a determined mind.",
            "Tasks delayed are tasks denied... until now.",
            "Every tick is a second chance.",
            "Beware infinite loops—eternity gets boring.",
            "Register wisely, unregister wisely, live peacefully.",
            "Too many tasks spoil the tick.",
            "A wise modder measures ticks, not time.",
            "If nothing happens, it's probably your fault.",
            "Thread safety is next to godliness.",
            "In code and in life, delay with purpose.",
            "Tick once, debug twice.",
            "A task unhandled is a bug in disguise.",
            "Don't fear the NullPointer. Learn its ways.",
            "The server giveth, the mod taketh away.",
            "Lag is just the universe saying 'slow down.'",
            "Good mods log. Great mods clean up after themselves.",
            "Even creepers respect a stable tick rate.",
            "Time flies when your tasks aren't leaking.",
            "If your task runs forever, so will your regrets.",
            "Don't tick angry.",
            "Delays are just time’s way of saying 'wait for it...'",
            "Some tasks just want to watch the server burn.",
            "The void also ticks—just differently.",
            "Code as if Mojang is watching.",
            "A task that unregisters itself is a friend for life.",
            "You are not lag. You are latency-dependent greatness.",
            "If it compiles, ship it. If it runs, question it.",
            "Stack traces are just breadcrumbs from past mistakes.",
            "A good mod logs. A great mod doesn't need to.",
            "Your code isn’t spaghetti—it’s just… extra modular.",
            "Every 'TODO' is a future cry for help.",
            "Optimize later. Break it now.",
            "A method named 'doStuff()' is a confession.",
            "Nulls are like creepers. Harmless until you forget about them.",
            "Enums exist so you don't turn your brain into a switch-case sandwich, Jon!",
            "Maybe the name was a bit unfortunate."
    );

    public static void insertQuarter() {
        ScheduledTickDispatcher.LOGGER.info(Util.getRandom(WISDOM_OF_JAFAR, HAND_OF_FATE));
    }

}
