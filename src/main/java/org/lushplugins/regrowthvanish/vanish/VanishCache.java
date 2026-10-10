package org.lushplugins.regrowthvanish.vanish;

import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class VanishCache {
    private final Set<UUID> vanishedPlayers = Collections.synchronizedSet(new HashSet<>());

    public Set<UUID> getVanishedPlayers() {
        return vanishedPlayers;
    }

    public boolean contains(UUID uuid) {
        return vanishedPlayers.contains(uuid);
    }

    public boolean contains(Player player) {
        return contains(player.getUniqueId());
    }

    public void addPlayer(UUID uuid) {
        vanishedPlayers.add(uuid);
    }

    public void removePlayer(UUID uuid) {
        vanishedPlayers.remove(uuid);
    }
}
