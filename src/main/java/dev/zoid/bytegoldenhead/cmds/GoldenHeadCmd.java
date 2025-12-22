package dev.zoid.bytegoldenhead.cmds;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import dev.zoid.bytegoldenhead.item.GoldenHead;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import io.papermc.paper.plugin.lifecycle.event.registrar.ReloadableRegistrarEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class GoldenHeadCmd {

    public static void register(final @NotNull ReloadableRegistrarEvent<@NotNull Commands> event) {
        event.registrar().register(
                Commands.literal("golden_head")
                        .requires(source -> source.getSender().hasPermission("byte.goldenhead"))
                        .then(Commands.literal("give")
                                .then(Commands.argument("targets", ArgumentTypes.players())
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(1, 64))
                                                .executes(ctx -> {
                                                    final List<Player> targets = ctx.getArgument("targets", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource());
                                                    final int amount = IntegerArgumentType.getInteger(ctx, "amount");

                                                    for (final Player player : targets) {
                                                        if (player.getInventory().firstEmpty() != -1) {
                                                            final ItemStack item = GoldenHead.getItem();
                                                            item.setAmount(amount);
                                                            player.getInventory().addItem(item);
                                                        }
                                                    }
                                                    return Command.SINGLE_SUCCESS;
                                                }))
                                        .executes(ctx -> {
                                            final List<Player> targets = ctx.getArgument("targets", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource());

                                            for (final Player player : targets) {
                                                if (player.getInventory().firstEmpty() != -1) {
                                                    player.getInventory().addItem(GoldenHead.getItem());
                                                }
                                            }
                                            return Command.SINGLE_SUCCESS;
                                        })))
                        .build(),
                "Give golden heads",
                List.of("goldenhead", "gh")
        );
    }
}