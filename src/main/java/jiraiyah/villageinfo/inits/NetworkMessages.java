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
package jiraiyah.villageinfo.inits;

import jiraiyah.villageinfo.network.SpawnPlayerMessage;
import jiraiyah.villageinfo.network.SpawnServerMessage;
import jiraiyah.villageinfo.network.VillagePlayerMessage;
import jiraiyah.villageinfo.network.VillageServerMessage;
import jiraiyah.villageinfo.references.Reference;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkMessages
{
	public static SimpleNetworkWrapper network;

	public static void register()
	{
		network = new SimpleNetworkWrapper( Reference.MOD_ID );
		network.registerMessage( VillageServerMessage.class, VillageServerMessage.Packet.class, nextId(), Side.CLIENT );
		network.registerMessage( VillagePlayerMessage.class, VillagePlayerMessage.Packet.class, nextId(), Side.SERVER );
		network.registerMessage( SpawnServerMessage.class, SpawnServerMessage.Packet.class, nextId(), Side.CLIENT );
		network.registerMessage( SpawnPlayerMessage.class, SpawnPlayerMessage.Packet.class, nextId(), Side.SERVER );
		//Log.info("=========================================================> Registered Network Messages");
	}

	private static int ID = 0;

	private static int nextId()
	{
		return ID++;
	}
}
