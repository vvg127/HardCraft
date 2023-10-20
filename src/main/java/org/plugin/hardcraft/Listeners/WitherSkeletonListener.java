package org.plugin.hardcraft.Listeners;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class WitherSkeletonListener implements Listener {

    @EventHandler
    public void PlayerDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof WitherSkeleton) {
            WitherSkeleton wither = (WitherSkeleton)e.getDamager();
            wither.setAbsorptionAmount(4.0);
            if (e.getEntity() instanceof Player) {
                Player p = (Player)e.getEntity();
                PlayerInventory pi = p.getInventory();
                pi.addItem(new ItemStack(Material.STONE_SWORD), new ItemStack(Material.STONE_SWORD), new ItemStack(Material.STONE_SWORD), new ItemStack(Material.STONE_SWORD), new ItemStack(Material.STONE_SWORD), new ItemStack(Material.STONE_SWORD), new ItemStack(Material.STONE_SWORD), new ItemStack(Material.STONE_SWORD), new ItemStack(Material.STONE_SWORD));
            }
        }

    }

    @EventHandler
    public void Damage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof WitherSkeleton) {
            WitherSkeleton wither = (WitherSkeleton) e.getEntity();

            if (e.getDamager() instanceof Player) {
                Player p = (Player) e.getDamager();
                PlayerInventory pi = p.getInventory();

                if (pi.containsAtLeast(new ItemStack(Material.COAL), 1)) {
                    pi.remove(Material.COAL);
                    p.setFireTicks(200);
                    wither.setAbsorptionAmount(wither.getAbsorptionAmount() + 8);
                }

            }

        }

    }

    @EventHandler
    public void Death(EntityDeathEvent e) {
        if (e.getEntity() instanceof WitherSkeleton) {

            World world = e.getEntity().getWorld();
            world.spawnEntity(e.getEntity().getLocation(), EntityType.SKELETON);

        }

    }


}
