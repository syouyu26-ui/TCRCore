package com.p1nero.tcrcore.mixin.ba_bt;

import com.brass_amber.ba_bt.worldGen.structures.CoreTower;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CoreTower.class)
public class CoreTowerMixin {

    @Inject(method = "getTowerType", at = @At("HEAD"), cancellable = true, remap = false)
    private void tcr$getTowerType(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(0);
    }

}
