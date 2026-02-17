package com.p1nero.tcrcore.mixin;

import com.p1nero.tcrcore.capability.TCRQuestManager;
import com.p1nero.tcrcore.capability.TCRQuests;
import io.redspace.ironsspellbooks.gui.arcane_anvil.ArcaneAnvilMenu;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 完成尝试锻造的任务
 */
@Mixin(ArcaneAnvilMenu.class)
public class ArcaneAnvilMenuMixin {

    @Inject(method = "onTake", at = @At("HEAD"))
    private void tcr$onTack(Player player, ItemStack itemStack, CallbackInfo ci) {
        if(player instanceof ServerPlayer serverPlayer && TCRQuestManager.hasQuest(serverPlayer, TCRQuests.TRY_TO_LEARN_MAGIC)) {
            TCRQuests.TRY_TO_LEARN_MAGIC.finish(serverPlayer, true);
            TCRQuests.TALK_TO_AINE_MAGIC_2.start(serverPlayer);
        }
    }

}
