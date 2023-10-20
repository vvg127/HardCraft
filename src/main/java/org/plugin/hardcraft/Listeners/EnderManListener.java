package org.plugin.hardcraft.Listeners;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EnderManListener implements Listener {

    @EventHandler
    public void Spawn(EntitySpawnEvent e) {
        if (e.getEntity() instanceof Enderman) {
            Enderman ender = (Enderman) e.getEntity();
            ender.setScreaming(true);
        }
    }

    @EventHandler
    public void PlayerDamage(EntityDamageByEntityEvent e) {

        if (e.getEntity() instanceof Player) {

            Player p = (Player)e.getEntity();
            World world = p.getWorld();
            PotionEffect potion = new PotionEffect(PotionEffectType.INVISIBILITY, 200, 1);

            if (e.getDamager() instanceof Enderman) {

                Enderman ender = (Enderman)e.getDamager();

                if (ender.getAttribute(Attribute.GENERIC_MAX_HEALTH) == null) {return;}

                if (!(ender.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() <= ender.getHealth())) {
                    ender.setHealth(ender.getHealth() + 1.0);
                } else {
                    ender.setAbsorptionAmount(ender.getAbsorptionAmount() + 2.0);
                }

                Location pl = p.getLocation();
                ender.teleportRandomly();
                Location el = ender.getLocation();
                p.teleport(el);
                ender.teleport(pl);
                world.spawnEntity(el, EntityType.ENDERMITE);
                el.createExplosion(1.0F, false, false);
                ender.addPotionEffect(potion);

            }
        }

    }



}
