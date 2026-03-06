package com.p1nero.tcrcore.mixin;

import com.mojang.blaze3d.platform.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Window.class)
public class WindowMixin {

    @Inject(method = "defaultErrorCallback", at = @At("HEAD"), cancellable = true)
    private void tcr$defaultErrorCallback(int p_85383_, long p_85384_, CallbackInfo ci) {
        ci.cancel();
    }

}
