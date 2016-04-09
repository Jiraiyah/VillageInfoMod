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
package jiraiyah.villageinfo.utilities;

import jiraiyah.villageinfo.references.Reference;
import net.minecraftforge.fml.common.FMLLog;
import org.apache.logging.log4j.Level;

public class Log
{
	public static void log(Level logLevel, Object object)
	{
		FMLLog.log( Reference.MOD_NAME, logLevel, String.valueOf( object ) );
	}

	public static void all ( Object object )
	{
		log( Level.ALL, object );
	}

	public static void debug ( Object object )
	{
		log( Level.DEBUG, object );
	}

	public static void error ( Object object )
	{
		log( Level.ERROR, object );
	}

	public static void fatal ( Object object )
	{
		log( Level.FATAL, object );
	}

	public static void info ( Object object )
	{
		log( Level.INFO, object );
	}

	public static void off ( Object object )
	{
		log( Level.OFF, object );
	}

	public static void trace ( Object object )
	{
		log( Level.TRACE, object );
	}

	public static void warn ( Object object )
	{
		log( Level.WARN, object );
	}
}
