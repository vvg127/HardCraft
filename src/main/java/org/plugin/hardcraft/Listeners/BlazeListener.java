package org.plugin.hardcraft.Listeners;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.plugin.hardcraft.HardCraft;

public class BlazeListener implements Listener {

    private final HardCraft plugin;

    public BlazeListener(HardCraft plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void Explosive(EntityDamageEvent e) {
        if (e.getEntity() instanceof Blaze) {
            if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {e.setCancelled(true);}
        }
    }

    @EventHandler
    public void PlayerDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();

            if (e.getDamager() instanceof Blaze) {
                p.setFireTicks(60);
                Blaze blaze = (Blaze) e.getDamager();

                blaze.setAbsorptionAmount(blaze.getAbsorptionAmount() + 1);
                blaze.launchProjectile(LargeFireball.class, blaze.getVelocity());

                if (p.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
                    p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                }

            }

        } else if (e.getEntity() instanceof Arrow) {
            Arrow arrow = (Arrow) e.getEntity();
            if (arrow.getShooter() instanceof Player) {
                Player p = (Player) arrow.getShooter();

                if (e.getDamager() instanceof Blaze) {
                    Blaze blaze = (Blaze) e.getDamager();

                    blaze.setAbsorptionAmount(blaze.getAbsorptionAmount() + 1);
                    blaze.launchProjectile(LargeFireball.class, blaze.getVelocity());

                    if (p.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
                        p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                    }

                }
            }
        }

    }

    @EventHandler
    public void Damage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Blaze) {
            Blaze blaze = (Blaze) e.getEntity();

            blaze.lookAt(e.getDamager());

            int interval = 1;
            int totalIterations = 5;

            BukkitRunnable task = new BukkitRunnable() {
                int iteration = 0;

                @Override
                public void run() {
                    if (iteration >= totalIterations) {
                        cancel();
                        return;
                    }

                    blaze.launchProjectile(SmallFireball.class, blaze.getVelocity());

                    iteration++;

                }
            };

            task.runTaskTimer(plugin, interval, interval);

        }

    }



}
