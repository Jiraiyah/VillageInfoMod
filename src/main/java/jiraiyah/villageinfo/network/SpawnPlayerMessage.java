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
import jiraiyah.villageinfo.events.WorldDataCollector;
import jiraiyah.villageinfo.inits.NetworkMessages;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class SpawnPlayerMessage implements IMessageHandler<SpawnPlayerMessage.Packet, IMessage>
{
	@Override
	public IMessage onMessage (SpawnPlayerMessage.Packet message, MessageContext ctx )
	{
		WorldDataCollector.getSpawnInformation(message.playerId);
		return null;
	}

	public static void sendMessage(UUID playerId)
	{
		Packet packet = new Packet(playerId);
		NetworkMessages.network.sendToServer(packet);
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public static class Packet implements IMessage
	{
		public UUID playerId;
		public boolean adding;

		public Packet()
		{

		}

		public Packet(UUID playerId)
		{
			this.playerId = playerId;
		}

		@Override
		public void fromBytes(ByteBuf buf)
		{
			int size = buf.readInt();
			byte[] bytes = buf.readBytes(size).array();
			String id = new String(bytes);
			playerId = UUID.fromString(id);
		}

		@Override
		public void toBytes(ByteBuf buf)
		{
			String id = playerId.toString();
			byte[] bytes = id.getBytes();
			buf.writeInt(bytes.length);
			buf.writeBytes(bytes);
		}
	}
}

