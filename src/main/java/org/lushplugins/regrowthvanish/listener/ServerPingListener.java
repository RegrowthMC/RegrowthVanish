package org.lushplugins.regrowthvanish.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.lushplugins.regrowthvanish.RegrowthVanish;
import org.lushplugins.regrowthvanish.vanish.VanishCache;

import java.util.Iterator;

public class ServerPingListener implements Listener {

    @EventHandler
    public void ping(ServerListPingEvent event) {
        Iterator<Player> players;
        try {
            players = event.iterator();
        } catch (UnsupportedOperationException e) {
            return;
        }

        VanishCache vanishCache = RegrowthVanish.getInstance().getVanishCache();
        Player player;
        while (players.hasNext()) {
            player = players.next();
            if (vanishCache.contains(player)) {
                players.remove();
            }
        }
    }
}
