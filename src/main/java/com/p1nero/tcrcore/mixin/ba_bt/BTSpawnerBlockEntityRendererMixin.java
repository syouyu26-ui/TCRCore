package com.p1nero.tcrcore.mixin.ba_bt;

import com.brass_amber.ba_bt.block.blockentity.spawner.BTAbstractSpawnerBlockEntity;
import com.brass_amber.ba_bt.client.renderer.BTSpawnerBlockEntityRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BaseSpawner;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BTSpawnerBlockEntityRenderer.class)
public class BTSpawnerBlockEntityRendererMixin {

    @Shadow(remap = false) @Final private EntityRenderDispatcher entityRenderer;

    @Inject(method = "render(Lcom/brass_amber/ba_bt/block/blockentity/spawner/BTAbstractSpawnerBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", at = @At("HEAD"), cancellable = true, remap = false)
    private void tcr$render(BTAbstractSpawnerBlockEntity spawnerBlockEntity, float rotation, PoseStack poseStack, MultiBufferSource bufferSource, int lightCoords, int p_112568_, CallbackInfo ci) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 0.0D, 0.5F);
        BaseSpawner basespawner = spawnerBlockEntity.getSpawner();
        if(spawnerBlockEntity.getLevel() != null) {
            Entity entity = basespawner.getOrCreateDisplayEntity(spawnerBlockEntity.getLevel(), spawnerBlockEntity.getLevel().getRandom(), spawnerBlockEntity.getBlockPos());
            if (entity != null) {
                entity.setGlowingTag(true);
                float f = 0.53125F;
                float f1 = Math.max(entity.getBbWidth(), entity.getBbHeight());
                if ((double) f1 > 1.0D) {
                    f /= f1;
                }
                poseStack.translate(0.0F, (double)0.4F, 0.0F);
                poseStack.mulPose(Axis.YP.rotationDegrees((float) Mth.lerp(rotation, basespawner.getoSpin(), basespawner.getSpin()) * 10.0F));
                poseStack.translate(0.0F, (double)-0.2F, 0.0F);
                poseStack.mulPose(Axis.XP.rotationDegrees(-30.0F));
                poseStack.scale(f, f, f);
                this.entityRenderer.render(entity, 0.0D, 0.0D, 0.0D, 0.0F, rotation, poseStack, bufferSource, lightCoords);
            }
        }

        poseStack.popPose();
        ci.cancel();
    }

}
