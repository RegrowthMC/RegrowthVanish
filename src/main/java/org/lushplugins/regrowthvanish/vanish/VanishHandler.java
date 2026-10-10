package org.lushplugins.regrowthvanish.vanish;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.lushplugins.regrowthvanish.RegrowthVanish;

public class VanishHandler {
    private static final NamespacedKey VANISH_STATE_KEY = new NamespacedKey("vanish", "state");
    private static final BossBar BOSS_BAR = BossBar.bossBar(Component.text()
        .content("You are vanished")
        .color(TextColor.fromHexString("#4bfa38"))
        .build(),  1, BossBar.Color.GREEN, BossBar.Overlay.NOTCHED_12);

    private final RegrowthVanish plugin;
    private final ShowPlayerHandler showPlayers;

    public VanishHandler(RegrowthVanish plugin) {
        this.plugin = plugin;
        this.showPlayers = new ShowPlayerHandler();
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, showPlayers, 4, 4);
    }

    public boolean isVanished(Player player) {
        return player.getPersistentDataContainer().getOrDefault(VANISH_STATE_KEY, PersistentDataType.BOOLEAN, false);
    }

    public void vanish(Player player) {
        player.getPersistentDataContainer().set(VANISH_STATE_KEY, PersistentDataType.BOOLEAN, true);
        plugin.getVanishCache().addPlayer(player.getUniqueId());
        player.setSleepingIgnored(true);
        BOSS_BAR.addViewer(player);

        for (Entity entity : player.getNearbyEntities(70, 70, 70)) {
            if (entity instanceof Creature creature && creature.getTarget() != null && creature.getTarget().equals(player)) {
                creature.setTarget(null);
            }
        }

        for (Player otherPlayer : plugin.getServer().getOnlinePlayers()) {
            if (!otherPlayer.hasPermission("regrowthvanish.seevanished")) {
                if (otherPlayer.canSee(player)) {
                    otherPlayer.hidePlayer(plugin, player);
                }
            } else {
                otherPlayer.hidePlayer(plugin, player);
                this.showPlayers.add(new ShowPlayerHandler.Entry(otherPlayer, player));
            }
        }
    }

    public void unvanish(Player player) {
        player.getPersistentDataContainer().set(VANISH_STATE_KEY, PersistentDataType.BOOLEAN, false);
        plugin.getVanishCache().removePlayer(player.getUniqueId());
        player.setSleepingIgnored(false);
        BOSS_BAR.removeViewer(player);

        for (Player otherPlayer : plugin.getServer().getOnlinePlayers()) {
            if (otherPlayer.hasPermission("regrowthvanish.seevanished")) {
                otherPlayer.hidePlayer(plugin, player);
            }
            if (!otherPlayer.canSee(player)) {
                this.showPlayers.add(new ShowPlayerHandler.Entry(otherPlayer, player));
            }
        }
    }

    public void showVanished(Player player) {
        for (Player otherPlayer : plugin.getServer().getOnlinePlayers()) {
            if (plugin.getVanishCache().contains(otherPlayer) && !player.canSee(otherPlayer)) {
                this.showPlayers.add(new ShowPlayerHandler.Entry(player, otherPlayer));
            }
        }
    }

    public void hideVanished(Player player) {
        for (Player otherPlayer : plugin.getServer().getOnlinePlayers()) {
            if (!player.equals(otherPlayer) && plugin.getVanishCache().contains(otherPlayer) && player.canSee(otherPlayer)) {
                player.hidePlayer(plugin, otherPlayer);
            }
        }
    }

    public void resetSeeing(Player player) {
        if (player.hasPermission("regrowthvanish.seevanished")) {
            showVanished(player);
        } else {
            hideVanished(player);
        }
    }

    public void refresh(Player player) {
        resetSeeing(player);

        if (player.hasPermission("regrowthvanish.vanish")) {
            if (plugin.getVanishCache().contains(player)) {
                vanish(player);
            } else {
                unvanish(player);
            }
        } else if (plugin.getVanishCache().contains(player)) {
            unvanish(player);
        }
    }
}
