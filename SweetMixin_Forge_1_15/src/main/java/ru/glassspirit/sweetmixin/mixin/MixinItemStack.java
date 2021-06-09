package ru.glassspirit.sweetmixin.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.glassspirit.sweetmixin.EssenceContainer;

import javax.annotation.Nullable;

@Mixin(ItemStack.class)
public abstract class MixinItemStack implements EssenceContainer {

    @Shadow
    public abstract Item getItem();

    @Shadow
    public abstract int getMaxDamage();

    @Shadow
    @Nullable
    public abstract CompoundNBT getTag();

    @Shadow
    public abstract void setTag(@Nullable CompoundNBT nbt);

    @Override
    public float getEssence() {
        return this.getItem() instanceof SwordItem && this.getTag() != null ? this.getTag().getFloat("Essence") : 0.0F;
    }

    @Override
    public void setEssence(float value) {
        if (this.getItem() instanceof SwordItem) {
            if (this.getTag() == null) this.setTag(new CompoundNBT());
            this.getTag().putFloat("Essence", Math.min(value, this.getMaxDamage()));
        }
    }

    @Override
    public float getMaxEssence() {
        return this.getItem() instanceof SwordItem ? this.getMaxDamage() : 0.0F;
    }

    @Override
    public void setMaxEssence(float value) {
        // NOOP
    }

    @Inject(method = "hitEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;addStat(Lnet/minecraft/stats/Stat;)V"))
    private void onEntityHit(LivingEntity entityIn, PlayerEntity playerIn, CallbackInfo ci) {
        if (this.getItem() instanceof SwordItem)
            this.setEssence(this.getEssence() + 1.0F);
    }
}
