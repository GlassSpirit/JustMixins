package ru.glassspirit.sweetmixin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SweetMixinListener {

    @SubscribeEvent
    public void onEntityKill(LivingHurtEvent event) {
        if (event.getSource().getTrueSource() instanceof EntityPlayer) {
            EssenceContainer essencePlayer = (EssenceContainer) event.getSource().getTrueSource();
            if (essencePlayer.getEssence() == essencePlayer.getMaxEssence()) {
                EssenceContainer essenceItemStack = (EssenceContainer) (Object) ((EntityPlayer) event.getSource().getTrueSource()).getHeldItemMainhand();
                essenceItemStack.setEssence(essenceItemStack.getEssence() + 1.0F);
            } else {
                essencePlayer.setEssence(essencePlayer.getEssence() + 1.0F);
                if (!event.getSource().getTrueSource().world.isRemote)
                    event.getSource().getTrueSource().sendMessage(new TextComponentString("Ваш уровень эссенции: " + essencePlayer.getEssence()));
            }
        }
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getPlayer() != null) {
            EssenceContainer essencePlayer = (EssenceContainer) event.getPlayer();
            EssenceContainer essenceItem = (EssenceContainer) (Object) event.getPlayer().getHeldItemMainhand();
            if (essenceItem.getEssence() > 0) {
                essenceItem.setEssence(essenceItem.getEssence() - 1.0F);
                event.getPlayer().sendMessage(new TextComponentString("Ваш уровень эссенции: " + essencePlayer.getEssence()));
            } else if (essencePlayer.getEssence() > 0) {
                essencePlayer.setEssence(essencePlayer.getEssence() - 1.0F);
            } else {
                if (!event.getPlayer().world.isRemote)
                    event.getPlayer().sendMessage(new TextComponentString("Недостаточно эссенции для того, чтобы сломать"));
                event.setCanceled(true);
            }
        }
    }

}
