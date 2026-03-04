package com.p1nero.tcrcore.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Drowned.class)
public abstract class DrownedMixin extends Zombie implements RangedAttackMob {

    public DrownedMixin(EntityType<? extends Zombie> p_34271_, Level p_34272_) {
        super(p_34271_, p_34272_);
    }

    @Inject(method = "wantsToSwim", at = @At("HEAD"), cancellable = true)
    private void tcr$wantToSwim(CallbackInfoReturnable<Boolean> cir) {
        if(this.getPersistentData().getBoolean("spawn_in_ocean_tower")) {
            cir.setReturnValue(false);
        }
    }

}
