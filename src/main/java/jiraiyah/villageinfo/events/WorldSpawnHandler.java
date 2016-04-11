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

import jiraiyah.villageinfo.infrastructure.Config;
import jiraiyah.villageinfo.inits.KeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;

@SuppressWarnings("unused")
public class WorldSpawnHandler
{
	private boolean showSpawnChunks;
	private static BlockPos spawnPoint = null;
	private boolean chatMessageShown;
	private Set<Chunk> spawnChunks = new HashSet<>();

	@SubscribeEvent
	public void renderWorldLastEvent(RenderWorldLastEvent event)
	{
		if (spawnPoint == null || !showSpawnChunks)
			return;
		if (spawnChunks == null || spawnChunks.size() == 0)
			spawnChunks = getSpawnChunks();
		if (spawnChunks == null || spawnChunks.size() == 0)
			return;
		VertexBuffer buffer = Tessellator.getInstance().getBuffer();
		EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
		float ticks = event.getPartialTicks();
		double plX = player.lastTickPosX + ((player.posX - player.lastTickPosX) * ticks);
		double plY = player.lastTickPosY + ((player.posY - player.lastTickPosY) * ticks);
		double plZ = player.lastTickPosZ + ((player.posZ - player.lastTickPosZ) * ticks);
		Vec3d playerPos = new Vec3d(plX, plY, plZ);
		double distance = Math.sqrt(playerPos.squareDistanceTo(spawnPoint.getX(), spawnPoint.getY(), spawnPoint.getZ()));
		int radius = 64;
		if (distance > Config.spawnChunkShowDistance)
			return;
		int minX = Integer.MIN_VALUE, minZ = Integer.MIN_VALUE, maxX = Integer.MAX_VALUE, maxZ = Integer.MAX_VALUE;
		for (Chunk chunk : spawnChunks)
		{
			int finalX = (Math.abs(chunk.xPosition * 16) + 16) * (int) Math.signum(chunk.xPosition);
			int finalZ = (Math.abs(chunk.zPosition * 16) + 16) * (int) Math.signum(chunk.zPosition);
			if (minX < finalX)
				minX = finalX;
			if (maxX > finalX)
				maxX = finalX;
			if (minZ < finalZ)
				minZ = finalZ;
			if (maxZ > finalZ)
				maxZ = finalZ;
		}
		GlStateManager.pushMatrix();
		{
			GlStateManager.translate(minX - plX,
					0 - plY,
					minZ - plZ);
			GlStateManager.glLineWidth(5);
			GlStateManager.disableLighting();
			GlStateManager.disableTexture2D();
			{
				GlStateManager.glLineWidth(1);
				int spacing = 16;
				for (int y = 0; y <= 256; y += spacing)
				{
					buffer.begin(GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
					buffer.pos(0f, y, 0f).color(0f, 1f, 0f, 1f).endVertex();
					buffer.pos(0f, y, (maxZ - minZ)).color(0f, 1f, 0f, 1f).endVertex();
					buffer.pos((maxX - minX), y, (maxZ - minZ)).color(0f, 1f, 0f, 1f).endVertex();
					buffer.pos((maxX - minX), y, 0f).color(0f, 1f, 0f, 1f).endVertex();
					buffer.pos(0, y, 0).color(0f, 1f, 0f, 1f).endVertex();
					Tessellator.getInstance().draw();
				}

				buffer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
				for (int x = (maxX - minX); x <= 0; x += spacing)
				{
					buffer.pos(x, 0, 0f).color(1f, 0f, 0f, 1f).endVertex();
					buffer.pos(x, 256, 0).color(1f, 0f, 0f, 1f).endVertex();
					buffer.pos(x, 0, (maxZ - minZ)).color(1f, 0f, 0f, 1f).endVertex();
					buffer.pos(x, 256, (maxZ - minZ)).color(1f, 0f, 0f, 1f).endVertex();
				}
				for (int z = (maxX - minX); z <= 0; z += spacing)
				{
					buffer.pos(0, 0, z).color(0f, 0f, 1f, 1f).endVertex();
					buffer.pos(0, 256, z).color(0f, 0f, 1f, 1f).endVertex();
					buffer.pos((maxX - minX), 0, z).color(0f, 0f, 1f, 1f).endVertex();
					buffer.pos((maxX - minX), 256, z).color(0f, 0f, 1f, 1f).endVertex();
				}
				Tessellator.getInstance().draw();
			}
			GlStateManager.enableLighting();
			GlStateManager.enableTexture2D();
		}
		GlStateManager.popMatrix();
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event)
	{
		if(KeyBindings.SPAWNCHUNK.isPressed())
		{
			showSpawnChunks = !showSpawnChunks;
			if (spawnPoint == null)
			{
				spawnPoint = FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld().getSpawnPoint();
				if (!chatMessageShown)
				{
					EntityPlayerSP playerSP = Minecraft.getMinecraft().thePlayer;
					if (playerSP != null)
					{
						ITextComponent textComponent = new TextComponentString("Spawn Point : " + TextFormatting.DARK_RED + spawnPoint.getX() + ", " + spawnPoint.getY() + ", " + spawnPoint.getZ());
						playerSP.addChatMessage(textComponent);
						chatMessageShown = true;
					}
				}
			}
		}
	}

	private Set<Chunk> getSpawnChunks()
	{
		Set<Chunk> temp = new HashSet<>();
		IChunkProvider chunkProvider = FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld().getChunkProvider();
		for (int x = spawnPoint.getX() - 256; x < spawnPoint.getX() + 256; x++)
		{
			for (int z = spawnPoint.getZ() - 256; z < spawnPoint.getZ() + 256; z++)
			{
				BlockPos tempPos = new BlockPos(x,0,z);
				Chunk chunk = FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld().getChunkFromBlockCoords(tempPos);
				if (FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld().isSpawnChunk(chunk.xPosition, chunk.zPosition))
				{
					temp.add(chunk);
				}
			}
		}
		return temp;
	}
}
