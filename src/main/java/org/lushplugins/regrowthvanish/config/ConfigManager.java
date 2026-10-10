package org.lushplugins.regrowthvanish.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.lushplugins.regrowthvanish.RegrowthVanish;

public class ConfigManager {

    public ConfigManager() {
        RegrowthVanish.getInstance().saveDefaultConfig();
    }

    public void reload() {
        RegrowthVanish.getInstance().reloadConfig();
        FileConfiguration config = RegrowthVanish.getInstance().getConfig();
    }
}
