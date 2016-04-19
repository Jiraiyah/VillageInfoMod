/**
 * Copyright 2016 VillageInfoMod (Jiraiyah)
 *
 * project link : http://minecraft.curseforge.com/projects/village-info
 *
 * Licensed under The MIT License (MIT);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jiraiyah.villageinfo.events;

import jiraiyah.villageinfo.VillageInfo;
import jiraiyah.villageinfo.inits.KeyBindings;
import jiraiyah.villageinfo.network.SpawnPlayerMessage;
import jiraiyah.villageinfo.network.VillagePlayerMessage;
import net.minecraft.client.Minecraft;
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
		if (KeyBindings.VILLAGE_PER_COLOR.isPressed())
			VillageInfo.perVillageColor = !VillageInfo.perVillageColor;
		if (KeyBindings.CHUNK_BORDER.isPressed())
			VillageInfo.chunkBorder = !VillageInfo.chunkBorder;
		if(KeyBindings.SPAWN_CHUNK.isPressed())
		{
			WorldSpawnHandler.showSpawnChunks = !WorldSpawnHandler.showSpawnChunks;
			SpawnPlayerMessage.sendMessage(Minecraft.getMinecraft().thePlayer.getUniqueID());
		}
	}
}
