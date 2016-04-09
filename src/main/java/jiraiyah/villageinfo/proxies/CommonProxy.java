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

import jiraiyah.villageinfo.inits.CommonEventRegister;
import jiraiyah.villageinfo.inits.NetworkMessages;
import jiraiyah.villageinfo.interfaces.IProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy implements IProxy
{

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		CommonEventRegister.register();
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		NetworkMessages.register();
	}

	@Override
	public void postInit(FMLPostInitializationEvent event)
	{

	}
}
