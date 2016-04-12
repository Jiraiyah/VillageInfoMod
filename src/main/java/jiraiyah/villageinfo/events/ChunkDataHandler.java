package jiraiyah.villageinfo.events;

import jiraiyah.villageinfo.VillageInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_QUADS;

@SuppressWarnings("unused")
public class ChunkDataHandler
{
	@SubscribeEvent
	public void renderWorldLastEvent(RenderWorldLastEvent event)
	{
		if (!VillageInfo.chunkBorder)
			return;
		Chunk playerChunk = getChunk();
		if (playerChunk == null)
			return;
		VertexBuffer buffer = Tessellator.getInstance().getBuffer();
		EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
		float ticks = event.getPartialTicks();
		double plX = player.lastTickPosX + ((player.posX - player.lastTickPosX) * ticks);
		double plY = player.lastTickPosY + ((player.posY - player.lastTickPosY) * ticks);
		double plZ = player.lastTickPosZ + ((player.posZ - player.lastTickPosZ) * ticks);
		int finalX = playerChunk.xPosition * 16;
		int finalZ = playerChunk.zPosition * 16;
		if (!VillageInfo.solidDraw)
		{
			GlStateManager.pushMatrix();
			{
				GlStateManager.translate(finalX + 8 - plX,
						0 - plY,
						finalZ + 8 - plZ);
				GlStateManager.glLineWidth(1);
				if (VillageInfo.disableDepth)
					GlStateManager.disableDepth();
				GlStateManager.disableLighting();
				GlStateManager.disableTexture2D();
				{
					int spacing = 1;
					for (int y = 0; y <= 256; y += spacing)
					{
						buffer.begin(GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
						buffer.pos(-8f, y, -8f).color(0f, 1f, 0f, 1f).endVertex();
						buffer.pos(-8f, y, 8f).color(0f, 1f, 0f, 1f).endVertex();
						buffer.pos(8f, y, 8f).color(0f, 1f, 0f, 1f).endVertex();
						buffer.pos(8f, y, -8f).color(0f, 1f, 0f, 1f).endVertex();
						buffer.pos(-8, y, -8).color(0f, 1f, 0f, 1f).endVertex();
						Tessellator.getInstance().draw();
					}

					buffer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
					for (int x = -8; x <= 8; x += spacing)
					{
						buffer.pos(x, 0, -8f).color(1f, 0f, 0f, 1f).endVertex();
						buffer.pos(x, 256, -8).color(1f, 0f, 0f, 1f).endVertex();
						buffer.pos(x, 0, 8).color(1f, 0f, 0f, 1f).endVertex();
						buffer.pos(x, 256, 8).color(1f, 0f, 0f, 1f).endVertex();
					}
					for (int z = -8; z <= 8; z += spacing)
					{
						buffer.pos(-8, 0, z).color(0f, 0f, 1f, 1f).endVertex();
						buffer.pos(-8, 256, z).color(0f, 0f, 1f, 1f).endVertex();
						buffer.pos(8, 0, z).color(0f, 0f, 1f, 1f).endVertex();
						buffer.pos(8, 256, z).color(0f, 0f, 1f, 1f).endVertex();
					}
					buffer.pos(0, 0, 0).color(0f, 0f, 1f, 1f).endVertex();
					buffer.pos(0, 256, 0).color(1f, 1f, 0f, 1f).endVertex();
					Tessellator.getInstance().draw();
				}
				if (VillageInfo.disableDepth)
					GlStateManager.enableDepth();
				GlStateManager.enableLighting();
				GlStateManager.enableTexture2D();
			}
			GlStateManager.popMatrix();
		}
		else
		{
			GlStateManager.pushMatrix();
			{
				GlStateManager.translate(finalX + 8 - plX,
						0 - plY,
						finalZ + 8 - plZ);
				GlStateManager.glLineWidth(1);
				GlStateManager.disableLighting();
				GlStateManager.disableTexture2D();
				if (VillageInfo.disableDepth)
					GlStateManager.disableDepth();
				GlStateManager.disableCull();
				GlStateManager.enableBlend();
				GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
				{
					buffer.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
					float opacity = 0.11f;
					buffer.pos(-8f, 0f, -8f).color(0f, 0f, 1f, opacity).endVertex();
					buffer.pos(8, 0f, -8f).color(0f, 0f, 1f, opacity).endVertex();
					buffer.pos(8, 256f, -8f).color(0f, 0f, 1f, opacity).endVertex();
					buffer.pos(-8f, 256f, -8f).color(0f, 0f, 1f, opacity).endVertex();

					buffer.pos(-8f, 0f, 8).color(0f, 0f, 1f, opacity).endVertex();
					buffer.pos(8, 0f, 8).color(0f, 0f, 1f, opacity).endVertex();
					buffer.pos(8, 256, 8).color(0f, 0f, 1f, opacity).endVertex();
					buffer.pos(-8, 256, 8).color(0f, 0f, 1f, opacity).endVertex();

					buffer.pos(-8f, 0f, -8).color(1f, 0f, 0f, opacity).endVertex();
					buffer.pos(-8, 0f, 8).color(1f, 0f, 0f, opacity).endVertex();
					buffer.pos(-8, 256, 8).color(1f, 0f, 0f, opacity).endVertex();
					buffer.pos(-8, 256, -8).color(1f, 0f, 0f, opacity).endVertex();

					buffer.pos(8, 0f, -8).color(1f, 0f, 0f, opacity).endVertex();
					buffer.pos(8, 0f, 8).color(1f, 0f, 0f, opacity).endVertex();
					buffer.pos(8, 256, 8).color(1f, 0f, 0f, opacity).endVertex();
					buffer.pos(8, 256, -8).color(1f, 0f, 0f, opacity).endVertex();

					buffer.pos(-8, 256, -8).color(0f, 1f, 0f, opacity).endVertex();
					buffer.pos(-8, 256, 8).color(0f, 1f, 0f, opacity).endVertex();
					buffer.pos(8, 256, 8).color(0f, 1f, 0f, opacity).endVertex();
					buffer.pos(8, 256, -8).color(0f, 1f, 0f, opacity).endVertex();

					buffer.pos(-8, 0, -8).color(0f, 1f, 0f, opacity).endVertex();
					buffer.pos(-8, 0, 8).color(0f, 1f, 0f, opacity).endVertex();
					buffer.pos(8, 0, 8).color(0f, 1f, 0f, opacity).endVertex();
					buffer.pos(8, 0, -8).color(0f, 1f, 0f, opacity).endVertex();
					Tessellator.getInstance().draw();
					buffer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
					buffer.pos(0, 0, 0).color(0f, 0f, 1f, 1f).endVertex();
					buffer.pos(0, 256, 0).color(1f, 1f, 0f, 1f).endVertex();
					Tessellator.getInstance().draw();
				}
				GlStateManager.glLineWidth(1);
				GlStateManager.disableBlend();
				GlStateManager.enableCull();
				if(VillageInfo.disableDepth)
					GlStateManager.enableDepth();
				GlStateManager.enableLighting();
				GlStateManager.enableTexture2D();
			}
			GlStateManager.popMatrix();
		}
	}

	private Chunk getChunk()
	{
		IChunkProvider chunkProvider = FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld().getChunkProvider();
		BlockPos tempPos = Minecraft.getMinecraft().thePlayer.getPosition();
		return FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld().getChunkFromBlockCoords(tempPos);
	}
}
