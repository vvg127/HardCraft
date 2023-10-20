package org.plugin.hardcraft.Listeners;

import io.papermc.paper.event.player.PlayerTradeEvent;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class VillagerListener implements Listener {

    @EventHandler
    public void Trade(PlayerTradeEvent e) {

        Player p = e.getPlayer();
        PlayerInventory pi = p.getInventory();

        if (pi.containsAtLeast(new ItemStack(Material.EMERALD), 1)) {

            int random = (int)(Math.random() * 10);

            if (random == 5) {
                pi.remove(new ItemStack(Material.EMERALD));
                e.getVillager().remove();
                p.spawnParticle(Particle.CLOUD, p.getLocation(), 4000, 5.0, 5.0, 5.0);
            }

        }

    }


}
