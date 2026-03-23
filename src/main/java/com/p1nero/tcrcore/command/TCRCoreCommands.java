package com.p1nero.tcrcore.command;

import com.mojang.brigadier.CommandDispatcher;
import com.p1nero.tcrcore.TCRCoreMod;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TCRCoreCommands {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        TCRDebugCommand.register(dispatcher);
        TCRDifficultyCommand.register(dispatcher);
    }
}