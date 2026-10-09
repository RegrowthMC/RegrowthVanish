package org.lushplugins.regrowthvanish;

import org.bukkit.plugin.java.JavaPlugin;

public final class RegrowthVanish extends JavaPlugin {
    private static RegrowthVanish plugin;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        // Enable implementation
    }

    @Override
    public void onDisable() {
        // Disable implementation
    }

    public static RegrowthVanish getInstance() {
        return plugin;
    }
}
