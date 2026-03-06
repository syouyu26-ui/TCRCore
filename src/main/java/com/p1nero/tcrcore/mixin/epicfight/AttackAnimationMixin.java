package com.p1nero.tcrcore.mixin.epicfight;

import com.hm.efn.registries.EFNItem;
import com.merlin204.sg.item.SGItems;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

@Mixin(AttackAnimation.class)
public class AttackAnimationMixin {

    @Inject(method = "getPlaySpeed", at = @At("RETURN"), cancellable = true, remap = false)
    private void tcr$getPlaySpeed(LivingEntityPatch<?> entityPatch, DynamicAnimation animation, CallbackInfoReturnable<Float> cir) {
        float max = 1.5F;
        ItemStack itemStack = entityPatch.getOriginal().getMainHandItem();
        //赤月额外算
        if(itemStack.is(EFNItem.CRIMSON_MOON.get())) {
            max = 3.0F;
            cir.setReturnValue(Math.min(max, cir.getReturnValue()));
            return;
        }
        CapabilityItem capabilityItem = EpicFightCapabilities.getItemStackCapabilityOr(itemStack, CapabilityItem.EMPTY);
        if(!capabilityItem.isEmpty()) {
            if(capabilityItem.getWeaponCategory().equals(CapabilityItem.WeaponCategories.UCHIGATANA)
                    || capabilityItem.getWeaponCategory().equals(CapabilityItem.WeaponCategories.SWORD)) {
                max = 1.3F;
            }
            if(capabilityItem.getWeaponCategory().equals(CapabilityItem.WeaponCategories.UCHIGATANA)) {
                max = 1.05F;
            }
            if(itemStack.is(SGItems.GOLEM_HEART.get())) {
                max = 1.2F;
            }
        }
        cir.setReturnValue(Math.min(max, cir.getReturnValue()));
    }

}
