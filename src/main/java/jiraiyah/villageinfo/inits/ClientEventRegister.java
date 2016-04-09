package jiraiyah.villageinfo.inits;

import jiraiyah.villageinfo.events.VillageDataHandler;
import net.minecraftforge.common.MinecraftForge;

public class ClientEventRegister
{
	public static void register()
	{
		MinecraftForge.EVENT_BUS.register(new VillageDataHandler());
		//Log.info("=========================================================> Registered Client Events");
	}
}
