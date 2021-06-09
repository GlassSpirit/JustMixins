package ru.glassspirit.sweetmixin.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.glassspirit.sweetmixin.EssenceContainer;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity implements EssenceContainer {

    /**
     * Наше новое поле в классе EntityPlayer - эссенция
     */
    private float essence;

    /**
     * Виртуальное поле, которое ссылается на реальное поле experienceLevel в классе EntityPlayer
     */
    @Shadow(remap = true)
    public int experienceLevel;

    @Override
    public float getEssence() {
        return this.essence;
    }

    /**
     * Изменяет текущее количество эссенции игрока. Если оно больше, чем максимально возможное для игрока - ставится максимальное
     */
    @Override
    public void setEssence(float value) {
        this.essence = Math.min(value, getMaxEssence());
    }

    /**
     * Максимальное количество эссенции игрока равно его уровню
     */
    @Override
    public float getMaxEssence() {
        return this.experienceLevel;
    }

    @Override
    public void setMaxEssence(float value) {
        // NOOP
        System.out.println("Максимальное количество эссенции зависит от уровня игрока!");
    }

    @Inject(method = "writeAdditional", at = @At("TAIL"))
    private void onWriteEntityToNBT(CompoundNBT compound, CallbackInfo ci) {
        compound.putFloat("Essence", this.getEssence());
    }

    @Inject(method = "readAdditional", at = @At("TAIL"))
    private void onReadEntityFromNBT(CompoundNBT compound, CallbackInfo ci) {
        setEssence(compound.getFloat("Essence"));
    }
}
