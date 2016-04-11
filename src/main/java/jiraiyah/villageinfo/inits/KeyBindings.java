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

import jiraiyah.villageinfo.references.Reference;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class KeyBindings
{
	public static KeyBinding VILLAGE_DATA;
	public static KeyBinding VILLAGE_DATA_SPHERE;
	public static KeyBinding VILLAGE_DATA_BORDER;
	public static KeyBinding VILLAGE_DATA_GOLEM;
	public static KeyBinding VILLAGE_DATA_DOORS;
	public static KeyBinding VILLAGE_DATA_INFO;
	public static KeyBinding VILLAGE_DATA_CENTER;
	public static KeyBinding SPAWN_CHUNK;
	public static KeyBinding SOLID_DRAW;
	public static KeyBinding Disable_DEPTH;

	public static void register()
	{
		VILLAGE_DATA = new KeyBinding(Reference.MOD_ID.toLowerCase() + ".key.villagedata", KeyConflictContext.IN_GAME, Keyboard.KEY_V, Reference.MOD_ID.toLowerCase() + ".key.categories");

		VILLAGE_DATA_DOORS = new KeyBinding(Reference.MOD_ID.toLowerCase() + ".key.villagedatadoors", KeyConflictContext.IN_GAME, KeyModifier.CONTROL, Keyboard.KEY_1, Reference.MOD_ID.toLowerCase() + ".key.categories");
		VILLAGE_DATA_GOLEM = new KeyBinding(Reference.MOD_ID.toLowerCase() + ".key.villagedatagolem", KeyConflictContext.IN_GAME, KeyModifier.CONTROL, Keyboard.KEY_2, Reference.MOD_ID.toLowerCase() + ".key.categories");
		VILLAGE_DATA_BORDER = new KeyBinding(Reference.MOD_ID.toLowerCase() + ".key.villagedataborder", KeyConflictContext.IN_GAME, KeyModifier.CONTROL, Keyboard.KEY_3, Reference.MOD_ID.toLowerCase() + ".key.categories");
		VILLAGE_DATA_INFO = new KeyBinding(Reference.MOD_ID.toLowerCase() + ".key.villagedatainfo", KeyConflictContext.IN_GAME, KeyModifier.CONTROL, Keyboard.KEY_4, Reference.MOD_ID.toLowerCase() + ".key.categories");
		VILLAGE_DATA_CENTER = new KeyBinding(Reference.MOD_ID.toLowerCase() + ".key.villagedatacenter", KeyConflictContext.IN_GAME, KeyModifier.CONTROL, Keyboard.KEY_5, Reference.MOD_ID.toLowerCase() + ".key.categories");
		VILLAGE_DATA_SPHERE = new KeyBinding(Reference.MOD_ID.toLowerCase() + ".key.villagedatasphere", KeyConflictContext.IN_GAME, KeyModifier.CONTROL, Keyboard.KEY_6, Reference.MOD_ID.toLowerCase() + ".key.categories");
		SOLID_DRAW = new KeyBinding(Reference.MOD_ID.toLowerCase() + ".key.soliddraw", KeyConflictContext.IN_GAME, KeyModifier.CONTROL, Keyboard.KEY_7, Reference.MOD_ID.toLowerCase() + ".key.categories");
		Disable_DEPTH = new KeyBinding(Reference.MOD_ID.toLowerCase() + ".key.disabledepth", KeyConflictContext.IN_GAME, KeyModifier.CONTROL, Keyboard.KEY_8, Reference.MOD_ID.toLowerCase() + ".key.categories");
		SPAWN_CHUNK = new KeyBinding(Reference.MOD_ID.toLowerCase() + ".key.spawnchunk", KeyConflictContext.IN_GAME, Keyboard.KEY_C, Reference.MOD_ID.toLowerCase() + ".key.categories");

		ClientRegistry.registerKeyBinding(VILLAGE_DATA);

		ClientRegistry.registerKeyBinding(VILLAGE_DATA_DOORS);
		ClientRegistry.registerKeyBinding(VILLAGE_DATA_GOLEM);
		ClientRegistry.registerKeyBinding(VILLAGE_DATA_BORDER);
		ClientRegistry.registerKeyBinding(VILLAGE_DATA_INFO);
		ClientRegistry.registerKeyBinding(VILLAGE_DATA_CENTER);
		ClientRegistry.registerKeyBinding(VILLAGE_DATA_SPHERE);
		ClientRegistry.registerKeyBinding(SPAWN_CHUNK);
		ClientRegistry.registerKeyBinding(SOLID_DRAW);
		ClientRegistry.registerKeyBinding(Disable_DEPTH);

		//Log.info("=========================================================> Registered Key Bindings");
	}
}
