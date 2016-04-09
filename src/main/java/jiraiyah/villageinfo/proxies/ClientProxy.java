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
package jiraiyah.villageinfo.proxies;

import jiraiyah.villageinfo.inits.ClientEventRegister;
import jiraiyah.villageinfo.inits.KeyBindings;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.lwjgl.input.Keyboard;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
		ClientEventRegister.register();
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
		KeyBindings.register();
	}

	@Override
	public void postInit(FMLPostInitializationEvent event)
	{
		super.postInit(event);
	}

	public static boolean isCtrlDown()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) ||
				Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
	}

	public static boolean isShiftDown()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) ||
				Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
	}

	public static boolean isAltDown()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_LMENU) ||
				Keyboard.isKeyDown(Keyboard.KEY_RMENU);
	}
}
