package ru.glassspirit.sweetmixin;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(
        modid = SweetMixin.MOD_ID,
        name = SweetMixin.MOD_NAME,
        version = SweetMixin.VERSION
)
public class SweetMixin {

    public static final String MOD_ID = "sweetmixin";
    public static final String MOD_NAME = "SweetMixin";
    public static final String VERSION = "1.0";

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new SweetMixinListener());
    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {
    }
}
