package org.lushplugins.regrowthvanish.util;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;
import org.lushplugins.regrowthvanish.RegrowthVanish;

public class EventUtil {

    /**
     * @return whether the event was canceled
     */
    public static boolean ifVanishedCancel(@NotNull Player player, Cancellable event) {
        if (RegrowthVanish.getInstance().getVanishCache().contains(player)) {
            event.setCancelled(true);
            return true;
        } else {
            return false;
        }
    }
}
