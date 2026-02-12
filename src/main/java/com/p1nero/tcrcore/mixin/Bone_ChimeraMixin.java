package com.p1nero.tcrcore.mixin;

import com.github.dodo.dodosmobs.entity.InternalAnimationMonster.IABossMonsters.Bone_Chimera_Entity;
import com.github.dodo.dodosmobs.entity.InternalAnimationMonster.IABossMonsters.IABoss_monster;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 对话控制开打
 */
@Mixin(Bone_Chimera_Entity.class)
public class Bone_ChimeraMixin extends IABoss_monster {

    public Bone_ChimeraMixin(EntityType entity, Level world) {
        super(entity, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tcr$tick(CallbackInfo ci) {
        boolean fighting = this.getPersistentData().getBoolean("fighting");
        if(tickCount < 20 || !fighting) {
            this.setTarget(null);
        }
        if(!fighting) {
            if(tickCount > 22) {
                tickCount = 22;
            }
        }
    }

    @Inject(method = "aiStep", at = @At("HEAD"))
    private void tcr$aiStep(CallbackInfo ci) {
        boolean fighting = this.getPersistentData().getBoolean("fighting");
        if(!fighting) {
            this.setTarget(null);
            this.attackTicks = 0;
        }
    }

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void tcr$hurt(DamageSource damagesource, float amount, CallbackInfoReturnable<Boolean> cir) {
        boolean fighting = this.getPersistentData().getBoolean("fighting");
        if(!fighting || this.tickCount < 20) {
            cir.setReturnValue(false);
        }
    }

}
