package org.lushplugins.regrowthvanish.listener;

import com.destroystokyo.paper.event.player.PlayerAdvancementCriterionGrantEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.raid.RaidTriggerEvent;
import org.lushplugins.regrowthvanish.util.EventUtil;

public class PlayerListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onAdvancementCriterionGrant(PlayerAdvancementCriterionGrantEvent event) {
        EventUtil.ifVanishedCancel(event.getPlayer(), event);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBreakHangingEntity(HangingBreakByEntityEvent event) {
        if (event.getRemover() instanceof Player player) {
            EventUtil.ifVanishedCancel(player, event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBucketFill(PlayerBucketFillEvent event) {
        EventUtil.ifVanishedCancel(event.getPlayer(), event);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDrop(PlayerDropItemEvent event) {
        EventUtil.ifVanishedCancel(event.getPlayer(), event);
    }

    @EventHandler(ignoreCancelled = true)
    public void onFoodChange(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player player) {
            EventUtil.ifVanishedCancel(player, event);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPickupItem(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player player) {
            EventUtil.ifVanishedCancel(player, event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPickupArrow(PlayerPickupArrowEvent event) {
        EventUtil.ifVanishedCancel(event.getPlayer(), event);
    }

    @EventHandler(ignoreCancelled = true)
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        EventUtil.ifVanishedCancel(event.getPlayer(), event);
    }

    @EventHandler(ignoreCancelled = true)
    public void onShear(PlayerShearEntityEvent event) {
        EventUtil.ifVanishedCancel(event.getPlayer(), event);
    }

    @EventHandler(ignoreCancelled = true)
    public void onRaidTrigger(RaidTriggerEvent event) {
        EventUtil.ifVanishedCancel(event.getPlayer(), event);
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityBlockForm(EntityBlockFormEvent event) {
        if (event.getEntity() instanceof Player player) {
            EventUtil.ifVanishedCancel(player, event);
        }
    }
}
