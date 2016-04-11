package jiraiyah.villageinfo.events;

import jiraiyah.villageinfo.VillageInfo;
import jiraiyah.villageinfo.inits.KeyBindings;
import jiraiyah.villageinfo.network.VillagePlayerMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("unused")
public class KeyBindingHandler
{
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event)
	{

		if(KeyBindings.VILLAGE_DATA.isPressed())
		{
			VillageInfo.showVillages = !VillageInfo.showVillages;
			VillagePlayerMessage.sendMessage(Minecraft.getMinecraft().thePlayer.getUniqueID(), VillageInfo.showVillages);
		}
		if (KeyBindings.VILLAGE_DATA_BORDER.isPressed())
			VillageInfo.villageBorder = !VillageInfo.villageBorder;
		if (KeyBindings.VILLAGE_DATA_DOORS.isPressed())
			VillageInfo.villageDoors = !VillageInfo.villageDoors;
		if (KeyBindings.VILLAGE_DATA_SPHERE.isPressed())
			VillageInfo.villageSphere = !VillageInfo.villageSphere;
		if (KeyBindings.VILLAGE_DATA_GOLEM.isPressed())
			VillageInfo.villageGolem = !VillageInfo.villageGolem;
		if (KeyBindings.VILLAGE_DATA_INFO.isPressed())
			VillageInfo.villageInfoText = !VillageInfo.villageInfoText;
		if (KeyBindings.VILLAGE_DATA_CENTER.isPressed())
			VillageInfo.villageCenter = !VillageInfo.villageCenter;
		if (KeyBindings.SOLID_DRAW.isPressed())
			VillageInfo.solidDraw = !VillageInfo.solidDraw;
		if (KeyBindings.Disable_DEPTH.isPressed())
			VillageInfo.disableDepth = !VillageInfo.disableDepth;
		if(KeyBindings.SPAWN_CHUNK.isPressed())
		{
			WorldSpawnHandler.showSpawnChunks = !WorldSpawnHandler.showSpawnChunks;
			if (WorldSpawnHandler.spawnPoint == null)
			{
				WorldSpawnHandler.spawnPoint = FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld().getSpawnPoint();
				if (!WorldSpawnHandler.chatMessageShown)
				{
					EntityPlayerSP playerSP = Minecraft.getMinecraft().thePlayer;
					if (playerSP != null)
					{
						ITextComponent textComponent = new TextComponentString("Spawn Point : " + TextFormatting.DARK_RED + WorldSpawnHandler.spawnPoint.getX() + ", " + WorldSpawnHandler.spawnPoint.getY() + ", " + WorldSpawnHandler.spawnPoint.getZ());
						playerSP.addChatMessage(textComponent);
						WorldSpawnHandler.chatMessageShown = true;
					}
				}
			}
		}
	}
}
