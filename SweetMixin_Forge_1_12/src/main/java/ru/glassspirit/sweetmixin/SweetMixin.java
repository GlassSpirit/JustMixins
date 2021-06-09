package ru.glassspirit.sweetmixin;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

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
