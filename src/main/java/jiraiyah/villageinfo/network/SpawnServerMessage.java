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
package jiraiyah.villageinfo.network;

import io.netty.buffer.ByteBuf;
import jiraiyah.villageinfo.inits.NetworkMessages;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class SpawnServerMessage implements IMessageHandler<SpawnServerMessage.Packet, IMessage>
{
	@Override
	public IMessage onMessage (SpawnServerMessage.Packet message, MessageContext ctx )
	{
		return null;
	}

	public static void sendMessage(UUID player, BlockPos spawnPoint)
	{
		SpawnServerMessage.Packet packet = new SpawnServerMessage.Packet(spawnPoint);
		EntityPlayerMP playerMP = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(player);
		NetworkMessages.network.sendTo(packet, playerMP);
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public static class Packet implements IMessage
	{
		public BlockPos position;

		public Packet()
		{

		}

		public Packet(BlockPos position)

		{
			this.position = position;
		}

		@Override
		public void fromBytes(ByteBuf buf)
		{
			position = BlockPos.fromLong(buf.readLong());
		}

		@Override
		public void toBytes(ByteBuf buf)
		{
			buf.writeLong(position.toLong());
		}
	}
}
