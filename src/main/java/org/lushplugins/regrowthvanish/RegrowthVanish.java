package org.lushplugins.regrowthvanish;

import org.bukkit.entity.Player;
import org.lushplugins.lushlib.utils.plugin.SpigotPlugin;
import org.lushplugins.regrowthvanish.command.VanishCommand;
import org.lushplugins.regrowthvanish.config.ConfigManager;
import org.lushplugins.regrowthvanish.listener.*;
import org.lushplugins.regrowthvanish.vanish.VanishCache;
import org.lushplugins.regrowthvanish.vanish.VanishHandler;
import revxrsal.commands.bukkit.BukkitLamp;

public final class RegrowthVanish extends SpigotPlugin {
    private static RegrowthVanish plugin;

    private final VanishCache vanishCache = new VanishCache();
    private ConfigManager configManager;
    private VanishHandler vanishHandler;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        this.configManager = new ConfigManager();
        this.configManager.reload();

        this.vanishHandler= new VanishHandler(this);

        registerListeners(
            new ChatListener(),
            new EntityListener(),
            new FakeChestListener(),
            new PlayerConnectionListener(),
            new PlayerListener(),
            new ServerPingListener()
        );

        BukkitLamp.builder(this)
            .build()
            .register(new VanishCommand());
    }

    @Override
    public void onDisable() {
        for (Player player : getServer().getOnlinePlayers()) {
            for (Player otherPlayer : getServer().getOnlinePlayers()) {
                if (player != null && otherPlayer != null && !player.equals(otherPlayer)) {
                    player.showPlayer(this, otherPlayer);
                }
            }
        }
    }

    public VanishCache getVanishCache() {
        return vanishCache;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public VanishHandler getVanishHandler() {
        return vanishHandler;
    }

    public static RegrowthVanish getInstance() {
        return plugin;
    }
}
