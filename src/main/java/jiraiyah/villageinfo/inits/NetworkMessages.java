package jiraiyah.villageinfo.inits;

import jiraiyah.villageinfo.network.VillageIndicatorMessage;
import jiraiyah.villageinfo.network.VillageInfoPlayerMessage;
import jiraiyah.villageinfo.references.Reference;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkMessages
{
	public static SimpleNetworkWrapper network;

	public static void register()
	{
		network = new SimpleNetworkWrapper( Reference.MOD_ID );
		network.registerMessage( VillageIndicatorMessage.class, VillageIndicatorMessage.Packet.class, nextId(), Side.CLIENT );
		network.registerMessage( VillageInfoPlayerMessage.class, VillageInfoPlayerMessage.Packet.class, nextId(), Side.SERVER );
		//Log.info("=========================================================> Registered Network Messages");
	}

	private static int ID = 0;

	public static int nextId ()
	{
		return ID++;
	}
}
