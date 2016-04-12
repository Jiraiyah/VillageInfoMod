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
import jiraiyah.villageinfo.infrastructure.VillageData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.vecmath.Vector4f;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

@SuppressWarnings({"unused", "SuspiciousNameCombination"})
public class VillageDataHandler
{
	private static List<VillageData> villageDataList = new ArrayList<>();
	private static final float PI = (float) Math.PI;
	private static final float DEG2RAD = PI/180;

	private static final Vector4f[] COLORS =
			{
				new Vector4f(1f, 1f, 1f, 1f),
				new Vector4f(1f, 0f, 0f, 1f),
				new Vector4f(0f, 1f, 0f, 1f),
				new Vector4f(0f, 0f, 1f, 1f),
				new Vector4f(1f, 1f, 0f, 1f),
				new Vector4f(0f, 1f, 1f, 1f),
				new Vector4f(1f, 0f, 1f, 1f)
			};

	@SubscribeEvent
	public void renderWorldLastEvent(RenderWorldLastEvent event)
	{
		if (!VillageInfo.showVillages || villageDataList == null || villageDataList.size() == 0)
			return;
		VertexBuffer buffer = Tessellator.getInstance().getBuffer();
		EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
		float ticks = event.getPartialTicks();
		double plX = player.lastTickPosX + ((player.posX - player.lastTickPosX) * ticks);
		double plY = player.lastTickPosY + ((player.posY - player.lastTickPosY) * ticks);
		double plZ = player.lastTickPosZ + ((player.posZ - player.lastTickPosZ) * ticks);
		Vec3d playerPos = new Vec3d(plX, plY, plZ);
		RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
		FontRenderer fontrenderer = renderManager.getFontRenderer();
		int cnt = 0;
		for (VillageData data : villageDataList)
		{
			double distance = Math.sqrt(playerPos.squareDistanceTo(data.center.getX(), data.center.getY(), data.center.getZ()));
			double scaleFactor = -0.015f * distance / 4.209f;
			boolean canSpawn = data.doorPositions.size() > 20;
			GlStateManager.pushMatrix();
			{
				GlStateManager.translate(0.5f + data.center.getX() - plX,
						0.5f + data.center.getY() - plY,
						0.5f + data.center.getZ() - plZ);
				GlStateManager.glLineWidth(1);
				GlStateManager.disableLighting();
				GlStateManager.disableTexture2D();
				if(VillageInfo.disableDepth)
					GlStateManager.disableDepth();
				GlStateManager.disableCull();
				GlStateManager.enableBlend();
				GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
				{
					if (VillageInfo.villageSphere)
						drawBorderSpehere(data.radius, 16, 64, buffer, VillageInfo.perVillageColor ? COLORS[cnt % COLORS.length] : new Vector4f(1f, 0f, 1f, 1f));
					if (VillageInfo.villageDoors)
						drawDoors(data.center, data.doorPositions, buffer, VillageInfo.perVillageColor ? COLORS[cnt % COLORS.length] : new Vector4f(1f, 1f, 1f, 1f));
					if (VillageInfo.villageBorder)
						drawBorderSquare(data.radius, buffer, VillageInfo.perVillageColor ? COLORS[cnt % COLORS.length] : new Vector4f(1f, 1f, 0f, 0.5f));
					if (VillageInfo.villageGolem)
						drawGolemSpawn(buffer, VillageInfo.perVillageColor ? COLORS[cnt % COLORS.length] : (canSpawn ? new Vector4f(0f, 1f, 0f, 1f) : new Vector4f(0.8f, 0f, 0f, 1f)));
					if (VillageInfo.villageCenter)
						drawCenter(buffer, VillageInfo.perVillageColor ? COLORS[cnt % COLORS.length] : new Vector4f(1f, 0f, 0f, 1f));
					if (VillageInfo.villageInfoText)
					{
						GlStateManager.translate(0f, 1.5f, 0f);
						GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
						GlStateManager.rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
						GlStateManager.rotate((float) (renderManager.options.thirdPersonView == 2 ? -1 : 1) * renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
						GlStateManager.scale(scaleFactor, scaleFactor, -scaleFactor);
						GlStateManager.enableBlend();
						GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
						GlStateManager.enableTexture2D();
						drawTextInfo("Villagers : " + data.villagerCount, 1, scaleFactor, fontrenderer);
						drawTextInfo("Doors : " + data.doorPositions.size(), 2, scaleFactor, fontrenderer);
						drawTextInfo("Max Golem : " + (canSpawn ? (TextFormatting.GREEN + Integer.toString(data.villagerCount / 10)) : TextFormatting.DARK_RED + "" + TextFormatting.BOLD + "0"), 3, scaleFactor, fontrenderer);
						drawTextInfo("Reputation : " + data.reputation, 4, scaleFactor, fontrenderer);
						GlStateManager.disableBlend();
					}
				}
				GlStateManager.glLineWidth(1);
				GlStateManager.disableBlend();
				GlStateManager.enableCull();
				if(VillageInfo.disableDepth)
					GlStateManager.enableDepth();
				GlStateManager.enableTexture2D();
				GlStateManager.enableLighting();
			}
			GlStateManager.popMatrix();
			cnt++;
		}
	}

	private void drawTextInfo(String text, int lineNumber, double scaleFactor, FontRenderer fontrenderer)
	{
		fontrenderer.drawString(text, -fontrenderer.getStringWidth(text) / 2, 10 * lineNumber, -1);
	}

	private void drawCenter(VertexBuffer buffer, Vector4f color)
	{
		if (!VillageInfo.solidDraw)
		{
			buffer.begin(GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
			buffer.pos(-0.5, -0.5, -0.5).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(-0.5, -0.5, 0.5).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(0.5, -0.5, 0.5).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(0.5, -0.5, -0.5).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(-0.5, -0.5, -0.5).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(-0.5, 0.5, -0.5).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(-0.5, 0.5, 0.5).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(0.5, 0.5, 0.5).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(0.5, 0.5, -0.5).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(-0.5, 0.5, -0.5).color(color.x, color.y, color.z, color.w).endVertex();
			Tessellator.getInstance().draw();
			buffer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
			buffer.pos(-0.5, -0.5, 0.5).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(-0.5, 0.5, 0.5).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(0.5, -0.5, 0.5).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(0.5, 0.5, 0.5).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(0.5, -0.5, -0.5).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(0.5, 0.5, -0.5).color(color.x, color.y, color.z, color.w).endVertex();
			Tessellator.getInstance().draw();
		}
		else
		{
			GlStateManager.disableCull();
			buffer.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
			float opacity = 1f;
			buffer.pos(-0.5f, 0.5f, -0.5f).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(0.5f, 0.5f, -0.5f).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(0.5f, -0.5f, -0.5f).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(-0.5f, -0.5f, -0.5f).color(color.x, color.y, color.z, color.w).endVertex();

			buffer.pos(-0.5f, 0.5, 0.5f).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(0.5f, 0.5, 0.5f).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(0.5f, -0.5, 0.5f).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(-0.5f, -0.5, 0.5f).color(color.x, color.y, color.z, color.w).endVertex();

			buffer.pos(-0.5f, 0.5, -0.5f).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(-0.5f, 0.5, 0.5f).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(-0.5f, -0.5, 0.5f).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(-0.5f, -0.5, -0.5f).color(color.x, color.y, color.z, color.w).endVertex();

			buffer.pos(0.5f, 0.5, -0.5f).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(0.5f, 0.5, 0.5f).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(0.5f, -0.5, 0.5f).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(0.5f, -0.5, -0.5f).color(color.x, color.y, color.z, color.w).endVertex();

			buffer.pos(0.5f, 0.5, -0.5f).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(0.5f, 0.5, 0.5f).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(-0.5f, 0.5, 0.5f).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(-0.5f, 0.5, -0.5f).color(color.x, color.y, color.z, color.w).endVertex();

			buffer.pos(0.5f, -0.5, -0.5f).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(0.5f, -0.5, 0.5f).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(-0.5f, -0.5, 0.5f).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(-0.5f, -0.5, -0.5f).color(color.x, color.y, color.z, color.w).endVertex();

			Tessellator.getInstance().draw();
			GlStateManager.enableCull();
		}
	}

	private void drawGolemSpawn(VertexBuffer buffer, Vector4f color)
	{
		if (!VillageInfo.solidDraw)
		{
			buffer.begin(GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
			buffer.pos(-8.5f, -3.5, -8.5f).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(-8.5f, -3.5, 7.5f).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(7.5f, -3.5, 7.5f).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(7.5f, -3.5, -8.5f).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(-8.5f, -3.5, -8.5f).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(-8.5f, 2.5, -8.5f).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(-8.5f, 2.5, 7.5f).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(7.5f, 2.5, 7.5f).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(7.5f, 2.5, -8.5f).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(-8.5f, 2.5, -8.5f).color(color.x, color.y, color.z, color.w).endVertex();
			Tessellator.getInstance().draw();
			buffer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
			buffer.pos(-8.5f, -3.5, 7.5f).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(-8.5f, 2.5, 7.5f).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(7.5f, -3.5, 7.5f).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(7.5f, 2.5, 7.5f).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(7.5f, -3.5, -8.5f).color(color.x, color.y, color.z, color.w).endVertex();
			buffer.pos(7.5f, 2.5, -8.5f).color(color.x, color.y, color.z, color.w).endVertex();
			Tessellator.getInstance().draw();
		}
		else
		{
			GlStateManager.disableCull();
			buffer.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
			float opacity = 0.11f;
			buffer.pos(-8.5f, 2.5f, -8.5f).color(color.x, color.y, color.z, opacity).endVertex();
			buffer.pos(7.5f, 2.5f, -8.5f).color(color.x, color.y, color.z, opacity).endVertex();
			buffer.pos(7.5f, -3.5, -8.5f).color(color.x, color.y, color.z, opacity).endVertex();
			buffer.pos(-8.5f, -3.5, -8.5f).color(color.x, color.y, color.z, opacity).endVertex();

			buffer.pos(-8.5f, 2.5, 7.5f).color(color.x, color.y, color.z, opacity).endVertex();
			buffer.pos(7.5f, 2.5, 7.5f).color(color.x, color.y, color.z, opacity).endVertex();
			buffer.pos(7.5f, -3.5, 7.5f).color(color.x, color.y, color.z, opacity).endVertex();
			buffer.pos(-8.5f, -3.5, 7.5f).color(color.x, color.y, color.z, opacity).endVertex();

			buffer.pos(-8.5f, 2.5, -8.5f).color(color.x, color.y, color.z, opacity).endVertex();
			buffer.pos(-8.5f, 2.5, 7.5f).color(color.x, color.y, color.z, opacity).endVertex();
			buffer.pos(-8.5f, -3.5, 7.5f).color(color.x, color.y, color.z, opacity).endVertex();
			buffer.pos(-8.5f, -3.5, -8.5f).color(color.x, color.y, color.z, opacity).endVertex();

			buffer.pos(7.5f, 2.5, -8.5f).color(color.x, color.y, color.z, opacity).endVertex();
			buffer.pos(7.5f, 2.5, 7.5f).color(color.x, color.y, color.z, opacity).endVertex();
			buffer.pos(7.5f, -3.5, 7.5f).color(color.x, color.y, color.z, opacity).endVertex();
			buffer.pos(7.5f, -3.5, -8.5f).color(color.x, color.y, color.z, opacity).endVertex();

			buffer.pos(7.5f, 2.5, -8.5f).color(color.x, color.y, color.z, opacity).endVertex();
			buffer.pos(7.5f, 2.5, 7.5f).color(color.x, color.y, color.z, opacity).endVertex();
			buffer.pos(-8.5f, 2.5, 7.5f).color(color.x, color.y, color.z, opacity).endVertex();
			buffer.pos(-8.5f, 2.5, -8.5f).color(color.x, color.y, color.z, opacity).endVertex();

			buffer.pos(7.5f, -3.5, -8.5f).color(color.x, color.y, color.z, opacity).endVertex();
			buffer.pos(7.5f, -3.5, 7.5f).color(color.x, color.y, color.z, opacity).endVertex();
			buffer.pos(-8.5f, -3.5, 7.5f).color(color.x, color.y, color.z, opacity).endVertex();
			buffer.pos(-8.5f, -3.5, -8.5f).color(color.x, color.y, color.z, opacity).endVertex();

			Tessellator.getInstance().draw();
			GlStateManager.enableCull();
		}
	}

	private void drawBorderSquare(int radius, VertexBuffer buffer, Vector4f color)
	{
		GlStateManager.glLineWidth(10);
		buffer.begin(GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
		buffer.pos(-radius - 0.5f, 0, -radius - 0.5f).color(color.x, color.y, color.z, color.w).endVertex();
		buffer.pos(-radius - 0.5f, 0, radius + 0.5f).color(color.x, color.y, color.z, color.w).endVertex();
		buffer.pos(radius + 0.5f, 0, radius + 0.5f).color(color.x, color.y, color.z, color.w).endVertex();
		buffer.pos(radius + 0.5f, 0, -radius - 0.5f).color(color.x, color.y, color.z, color.w).endVertex();
		buffer.pos(-radius - 0.5f, 0, -radius - 0.5f).color(color.x, color.y, color.z, color.w).endVertex();
		Tessellator.getInstance().draw();
		GlStateManager.glLineWidth(1);
	}

	private void drawDoors(BlockPos center, List<BlockPos> doorPositions, VertexBuffer buffer, Vector4f color)
	{
		buffer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
		for (BlockPos doorPos : doorPositions)
		{
			buffer.pos(0, 0, 0)
					.color(color.x, color.y, color.z, color.w)
					.endVertex();
			buffer.pos(doorPos.getX() - center.getX() ,
					doorPos.getY() - center.getY() + 1,
					doorPos.getZ() - center.getZ() )
					.color(color.x, color.y, color.z, color.w)
					.endVertex();
		}
		Tessellator.getInstance().draw();
	}

	private void drawBorderSpehere(int radius, int spokes, int smoothness, VertexBuffer buffer, Vector4f color)
	{
		for(int i = 0; i < spokes; i++) {
			double iAngle = ((double) i / spokes) * Math.PI;
			buffer.begin(GL_LINE_LOOP, DefaultVertexFormats.POSITION_COLOR);
			for(int a = 0; a < smoothness; a++) {
				double aAngle = ((double) a / smoothness) * Math.PI * 2;
				buffer.pos(Math.cos(aAngle) * Math.cos(iAngle) * radius,
						Math.sin(aAngle) * radius,
						Math.cos(aAngle) * -Math.sin(iAngle) * radius).color(color.x, color.y, color.z, color.w).endVertex();
			}
			Tessellator.getInstance().draw();
		}
		for(int i = 0; i < spokes; i++) {
			double iAngle = ((double) i / spokes) * Math.PI;
			buffer.begin(GL_LINE_LOOP, DefaultVertexFormats.POSITION_COLOR);
			for(int a = 0; a < smoothness; a++) {
				double aAngle = ((double) a / smoothness) * Math.PI * 2;
				buffer.pos(Math.sin(aAngle) * Math.sin(iAngle) * radius,
						Math.cos(iAngle) * radius,
						Math.cos(aAngle) * Math.sin(iAngle) * radius).color(color.x, color.y, color.z, color.w).endVertex();
			}
			Tessellator.getInstance().draw();
		}
	}

	public static void setVillageData(List<VillageData> data)
	{
		villageDataList = data;
		//Log.info("------------Client received village data-----------------> " + villageDataList.size());
	}
}
