package org.lushplugins.regrowthvanish.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.LazyMetadataValue;
import org.lushplugins.regrowthvanish.RegrowthVanish;
import org.lushplugins.regrowthvanish.vanish.VanishCache;

import java.util.UUID;

public class PlayerConnectionListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoinEarly(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (RegrowthVanish.getInstance().getVanishHandler().isVanished(player)) {
            RegrowthVanish.getInstance().getVanishCache().addPlayer(uuid);
        }

        event.getPlayer().setMetadata("vanished", new LazyMetadataValue(
            RegrowthVanish.getInstance(),
            LazyMetadataValue.CacheStrategy.NEVER_CACHE,
            () -> RegrowthVanish.getInstance().getVanishCache().contains(uuid))
        );


        RegrowthVanish.getInstance().getVanishHandler().refresh(player);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoinLate(PlayerJoinEvent event) {
        if (RegrowthVanish.getInstance().getVanishCache().contains(event.getPlayer())) {
            event.joinMessage(null);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        VanishCache vanishCache = RegrowthVanish.getInstance().getVanishCache();
        if (vanishCache.contains(uuid)) {
            vanishCache.removePlayer(uuid);
            event.quitMessage(null);
        }
    }
}
