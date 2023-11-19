package org.plugin.hardcraft.Listeners;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.SculkShrieker;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Warden;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class WardenListener implements Listener {

    @EventHandler
    public void WardenDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Warden) {
            if (e.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {e.setCancelled(true);}
            if (e.getCause() == EntityDamageEvent.DamageCause.POISON) {e.setCancelled(true);}
            if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {e.setCancelled(true);}
            if (e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {e.setCancelled(true);}
        }
    }

    @EventHandler
    public void WardenAttack(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Warden) {
            Warden warden = (Warden) e.getDamager();

            if (e.getEntity() instanceof Player) {
                Player p = (Player) e.getEntity();

                p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION,300,2));
                warden.setAbsorptionAmount(warden.getAbsorptionAmount() + e.getFinalDamage() / 2);
                warden.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,1200,1));

                Block block = p.getLocation().getBlock();
                block.setType(Material.SCULK_SHRIEKER);
                SculkShrieker data = (SculkShrieker) block.getBlockData();
                data.setCanSummon(true);
                block.setBlockData(data);

                if (p.getLevel() != 0) {
                    p.setLevel(p.getLevel() - 1);

                    AttributeInstance instance = warden.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
                    if (instance == null) {return;}
                    instance.setBaseValue(instance.getBaseValue() + 1);

                }

            }
        }
    }

    @EventHandler
    public void WardenDeath(EntityDeathEvent e) {
        if (e.getEntity() instanceof Warden) {
            Warden warden = (Warden) e.getEntity();

            if (warden.getKiller() != null) {
                Player p = warden.getKiller();
                PlayerInventory pi = p.getInventory();

                ItemStack item = new ItemStack(Material.ENCHANTED_BOOK,1);
                EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();

                meta.addStoredEnchant(Enchantment.DAMAGE_ALL,7,true);
                meta.addStoredEnchant(Enchantment.ARROW_DAMAGE,7,true);
                meta.addStoredEnchant(Enchantment.DURABILITY,5,true);
                meta.addStoredEnchant(Enchantment.VANISHING_CURSE,1,true);
                meta.addStoredEnchant(Enchantment.LOOT_BONUS_BLOCKS,5,true);
                meta.addStoredEnchant(Enchantment.DIG_SPEED,7,true);
                meta.addStoredEnchant(Enchantment.THORNS,10,true);
                meta.addStoredEnchant(Enchantment.BINDING_CURSE,1,true);

                item.setItemMeta(meta);
                pi.addItem(item);

            }


        }
    }

}
