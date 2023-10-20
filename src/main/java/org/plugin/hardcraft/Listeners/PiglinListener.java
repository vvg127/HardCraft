package org.plugin.hardcraft.Listeners;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Piglin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PiglinListener implements Listener {

    @EventHandler
    public void PiglinSpawn(EntitySpawnEvent e) {
        if (e.getEntity() instanceof Piglin) {
            Piglin piglin = (Piglin) e.getEntity();

            piglin.removeBarterMaterial(Material.ENDER_PEARL);
            piglin.removeMaterialOfInterest(Material.GOLD_INGOT);

        }
    }

    @EventHandler
    public void PlayerDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Piglin) {
            Piglin piglin = (Piglin) e.getDamager();

            piglin.setAbsorptionAmount(2);

            if (e.getEntity() instanceof Player) {
                Player p = (Player) e.getEntity();
                PlayerInventory pi = p.getInventory();

                if (pi.containsAtLeast(new ItemStack(Material.GOLD_INGOT),1)) {
                    pi.remove(Material.GOLD_INGOT);
                }

                if (pi.containsAtLeast(new ItemStack(Material.GOLD_BLOCK),1)) {
                    pi.remove(Material.GOLD_BLOCK);
                }

                if (pi.containsAtLeast(new ItemStack(Material.IRON_INGOT),1)) {
                    pi.remove(Material.IRON_INGOT);
                    pi.addItem(new ItemStack(Material.GOLD_INGOT));
                }

            }

        } else if (e.getDamager() instanceof Arrow) {
            Arrow arrow = (Arrow) e.getDamager();

            if (arrow.getShooter() instanceof Piglin) {
                Piglin piglin = (Piglin) arrow.getShooter();

                piglin.setAbsorptionAmount(2);

                if (e.getEntity() instanceof Player) {
                    Player p = (Player) e.getEntity();
                    PlayerInventory pi = p.getInventory();

                    if (pi.containsAtLeast(new ItemStack(Material.GOLD_INGOT),1)) {
                        pi.remove(Material.GOLD_INGOT);
                    }

                    if (pi.containsAtLeast(new ItemStack(Material.GOLD_BLOCK),1)) {
                        pi.remove(Material.GOLD_BLOCK);
                    }

                    if (pi.containsAtLeast(new ItemStack(Material.IRON_INGOT),1)) {
                        pi.remove(Material.IRON_INGOT);
                        pi.addItem(new ItemStack(Material.GOLD_INGOT));
                    }

                }
            }

        }

    }

}
