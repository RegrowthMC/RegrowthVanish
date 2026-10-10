package org.lushplugins.regrowthvanish.vanish;

import org.bukkit.entity.Player;
import org.lushplugins.regrowthvanish.RegrowthVanish;

import java.util.HashSet;
import java.util.Set;

public class ShowPlayerHandler implements Runnable {
    private final Set<Entry> entries = new HashSet<>();
    private final Set<Entry> next = new HashSet<>();

    public void add(Entry player) {
        this.entries.add(player);
    }

    @Override
    public void run() {
        RegrowthVanish plugin = RegrowthVanish.getInstance();
        for (Entry entry : this.next) {
            Player player = entry.player();
            Player target = entry.target();
            if (player.isOnline() && target.isOnline()) {
                player.showPlayer(plugin, target);
            }
        }

        this.next.clear();
        this.next.addAll(this.entries);
        this.entries.clear();
    }

    public record Entry(Player player, Player target) {}
}
