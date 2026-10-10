package org.lushplugins.regrowthvanish.listener;

import com.destroystokyo.paper.event.entity.PhantomPreSpawnEvent;
import com.destroystokyo.paper.event.entity.PlayerNaturallySpawnCreaturesEvent;
import com.destroystokyo.paper.event.entity.ProjectileCollideEvent;
import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockReceiveGameEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityMountEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.lushplugins.regrowthvanish.RegrowthVanish;
import org.lushplugins.regrowthvanish.util.EventUtil;

public class EntityListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player player && RegrowthVanish.getInstance().getVanishCache().contains(player)) {
            event.setCancelled(true);
        }

        if (event instanceof EntityDamageByEntityEvent damageByEntityEvent) {
            Entity damager = damageByEntityEvent.getDamager();
            Player player = null;
            if (damager instanceof Player) {
                player = (Player) damager;
            } else if (damager instanceof Projectile projectile) {
                if ((projectile.getShooter() != null) && (projectile.getShooter() instanceof Player)) {
                    player = (Player) projectile.getShooter();
                }
            }

            if (player != null && RegrowthVanish.getInstance().getVanishCache().contains(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityTarget(EntityTargetEvent event) {
        if ((event.getTarget() instanceof Player player)) {
            EventUtil.ifVanishedCancel(player, event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onVehicleDestroy(VehicleDestroyEvent event) {
        if (event.getAttacker() instanceof Player player) {
            EventUtil.ifVanishedCancel(player, event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onMount(EntityMountEvent event) {
        if (event.getMount() instanceof Player player) {
            EventUtil.ifVanishedCancel(player, event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onNaturalSpawn(PlayerNaturallySpawnCreaturesEvent event) {
        if (RegrowthVanish.getInstance().getVanishCache().contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPhantom(PhantomPreSpawnEvent event) {
        if (event.getSpawningEntity() instanceof Player player) {
            EventUtil.ifVanishedCancel(player, event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPickupExperience(PlayerPickupExperienceEvent event) {
        EventUtil.ifVanishedCancel(event.getPlayer(), event);
    }

    @EventHandler(ignoreCancelled = true)
    public void onProjectileCollide(ProjectileCollideEvent event) {
        if (event.getCollidedWith() instanceof Player player) {
            EventUtil.ifVanishedCancel(player, event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockReceiveGameEvent(BlockReceiveGameEvent event) {
        if (event.getEntity() instanceof Player player) {
            EventUtil.ifVanishedCancel(player, event);
        }
    }
}
