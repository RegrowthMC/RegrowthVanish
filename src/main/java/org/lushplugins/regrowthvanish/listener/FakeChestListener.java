package org.lushplugins.regrowthvanish.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import org.lushplugins.regrowthvanish.RegrowthVanish;
import org.lushplugins.regrowthvanish.util.EventUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FakeChestListener implements Listener {
    private final Set<UUID> haveFakeInventoryOpen = new HashSet<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (
            event.getAction() == Action.RIGHT_CLICK_BLOCK
                && event.getClickedBlock() != null
                && event.getClickedBlock().getState() instanceof Container container
                && !haveFakeInventoryOpen.contains(uuid)
                && !player.isSneaking()
                && RegrowthVanish.getInstance().getVanishCache().contains(uuid)
        ) {
            Inventory inventory;
            if (container.getInventory() instanceof DoubleChestInventory) {
                inventory = RegrowthVanish.getInstance().getServer().createInventory(player, 54, Component.text("Silently opened inventory"));
            } else {
                inventory = RegrowthVanish.getInstance().getServer().createInventory(player, container.getInventory().getType(), Component.text("Silently opened inventory"));
            }

            inventory.setContents(container.getInventory().getContents());
            haveFakeInventoryOpen.add(uuid);
            player.sendMessage(Component.text()
                .content("Opening chest silently (cannot modify contents)")
                .color(TextColor.fromHexString("#b7faa2"))
                .build());

            player.openInventory(inventory);
            event.setCancelled(true);
        } else {
            EventUtil.ifVanishedCancel(player, event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (haveFakeInventoryOpen.contains(event.getWhoClicked().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClose(InventoryCloseEvent event) {
        haveFakeInventoryOpen.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        haveFakeInventoryOpen.remove(event.getPlayer().getUniqueId());
    }
}
