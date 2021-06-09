package ru.glassspirit.sweetmixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SweetMixinListener {

    @SubscribeEvent
    public void onEntityKill(LivingHurtEvent event) {
        if (event.getSource().getTrueSource() instanceof PlayerEntity) {
            EssenceContainer essencePlayer = (EssenceContainer) event.getSource().getTrueSource();
            if (essencePlayer.getEssence() == essencePlayer.getMaxEssence()) {
                EssenceContainer essenceItemStack = (EssenceContainer) (Object) ((PlayerEntity) event.getSource().getTrueSource()).getHeldItemMainhand();
                essenceItemStack.setEssence(essenceItemStack.getEssence() + 1.0F);
            } else essencePlayer.setEssence(essencePlayer.getEssence() + 1.0F);
        }
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
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
