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
package jiraiyah.villageinfo.infrastructure;

import jiraiyah.villageinfo.references.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

@SuppressWarnings("unused")
public class Config
{
	public static int updateRatio = 1;
	public static int villageDetectDistance = 140;
	public static boolean perVillageColor;

	public static int spawnChunkShowDistance = 140;

	private static final String CATEGORY_GENERIC = "Generic";

	private static File configurationFile;
	private static Configuration config;


	@SubscribeEvent
	public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if (Reference.MOD_ID.equals(event.getModID()))
		{
			loadConfigs(config);
		}
	}

	public static void loadConfigsFromFile(File configFile)
	{
		configurationFile = configFile;
		config = new Configuration(configFile, "1.0", true);
		config.load();

		loadConfigs(config);
	}

	public static void loadConfigs(Configuration conf)
	{
		Property prop = conf.get(CATEGORY_GENERIC, "updateRatio", 1).setRequiresMcRestart(false);
		prop.setComment("Second intervals that server will update village information for each player, should be a position integer (obviously).");
		updateRatio = prop.getInt();

		prop = conf.get(CATEGORY_GENERIC, "villageDetectDistance", 140).setRequiresMcRestart(false);
		prop.setComment("Distance from border of the village (not the center) that server accepts for each player to show the village info for, should be positive integer.");
		villageDetectDistance = prop.getInt();

		prop = conf.get(CATEGORY_GENERIC, "perVillageColor", false).setRequiresMcRestart(false);
		prop.setComment("If set to true, every graphical representation for single village will be in same color, but change per village base. If you have single village, set it to false for color change in the village information.");
		perVillageColor = prop.getBoolean();

		prop = conf.get(CATEGORY_GENERIC, "spawnChunkShowDistance", 140).setRequiresMcRestart(false);
		prop.setComment("Distance from border of the spawn chunks that graphics starts showing up, should be positive (obviously).");
		spawnChunkShowDistance = prop.getInt();

		if (conf.hasChanged())
		{
			conf.save();
		}
	}
}