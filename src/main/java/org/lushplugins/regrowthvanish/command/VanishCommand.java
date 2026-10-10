package org.lushplugins.regrowthvanish.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.lushplugins.regrowthvanish.RegrowthVanish;
import org.lushplugins.regrowthvanish.vanish.VanishCache;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Optional;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@SuppressWarnings("unused")
@Command("vanish")
public class VanishCommand {

    @Command("vanish")
    @CommandPermission("regrowthvanish.vanish")
    public void vanish(BukkitCommandActor actor, @Optional Boolean vanish) {
        Player player = actor.requirePlayer();
        boolean shouldVanish = vanish != null ? vanish : !RegrowthVanish.getInstance().getVanishCache().contains(player);

        if (shouldVanish) {
            RegrowthVanish.getInstance().getVanishHandler().vanish(player);

            actor.sender().sendMessage(Component.text()
                .content("You are now vanished")
                .color(TextColor.fromHexString("#b7faa2"))
                .build());
        } else {
            RegrowthVanish.getInstance().getVanishHandler().unvanish(player);

            actor.sender().sendMessage(Component.text()
                .content("You are no longer vanished")
                .color(TextColor.fromHexString("#b7faa2"))
                .build());
        }
    }

    @Command("unvanish")
    public void unvanish(BukkitCommandActor actor) {
        vanish(actor, false);
    }

    @Subcommand("list")
    @CommandPermission("regrowthvanish.list")
    public void list(CommandSender sender) {
        TextComponent.Builder list = Component.text()
            .content("Vanished: ")
            .color(TextColor.fromHexString("#b7faa2"))
            .appendNewline();

        boolean first = true;
        VanishCache vanishCache = RegrowthVanish.getInstance().getVanishCache();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player != null && vanishCache.contains(player)) {
                if (!first) {
                    list.append(Component.text()
                        .content(", ")
                        .color(TextColor.fromHexString("#b7faa2")));
                } else {
                    first = false;
                }

                list.append(Component.text()
                    .content(player.getName())
                    .color(TextColor.fromHexString("#b7faa2")));
            }
        }

        sender.sendMessage(list);
    }

    @Subcommand("reload")
    @CommandPermission("regrowthvanish.reload")
    public void reload(CommandSender sender) {
        RegrowthVanish.getInstance().getConfigManager().reload();

        sender.sendMessage(Component.text()
            .content("RegrowthVanish reloaded!")
            .color(TextColor.fromHexString("#b7faa2"))
            .build());
    }
}
