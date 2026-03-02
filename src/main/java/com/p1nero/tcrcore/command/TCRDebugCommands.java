package com.p1nero.tcrcore.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.p1nero.invincible.capability.InvinciblePlayerCapabilityProvider;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.capability.TCRQuestManager;
import com.p1nero.tcrcore.capability.TCRQuests;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.stream.Collectors;

public class TCRDebugCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(TCRCoreMod.MOD_ID).requires((commandSourceStack) -> commandSourceStack.hasPermission(2))
                .then(Commands.literal("debug")
                        .then(Commands.literal("addQuest")
                                .then(Commands.argument("quest_id", StringArgumentType.string())
                                        .executes((context) -> {
                                            if(context.getSource().getPlayer() != null){
                                                TCRQuestManager.Quest quest = TCRQuestManager.getQuestByKey(StringArgumentType.getString(context, "quest_id"));
                                                if(quest != null) {
                                                    TCRQuestManager.startQuest(context.getSource().getPlayer(), quest);
                                                    context.getSource().getPlayer().displayClientMessage(Component.literal("Quest is added!").withStyle(ChatFormatting.GREEN), false);
                                                } else {
                                                    context.getSource().getPlayer().displayClientMessage(Component.literal("Quest is null!").withStyle(ChatFormatting.RED), false);
                                                }
                                            }
                                            return 0;
                                        })
                                )
                        )
                        .then(Commands.literal("finishQuest")
                                .then(Commands.argument("quest_id", StringArgumentType.string())
                                        .executes((context) -> {
                                            if(context.getSource().getPlayer() != null){
                                                TCRQuestManager.Quest quest = TCRQuestManager.getQuestByKey(StringArgumentType.getString(context, "quest_id"));
                                                if(quest != null) {
                                                    if(TCRQuestManager.finishQuest(context.getSource().getPlayer(), quest, true)){
                                                        context.getSource().getPlayer().displayClientMessage(Component.literal("Quest is removed!").withStyle(ChatFormatting.GREEN), false);
                                                    }
                                                } else {
                                                    context.getSource().getPlayer().displayClientMessage(Component.literal("Quest is null!").withStyle(ChatFormatting.RED), false);
                                                }
                                            }
                                            return 0;
                                        })
                                )
                        )
                        .then(Commands.literal("list_quest_ids")
                                .executes(context -> {
                                    List<String> questKeys = TCRQuestManager.getAllQuests().stream()
                                            .map(TCRQuestManager.Quest::getKey)
                                            .toList();
                                    if (context.getSource().getPlayer() != null) {
                                        String formatted = questKeys.stream()
                                                .map(key -> "[" + key + "]")
                                                .collect(Collectors.joining(", "));
                                        context.getSource().getPlayer().displayClientMessage(
                                                Component.literal(formatted).withStyle(ChatFormatting.GREEN), false
                                        );
                                    }
                                    return 0;
                                }))
                )
        );
    }

}
