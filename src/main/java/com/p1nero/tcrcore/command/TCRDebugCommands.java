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
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.stream.Collectors;

public class TCRDebugCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(TCRCoreMod.MOD_ID).requires((commandSourceStack) -> commandSourceStack.hasPermission(2))
                .then(Commands.literal("debug")
                        .then(Commands.literal("addQuest")
                                .then(Commands.argument("quest_id", StringArgumentType.string())
                                        .suggests((commandContext, suggestionsBuilder) -> {
                                            TCRQuestManager.getAllQuests().forEach((quest -> {
                                                Component message = quest.getTitle().copy()
                                                        .append(": ")
                                                        .append(quest.getShortDesc());
                                                suggestionsBuilder.suggest(quest.getKey(), message);
                                            }));
                                            return suggestionsBuilder.buildFuture();
                                        })
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
                                        .suggests((commandContext, suggestionsBuilder) -> {
                                            TCRQuestManager.getAllQuests().forEach((quest -> {
                                                Component message = quest.getTitle().copy()
                                                        .append(": ")
                                                        .append(quest.getShortDesc());
                                                suggestionsBuilder.suggest(quest.getKey(), message);
                                            }));
                                            return suggestionsBuilder.buildFuture();
                                        })
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
                        .then(Commands.literal("listQuestIds")
                                .executes(context -> {
                                    List<TCRQuestManager.Quest> quests = TCRQuestManager.getAllQuests().stream().toList();
                                    if (context.getSource().getPlayer() != null) {
                                        Player player = context.getSource().getPlayer();
                                        MutableComponent message = Component.empty();
                                        for (int i = 0; i < quests.size(); i++) {
                                            TCRQuestManager.Quest quest = quests.get(i);
                                            // 构建带悬停文本的键组件
                                            Component keyComponent = Component.literal("[" + quest.getKey() + "]")
                                                    .withStyle(style -> style
                                                            .withColor(ChatFormatting.GREEN)
                                                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                                                    quest.getTitle().copy()
                                                                            .append(": ")
                                                                            .append(quest.getShortDesc()))));
                                            message = message.append(keyComponent);
                                            if (i < quests.size() - 1) {
                                                message = message.append(Component.literal(", ").withStyle(ChatFormatting.GREEN));
                                            }
                                        }
                                        player.displayClientMessage(message, false);
                                    }
                                    return 0;
                                })
                        )
                )
        );
    }

}
