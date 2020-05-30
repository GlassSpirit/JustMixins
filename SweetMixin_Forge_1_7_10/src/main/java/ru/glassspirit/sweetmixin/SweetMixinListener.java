package ru.glassspirit.sweetmixin;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.world.BlockEvent;

public class SweetMixinListener {

    @SubscribeEvent
    public void onEntityKill(LivingHurtEvent event) {
        if (event.source.getSourceOfDamage() instanceof EntityPlayer) {
            EssenceContainer essencePlayer = (EssenceContainer) event.source.getSourceOfDamage();
            if (essencePlayer.getEssence() == essencePlayer.getMaxEssence()) {
                EssenceContainer essenceItemStack = (EssenceContainer) (Object) ((EntityPlayer) event.source.getSourceOfDamage()).getHeldItem();
                if (essenceItemStack != null) essenceItemStack.setEssence(essenceItemStack.getEssence() + 1.0F);
            } else essencePlayer.setEssence(essencePlayer.getEssence() + 1.0F);
        }
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getPlayer() != null) {
            EssenceContainer essencePlayer = (EssenceContainer) event.getPlayer();
            EssenceContainer essenceItemStack = (EssenceContainer) (Object) event.getPlayer().getHeldItem();
            if (essenceItemStack != null && essenceItemStack.getEssence() > 0) {
                essenceItemStack.setEssence(essenceItemStack.getEssence() - 1.0F);
            } else if (essencePlayer.getEssence() > 0) {
                essencePlayer.setEssence(essencePlayer.getEssence() - 1.0F);
            } else event.setCanceled(true);
        }
    }

}
