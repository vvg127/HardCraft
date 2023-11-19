package org.plugin.hardcraft.Listeners;

import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Iterator;

public class CreeperListener implements Listener {

    @EventHandler
    public void PlayerDamage(EntityDamageByEntityEvent e) {

        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();


            if (e.getDamager() instanceof Creeper) {
                PlayerInventory pi = p.getInventory();
                World world = p.getWorld();

                ItemStack[] items = pi.getArmorContents();

                Creeper creeper = (Creeper) world.spawnEntity(e.getDamager().getLocation(), EntityType.CREEPER);
                creeper.setNoDamageTicks(100);
                creeper.setExplosionRadius(((Creeper) e.getDamager()).getExplosionRadius() * 2);

                int index = (int) (Math.random() * 4);

                if (items[index] == null) {
                    if (items[0] != null) {index = 0;}
                    if (items[3] != null) {index = 3;}
                    if (items[2] != null) {index = 2;}
                    if (items[1] != null) {index = 1;}
                }

                if (items[index] != null) {
                    ItemMeta meta = items[index].getItemMeta();

                    if (meta.hasEnchants()) {
                        Iterator<Enchantment> it = meta.getEnchants().keySet().iterator();
                        Enchantment enchant = it.next();
                        if (enchant == Enchantment.BINDING_CURSE) {
                            if (it.hasNext()) {
                                enchant = it.next();
                                meta.removeEnchant(enchant);
                            } else {meta.addEnchant(Enchantment.BINDING_CURSE,1,true);}
                        } else {meta.removeEnchant(enchant);}

                    } else {
                        meta.addEnchant(Enchantment.BINDING_CURSE,1,true);
                    }

                    items[index].setItemMeta(meta);

                } else {

                    ItemStack item2 = pi.getItemInMainHand();
                    ItemMeta meta = item2.getItemMeta();

                    if (meta.hasEnchants()) {
                        Iterator<Enchantment> it = meta.getEnchants().keySet().iterator();
                        Enchantment enchant = it.next();
                        if (enchant == Enchantment.VANISHING_CURSE) {
                            if (it.hasNext()) {
                                enchant = it.next();
                                meta.removeEnchant(enchant);
                            } else {meta.addEnchant(Enchantment.VANISHING_CURSE,1,true);}
                        } else {meta.removeEnchant(enchant);}
                    } else {
                        meta.addEnchant(Enchantment.VANISHING_CURSE,1,true);
                    }

                    item2.setItemMeta(meta);

                }

            }

        }

    }

    @EventHandler
    public void Damage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Creeper) {
            if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {e.setCancelled(true);}
            if (e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {e.setCancelled(true);}
        }
    }

}
