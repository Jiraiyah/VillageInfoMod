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

	public static void register()
	{
		VILLAGE_DATA = new KeyBinding(Reference.MOD_ID.toLowerCase() + ".key.villagedata", KeyConflictContext.IN_GAME, Keyboard.KEY_V, Reference.MOD_ID.toLowerCase() + ".key.categories");

		VILLAGE_DATA_DOORS = new KeyBinding(Reference.MOD_ID.toLowerCase() + ".key.villagedatadoors", KeyConflictContext.IN_GAME, KeyModifier.CONTROL, Keyboard.KEY_1, Reference.MOD_ID.toLowerCase() + ".key.categories");
		VILLAGE_DATA_GOLEM = new KeyBinding(Reference.MOD_ID.toLowerCase() + ".key.villagedatagolem", KeyConflictContext.IN_GAME, KeyModifier.CONTROL, Keyboard.KEY_2, Reference.MOD_ID.toLowerCase() + ".key.categories");
		VILLAGE_DATA_BORDER = new KeyBinding(Reference.MOD_ID.toLowerCase() + ".key.villagedataborder", KeyConflictContext.IN_GAME, KeyModifier.CONTROL, Keyboard.KEY_3, Reference.MOD_ID.toLowerCase() + ".key.categories");
		VILLAGE_DATA_INFO = new KeyBinding(Reference.MOD_ID.toLowerCase() + ".key.villagedatainfo", KeyConflictContext.IN_GAME, KeyModifier.CONTROL, Keyboard.KEY_4, Reference.MOD_ID.toLowerCase() + ".key.categories");
		VILLAGE_DATA_CENTER = new KeyBinding(Reference.MOD_ID.toLowerCase() + ".key.villagedatacenter", KeyConflictContext.IN_GAME, KeyModifier.CONTROL, Keyboard.KEY_5, Reference.MOD_ID.toLowerCase() + ".key.categories");
		VILLAGE_DATA_SPHERE = new KeyBinding(Reference.MOD_ID.toLowerCase() + ".key.villagedatasphere", KeyConflictContext.IN_GAME, KeyModifier.CONTROL, Keyboard.KEY_6, Reference.MOD_ID.toLowerCase() + ".key.categories");

		ClientRegistry.registerKeyBinding(VILLAGE_DATA);

		ClientRegistry.registerKeyBinding(VILLAGE_DATA_DOORS);
		ClientRegistry.registerKeyBinding(VILLAGE_DATA_GOLEM);
		ClientRegistry.registerKeyBinding(VILLAGE_DATA_BORDER);
		ClientRegistry.registerKeyBinding(VILLAGE_DATA_INFO);
		ClientRegistry.registerKeyBinding(VILLAGE_DATA_CENTER);
		ClientRegistry.registerKeyBinding(VILLAGE_DATA_SPHERE);

		//Log.info("=========================================================> Registered Key Bindings");
	}
}
