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
import jiraiyah.villageinfo.infrastructure.VillageData;
import jiraiyah.villageinfo.inits.KeyBindings;
import jiraiyah.villageinfo.network.VillagePlayerMessage;
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
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.vecmath.Vector4f;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

@SuppressWarnings({"unused", "SuspiciousNameCombination"})
public class VillageDataHandler
{
	private boolean showVillages;
	private boolean showVillagesDoors = true;
	private boolean showVillagesBorder;
	private boolean showVillagesSpehere;
	private boolean showVillagesCenter;
	private boolean showVillagesInfo;
	private boolean showVillagesGolem = true;


	private static List<VillageData> villageDataList = new ArrayList<>();
	private static final float PI = (float) Math.PI;
	private static final float DEG2RAD = PI/180;

	@SubscribeEvent
	public void renderWorldLastEvent(RenderWorldLastEvent event)
	{
		if (!showVillages || villageDataList == null || villageDataList.size() == 0)
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
		for (VillageData data : villageDataList)
		{
			double distance = Math.sqrt(playerPos.squareDistanceTo(data.center.getX(), data.center.getY(), data.center.getZ()));
			double scaleFactor = -0.015f * distance / 4.209f;
			boolean canSpaw = data.doorPositions.size() > 20;
			GlStateManager.pushMatrix();
			{
				GlStateManager.translate(0.5f + data.center.getX() - plX,
						0.5f + data.center.getY() - plY,
						0.5f + data.center.getZ() - plZ);
				GlStateManager.glLineWidth(1);
				GlStateManager.disableLighting();
				GlStateManager.disableTexture2D();
				GlStateManager.disableDepth();
				GlStateManager.enableBlend();
				GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
				{
					// TODO : per village coloring
					// TODO : semi solid cube for some parts
					if (showVillagesSpehere)
						drawBorderSpehere(data.radius, buffer, Config.perVillageColor ? new Vector4f(1f, 0f, 1f, 1f) : new Vector4f(1f, 0f, 1f, 1f));
					if (showVillagesDoors)
						drawDoors(data.center, data.doorPositions, buffer, Config.perVillageColor ? new Vector4f(1f, 0f, 0f, 1f) : new Vector4f(1f, 1f, 1f, 1f));
					if (showVillagesBorder)
						drawBorderSquare(data.radius, buffer, Config.perVillageColor ? new Vector4f(1f, 0f, 0f, 1f) : new Vector4f(1f, 1f, 0f, 0.5f));
					if (showVillagesGolem)
						drawGolemSpawn(buffer, Config.perVillageColor ? new Vector4f(1f, 0f, 0f, 1f) : (canSpaw ? new Vector4f(0f, 1f, 0f, 1f) : new Vector4f(0.8f, 0f, 0f, 1f)));
					if (showVillagesCenter)
						drawCenter(buffer, Config.perVillageColor ? new Vector4f(1f, 0f, 0f, 1f) : new Vector4f(1f, 0f, 0f, 1f));
					if (showVillagesInfo)
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
						drawTextInfo("Max Golem : " + (canSpaw ? (TextFormatting.GREEN + Integer.toString(data.villagerCount / 10)) : TextFormatting.DARK_RED + "" + TextFormatting.BOLD + "0"), 3, scaleFactor, fontrenderer);
						drawTextInfo("Reputation : " + data.reputation, 4, scaleFactor, fontrenderer);
						GlStateManager.disableBlend();
					}
				}
				GlStateManager.glLineWidth(1);
				GlStateManager.disableBlend();
				GlStateManager.enableDepth();
				GlStateManager.enableTexture2D();
				GlStateManager.enableLighting();
			}
			GlStateManager.popMatrix();
		}
	}

	private void drawTextInfo(String text, int lineNumber, double scaleFactor, FontRenderer fontrenderer)
	{
		fontrenderer.drawString(text, -fontrenderer.getStringWidth(text) / 2, 10 * lineNumber, -1);
	}

	private void drawCenter(VertexBuffer buffer, Vector4f color)
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

	private void drawGolemSpawn(VertexBuffer buffer, Vector4f color)
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

	private void drawBorderSpehere(int radius, VertexBuffer buffer, Vector4f color)
	{
		// TODO : another, clearner algorithm to draw sphere !
		int space = 5;
		int upper = 90;
		int upper2 = 360 - space;
		double x, y, z;
		for (int b = -space; b <= upper; b += space)
		{
			buffer.begin(GL_LINE_LOOP, DefaultVertexFormats.POSITION_COLOR);
			for (int a = 0; a <= upper2; a++)
			{
				x = radius * Math.sin((a) * DEG2RAD) * Math.sin((b) * DEG2RAD);
				z = radius * Math.cos((a) * DEG2RAD) * Math.sin((b) * DEG2RAD);
				y = radius * Math.cos((b) * DEG2RAD);
				buffer.pos(x, y, z).color(color.x, color.y, color.z, color.w).endVertex();
			}
			Tessellator.getInstance().draw();
		}
		upper = 180 - space;
		upper2 = 90;
		for (int b = 0; b <= upper; b += space)
		{
			buffer.begin(GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
			for (int a = -90; a <= upper2; a++)
			{
				x = radius * Math.sin((a) * DEG2RAD) * Math.sin((b) * DEG2RAD);
				z = radius * Math.cos((a) * DEG2RAD) * Math.sin((b) * DEG2RAD);
				y = radius * Math.cos((b) * DEG2RAD);
				buffer.pos(x, z, y).color(color.x, color.y, color.z, color.w).endVertex();
			}
			Tessellator.getInstance().draw();
		}
		upper = 180 - space;
		upper2 = 90;
		for (int b = 0; b <= upper; b += space)
		{
			buffer.begin(GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
			for (int a = -90; a <= upper2; a++)
			{
				x = radius * Math.sin((a) * DEG2RAD) * Math.sin((b) * DEG2RAD);
				z = radius * Math.cos((a) * DEG2RAD) * Math.sin((b) * DEG2RAD);
				y = radius * Math.cos((b) * DEG2RAD);
				buffer.pos(y, z, x).color(color.x, color.y, color.z, color.w).endVertex();
			}
			Tessellator.getInstance().draw();
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event)
	{
		if(KeyBindings.VILLAGE_DATA.isPressed())
		{
			showVillages = !showVillages;
			VillagePlayerMessage.sendMessage(Minecraft.getMinecraft().thePlayer.getUniqueID(), showVillages);
		}
		if (KeyBindings.VILLAGE_DATA_BORDER.isPressed())
			showVillagesBorder = !showVillagesBorder;
		if (KeyBindings.VILLAGE_DATA_DOORS.isPressed())
			showVillagesDoors = !showVillagesDoors;
		if (KeyBindings.VILLAGE_DATA_SPHERE.isPressed())
			showVillagesSpehere = !showVillagesSpehere;
		if (KeyBindings.VILLAGE_DATA_GOLEM.isPressed())
			showVillagesGolem = !showVillagesGolem;
		if (KeyBindings.VILLAGE_DATA_INFO.isPressed())
			showVillagesInfo = !showVillagesInfo;
		if (KeyBindings.VILLAGE_DATA_CENTER.isPressed())
			showVillagesCenter = !showVillagesCenter;

	}

	public static void setVillageData(List<VillageData> data)
	{
		villageDataList = data;
		//Log.info("------------Client received village data-----------------> " + villageDataList.size());
	}
}
