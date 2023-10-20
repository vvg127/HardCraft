package org.plugin.hardcraft.Listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.SplashPotion;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.Witch;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.plugin.hardcraft.HardCraft;

public class WitchListener implements Listener {

    private final HardCraft plugin;

    public WitchListener(HardCraft plugin) {this.plugin = plugin;}

    @EventHandler
    public void Potion(EntityDamageEvent e) {
        if (e.getEntity() instanceof Witch) {
            if (e.getCause() == EntityDamageEvent.DamageCause.MAGIC) {
                e.setCancelled(true);
            }

            if (e.getCause() == EntityDamageEvent.DamageCause.POISON) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void Damage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Witch) {
            Witch witch = (Witch) e.getEntity();

            if (e.getDamager() instanceof Player) {

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

                        witch.launchProjectile(
                                SplashPotion.class,
                                witch.getVelocity(),
                                (ThrownPotion splash) -> {
                                    ItemStack item = new ItemStack(Material.SPLASH_POTION);
                                    PotionMeta meta = (PotionMeta) item.getItemMeta();

                                    meta.addCustomEffect(new PotionEffect(PotionEffectType.HARM, 1, 1), true);

                                    splash.setPotionMeta(meta);
                                    splash.setVelocity(witch.getVelocity().multiply(-1.5));
                            }

                        );

                        iteration++;

                    }
                };

                task.runTaskTimer(plugin, interval, interval);

            }

        }

    }


}
