package jiraiyah.villageinfo;

import jiraiyah.villageinfo.proxies.CommonProxy;
import jiraiyah.villageinfo.references.Reference;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@SuppressWarnings({"WeakerAccess", "unused"})
@Mod(modid = Reference.MOD_ID, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES, name = Reference.MOD_NAME)
public class VillageInfo
{
    @Mod.Instance(Reference.MOD_ID)
    public static VillageInfo INSTANCE;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.COMMON_PROXY)
    public static CommonProxy PROXY;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        PROXY.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        PROXY.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        PROXY.postInit(event);
    }
}
