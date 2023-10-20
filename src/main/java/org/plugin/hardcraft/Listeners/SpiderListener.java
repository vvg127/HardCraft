package org.plugin.hardcraft.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpiderListener implements Listener {

    @EventHandler
    public void PlayerDamage(EntityDamageByEntityEvent e) {

        if (e.getEntity() instanceof Player) {

            Player p = (Player)e.getEntity();
            PotionEffect potion = new PotionEffect(PotionEffectType.SLOW_DIGGING, 1200, 2);
            PotionEffect potion2 = new PotionEffect(PotionEffectType.JUMP, 1200, 250);

            if (e.getDamager() instanceof Spider) {
                p.teleport(p.getLocation().subtract(0.0, 3.0, 0.0));
                p.addPotionEffect(potion);
                p.addPotionEffect(potion2);
            }

        }

    }

}
