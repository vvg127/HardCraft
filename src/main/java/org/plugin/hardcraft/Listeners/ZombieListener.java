package org.plugin.hardcraft.Listeners;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ZombieListener implements Listener {

    @EventHandler
    public void PlayerDamage(EntityDamageByEntityEvent e) {

        if (e.getEntity() instanceof Player) {

            Player p = (Player)e.getEntity();
            World world = p.getWorld();
            PotionEffect potion = new PotionEffect(PotionEffectType.CONFUSION, 400, 1);

            if (e.getDamager() instanceof Zombie) {

                Zombie z = (Zombie)e.getDamager();
                Location l = z.getLocation();
                p.addPotionEffect(potion);
                world.spawnEntity(l, EntityType.ZOMBIE);

            }

        }

    }

    @EventHandler
    public void Damage(EntityDamageEvent e) {

        if (e.getEntity() instanceof Zombie) {

            Zombie z = (Zombie)e.getEntity();
            z.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 5));
            AttributeInstance instance = z.getAttribute(Attribute.GENERIC_ARMOR);

            if (instance == null) {
                return;
            }

            instance.setBaseValue(instance.getBaseValue() + 2.0);
        }
    }

    @EventHandler
    public void Death(EntityDeathEvent e) {
        if (e.getEntity() instanceof Zombie) {
            if (e.getEntity().getKiller() == null) {
                return;
            }

            e.getEntity().getKiller().addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 200, 2));
        }

    }

}
