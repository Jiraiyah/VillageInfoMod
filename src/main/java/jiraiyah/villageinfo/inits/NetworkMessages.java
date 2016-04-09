/**
 * Copyright 2016 Village Info (Jiraiyah)
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
