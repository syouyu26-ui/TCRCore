package com.p1nero.tcrcore.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.capability.TCRQuestManager;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;

/**
 * 调试命令，用来调试指定任务
 */
public class TCRDebugCommand {

    private static final SuggestionProvider<CommandSourceStack> QUEST_SUGGESTIONS = (context, builder) -> {
        TCRQuestManager.getAllQuests().forEach(quest -> {
            Component message = quest.getTitle().copy()
                    .append(": ")
                    .append(quest.getShortDesc());
            builder.suggest(quest.getKey(), message);
        });
        return builder.buildFuture();
    };

    private static int executeAddQuest(CommandContext<CommandSourceStack> context, ServerPlayer target) {
        String questId = StringArgumentType.getString(context, "quest_id");
        TCRQuestManager.Quest quest = TCRQuestManager.getQuestByKey(questId);
        if (quest != null) {
            TCRQuestManager.startQuest(target, quest);
            context.getSource().sendSuccess(() ->
                            Component.literal("Quest added to " + target.getName().getString() + "!")
                                    .withStyle(ChatFormatting.GREEN),
                    false
            );
        } else {
            context.getSource().sendFailure(
                    Component.literal("Quest is null!").withStyle(ChatFormatting.RED)
            );
        }
        return 0;
    }

    private static int executeFinishQuest(CommandContext<CommandSourceStack> context, ServerPlayer target) {
        String questId = StringArgumentType.getString(context, "quest_id");
        TCRQuestManager.Quest quest = TCRQuestManager.getQuestByKey(questId);
        if (quest != null) {
            if (TCRQuestManager.finishQuest(target, quest, true)) {
                context.getSource().sendSuccess(() ->
                                Component.literal("Quest removed from " + target.getName().getString() + "!")
                                        .withStyle(ChatFormatting.GREEN),
                        false
                );
            } else {
                context.getSource().sendFailure(
                        Component.literal("Failed to finish quest (maybe not started?)").withStyle(ChatFormatting.RED)
                );
            }
        } else {
            context.getSource().sendFailure(
                    Component.literal("Quest is null!").withStyle(ChatFormatting.RED)
            );
        }
        return 0;
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(TCRCoreMod.MOD_ID).requires((commandSourceStack) -> commandSourceStack.hasPermission(2))
                .then(Commands.literal("debug")
                        .then(Commands.literal("addQuest")
                                .then(Commands.argument("quest_id", StringArgumentType.string())
                                        .suggests(QUEST_SUGGESTIONS)
                                        .executes((context) -> {
                                            ServerPlayer sender = context.getSource().getPlayer();
                                            if (sender == null) {
                                                context.getSource().sendFailure(
                                                        Component.literal("You must be a player to use this command without a player parameter.")
                                                );
                                                return 0;
                                            }
                                            return executeAddQuest(context, sender);
                                        })
                                )
                        )
                        .then(Commands.literal("finishQuest")
                                .then(Commands.argument("quest_id", StringArgumentType.string())
                                        .suggests(QUEST_SUGGESTIONS)
                                        .executes((context) -> {
                                            ServerPlayer sender = context.getSource().getPlayer();
                                            if (sender == null) {
                                                context.getSource().sendFailure(
                                                        Component.literal("You must be a player to use this command without a player parameter.")
                                                );
                                                return 0;
                                            }
                                            return executeFinishQuest(context, sender);
                                        })
                                )
                        )
                        .then(Commands.argument("player", EntityArgument.player())
                                .then(Commands.literal("addQuest")
                                        .then(Commands.argument("quest_id", StringArgumentType.string())
                                                .suggests(QUEST_SUGGESTIONS)
                                                .executes((context) -> {
                                                    ServerPlayer target = EntityArgument.getPlayer(context, "player");
                                                    return executeAddQuest(context, target);
                                                })
                                        )
                                )
                                .then(Commands.literal("finishQuest")
                                        .then(Commands.argument("quest_id", StringArgumentType.string())
                                                .suggests(QUEST_SUGGESTIONS)
                                                .executes((context) -> {
                                                    ServerPlayer target = EntityArgument.getPlayer(context, "player");
                                                    return executeFinishQuest(context, target);
                                                })
                                        )
                                )
                        )
                        .then(Commands.literal("listQuestIds")
                                .executes(context -> {
                                    List<TCRQuestManager.Quest> quests = TCRQuestManager.getAllQuests().stream().toList();
                                    if (context.getSource().getPlayer() != null) {
                                        ServerPlayer player = context.getSource().getPlayer();
                                        MutableComponent message = Component.empty();
                                        for (int i = 0; i < quests.size(); i++) {
                                            TCRQuestManager.Quest quest = quests.get(i);
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