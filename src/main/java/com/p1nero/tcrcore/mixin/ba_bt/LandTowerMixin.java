package com.p1nero.tcrcore.mixin.ba_bt;

import com.brass_amber.ba_bt.worldGen.structures.LandTower;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LandTower.class)
public class LandTowerMixin {

    @Inject(method = "getTowerTypeConversion", at = @At("HEAD"), cancellable = true, remap = false)
    private void tcr$getTowerTypeConversion(CallbackInfoReturnable<String[]> cir) {
        cir.setReturnValue(new String[]{"normal", "icy"});
    }

}
