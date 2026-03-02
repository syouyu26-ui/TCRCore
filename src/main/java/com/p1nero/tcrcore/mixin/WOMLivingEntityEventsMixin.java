package com.p1nero.tcrcore.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import reascer.wom.events.WOMLivingEntityEvents;

import static reascer.wom.events.WOMLivingEntityEvents.*;

@Mixin(WOMLivingEntityEvents.class)
public class WOMLivingEntityEventsMixin {

    @Inject(method = "onSpawnEvent", at = @At("HEAD"), cancellable = true, remap = false)
    private static void tcr$onLivingSpawn(MobSpawnEvent event, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "onUpdateEvent", at = @At("HEAD"), cancellable = true, remap = false)
    private static void tcr$onLivingTick(LivingEvent.LivingTickEvent event, CallbackInfo ci) {
        ci.cancel();
        Entity e = event.getEntity();
        AntiStunlock(event, e);
        TimedSakuraSlashes(event, e);
        LunarEclipse(event, e);
        SolarIgnited(event);
        Blackout(event);
    }
}
