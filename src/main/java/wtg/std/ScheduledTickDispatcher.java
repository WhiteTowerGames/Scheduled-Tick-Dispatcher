package wtg.std;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduledTickDispatcher implements ModInitializer {
	public static final String MOD_ID = "std";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		GreatJafar.insertQuarter();
		BackgroundLifecycleOperationsWrapper.init();
	}
}