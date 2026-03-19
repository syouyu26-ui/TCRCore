package com.p1nero.tcrcore.mixin.epicfight;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.client.render.CuriosLayer;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.compat.CuriosCompat;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(CuriosCompat.PatchedCuriosLayerRenderer.class)
public class CuriosCompatMixin {

    @Inject(method = "renderLayer(Lyesman/epicfight/world/capabilities/entitypatch/LivingEntityPatch;Lnet/minecraft/world/entity/LivingEntity;Ltop/theillusivec4/curios/client/render/CuriosLayer;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I[Lyesman/epicfight/api/utils/math/OpenMatrix4f;FFFF)V", at = @At("HEAD"), cancellable = true, remap = false)
    private void tcr$renderLayer(LivingEntityPatch<LivingEntity> entitypatch, LivingEntity livingEntity, CuriosLayer<LivingEntity, EntityModel<LivingEntity>> vanillaLayer, PoseStack poseStack, MultiBufferSource buffers, int packedLight, OpenMatrix4f[] poses, float bob, float yRot, float xRot, float partialTicks, CallbackInfo ci) {
        ci.cancel();
    }

}
