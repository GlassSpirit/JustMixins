package ru.glassspirit.sweetmixin.mixin;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
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
    public abstract NBTTagCompound getTagCompound();

    @Shadow
    public abstract void setTagCompound(@Nullable NBTTagCompound nbt);

    @Override
    public float getEssence() {
        return this.getItem() instanceof ItemSword && this.getTagCompound() != null ? this.getTagCompound().getFloat("Essence") : 0.0F;
    }

    @Override
    public void setEssence(float value) {
        if (this.getItem() instanceof ItemSword) {
            if (this.getTagCompound() == null) this.setTagCompound(new NBTTagCompound());
            this.getTagCompound().setFloat("Essence", Math.min(value, this.getMaxDamage()));
        }
    }

    @Override
    public float getMaxEssence() {
        return this.getItem() instanceof ItemSword ? this.getMaxDamage() : 0.0F;
    }

    @Override
    public void setMaxEssence(float value) {
        // NOOP
    }

    @Inject(method = "hitEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;addStat(Lnet/minecraft/stats/StatBase;)V"))
    private void onEntityHit(EntityLivingBase entityIn, EntityPlayer playerIn, CallbackInfo ci) {
        if (this.getItem() instanceof ItemSword)
            this.setEssence(this.getEssence() + 1.0F);
    }
}
