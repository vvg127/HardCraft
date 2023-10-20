package org.plugin.hardcraft.Listeners;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SkeletonListener implements Listener {

    @EventHandler
    public void PlayerDamage(EntityDamageByEntityEvent e) {

        if (e.getEntity() instanceof Player) {

            Player p = (Player)e.getEntity();
            PotionEffect potion = new PotionEffect(PotionEffectType.SLOW, 200, 2);

            if (e.getDamager() instanceof Arrow) {
                Arrow a = (Arrow)e.getDamager();

                if (a.getShooter() instanceof Skeleton) {

                    e.setDamage(e.getDamage() + (double)(p.getArrowsInBody() * 3));
                    Skeleton s = (Skeleton)a.getShooter();
                    Location l = p.getLocation().add(p.getLocation(), 0.0, 0.0, -1.0);
                    a.setKnockbackStrength(10);
                    p.addPotionEffect(potion);
                    s.teleport(l);
                    s.launchProjectile(a.getClass(), p.getVelocity());

                }

            }

        }

    }

    @EventHandler
    public void Damage(EntityDamageEvent e) {

        if (e.getEntity() instanceof Skeleton) {
            Skeleton s = (Skeleton)e.getEntity();
            s.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 600, 1));
        }

    }



}
