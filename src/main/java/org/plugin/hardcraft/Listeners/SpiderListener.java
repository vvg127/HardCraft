package org.plugin.hardcraft.Listeners;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
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
            Player p = (Player) e.getEntity();

            PotionEffect potion = new PotionEffect(PotionEffectType.SLOW_DIGGING, 1200, 1);
            PotionEffect potion2 = new PotionEffect(PotionEffectType.JUMP, 1200, 250);

            if (e.getDamager() instanceof Spider) {
                p.teleport(p.getLocation().subtract(0.0, 3.0, 0.0));
                p.addPotionEffect(potion);
                p.addPotionEffect(potion2);
            }
        }
    }

    @EventHandler
    public void SpiderDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Spider) {
            Spider spider = (Spider) e.getEntity();

            if (e.getDamager() instanceof Player) {
                Player p = (Player) e.getDamager();

                p.getLocation().getBlock().setType(Material.COBWEB);
                AttributeInstance instance = spider.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
                if (instance == null) {return;}
                instance.setBaseValue(instance.getBaseValue() * 2);

            }
        }
    }

}
