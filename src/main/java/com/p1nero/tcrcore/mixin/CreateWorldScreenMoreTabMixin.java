package com.p1nero.tcrcore.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.p1nero.tcrcore.events.ClientForgeEvents;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CreateWorldScreen.MoreTab.class)
public class CreateWorldScreenMoreTabMixin {

    @WrapOperation(
            method = {"<init>"},
            at = {@At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/layouts/GridLayout$RowHelper;addChild(Lnet/minecraft/client/gui/layouts/LayoutElement;)Lnet/minecraft/client/gui/layouts/LayoutElement;"
            )}
    )
    private <T extends LayoutElement> T tcr$wrapAddChild(GridLayout.RowHelper instance, T layoutElement, Operation<T> original) {
        Component EXPERIMENTS_LABEL = Component.translatable("selectWorld.experiments");
        Component DATA_PACKS_LABEL = Component.translatable("selectWorld.dataPacks");
        if(this.tcrcore$checkExperiments(layoutElement)) {
            Button button = Button.builder(EXPERIMENTS_LABEL, (button1) -> {
                int dx = (int)(Math.random() * 100) - 50;
                int dy = (int)(Math.random() * 100) - 50;
                button1.setPosition(button1.getX() + dx, button1.getY() + dy);
            }).width(210).build();
            instance.addChild(button);
            ClientForgeEvents.buttonsInCreateWorldScreen.add(button);
            return null;
        }
        if(this.tcrcore$checkDataPacks(layoutElement)) {
            Button button = Button.builder(DATA_PACKS_LABEL,  (button1) -> {
                int dx = (int)(Math.random() * 100) - 50;
                int dy = (int)(Math.random() * 100) - 50;
                button1.setPosition(button1.getX() + dx, button1.getY() + dy);
            }).width(210).build();
            instance.addChild(button);
            ClientForgeEvents.buttonsInCreateWorldScreen.add(button);
            return null;
        }
        return original.call(instance, layoutElement);
    }

    @Unique
    private boolean tcrcore$checkExperiments(Object layoutElement) {
        if (layoutElement instanceof Button button) {
            Component message = button.getMessage();
            if (message instanceof MutableComponent c) {
                ComponentContents contents = c.getContents();
                if (contents instanceof TranslatableContents t) {
                    return "selectWorld.experiments".equals(t.getKey());
                }
            }
        }
        return false;
    }

    @Unique
    private boolean tcrcore$checkDataPacks(Object layoutElement) {
        if (layoutElement instanceof Button button) {
            Component message = button.getMessage();
            if (message instanceof MutableComponent c) {
                ComponentContents contents = c.getContents();
                if (contents instanceof TranslatableContents t) {
                    return "selectWorld.dataPacks".equals(t.getKey());
                }
            }
        }
        return false;
    }

}
