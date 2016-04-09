package jiraiyah.villageinfo.inits;

import jiraiyah.villageinfo.events.VillageDataCollector;
import net.minecraftforge.common.MinecraftForge;

public class CommonEventRegister
{
	public static void register()
	{
		MinecraftForge.EVENT_BUS.register(new VillageDataCollector());
		//Log.info("=========================================================> Registered Common Events");
	}
}
