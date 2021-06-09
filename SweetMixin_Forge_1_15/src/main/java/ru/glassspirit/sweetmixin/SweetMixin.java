package ru.glassspirit.sweetmixin;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SweetMixin.MOD_ID)
public class SweetMixin {

    public static final String MOD_ID = "sweetmixin";
    public static final String MOD_NAME = "SweetMixin";
    public static final String VERSION = "1.0";

    public SweetMixin() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);
    }

    private void init(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new SweetMixinListener());
    }
}
