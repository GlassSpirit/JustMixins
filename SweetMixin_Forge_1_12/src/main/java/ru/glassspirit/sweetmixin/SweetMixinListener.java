package ru.glassspirit.sweetmixin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class SweetMixinListener {

    @SubscribeEvent
    public static void onEntityKill(LivingHurtEvent event) {
        if (event.getSource().getTrueSource() instanceof EntityPlayer) {
            EssenceContainer essencePlayer = (EssenceContainer) event.getSource().getTrueSource();
            if (essencePlayer.getEssence() == essencePlayer.getMaxEssence()) {
                EssenceContainer essenceItemStack = (EssenceContainer) (Object) ((EntityPlayer) event.getSource().getTrueSource()).getHeldItemMainhand();
                essenceItemStack.setEssence(essenceItemStack.getEssence() + 1.0F);
            } else essencePlayer.setEssence(essencePlayer.getEssence() + 1.0F);
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getPlayer() != null) {
            EssenceContainer essencePlayer = (EssenceContainer) event.getPlayer();
            EssenceContainer essenceItem = (EssenceContainer) (Object) event.getPlayer().getHeldItemMainhand();
            if (essenceItem.getEssence() > 0) {
                essenceItem.setEssence(essenceItem.getEssence() - 1.0F);
            } else if (essencePlayer.getEssence() > 0) {
                essencePlayer.setEssence(essencePlayer.getEssence() - 1.0F);
            } else event.setCanceled(true);
        }
    }

}
