package org.plugin.hardcraft.Boss.Dragon;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DragonListener implements Listener {

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof EnderDragon) {
            EnderDragon dragon = (EnderDragon)event.getEntity();
            DragonProjectile.startTask(dragon);
            if (dragon.getAttribute(Attribute.GENERIC_MAX_HEALTH) == null) {
                return;
            }

            AttributeInstance instance = dragon.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            if (instance.getValue() != 2000.0) {
                instance.setBaseValue(2000.0);
                dragon.setHealth(2000.0);
            }
        }

    }

    @EventHandler
    public void GiveDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof EnderDragon) {
            EnderDragon dragon = (EnderDragon)e.getDamager();
            if (dragon.getAttribute(Attribute.GENERIC_MAX_HEALTH) == null) {
                return;
            }

            double maxHealth = dragon.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            if (maxHealth >= dragon.getHealth() * 2.0) {
                if (dragon.getHealth() + e.getDamage() / 2.0 >= maxHealth) {
                    return;
                }

                dragon.setHealth(dragon.getHealth() + e.getDamage() / 2.0);
            }
        }

    }

    @EventHandler
    public void EnderEnter(PlayerPortalEvent e) {
        Player p = e.getPlayer();
        PlayerInventory pi = p.getInventory();
        Location toLocation = e.getTo();
        World world = toLocation.getWorld();

        if (pi.contains(Material.NETHER_STAR)) {
            pi.removeItemAnySlot(new ItemStack(Material.NETHER_STAR, 1));
            return;
        }

        if (world.getEnvironment() == World.Environment.THE_END) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, Integer.MAX_VALUE, 0, true, false));
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1, true, false));
        }

    }

    @EventHandler
    public void GetDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof EnderDragon) {
            EnderDragon dragon = (EnderDragon)e.getEntity();
            World world = dragon.getWorld();

            DragonProjectile.startTask(dragon);

            if (dragon.getAttribute(Attribute.GENERIC_MAX_HEALTH) == null) {return;}

            double maxHealth = dragon.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

            if (maxHealth < dragon.getHealth() * 2.0) {e.setDamage(e.getDamage() * 2);}

            if (maxHealth >= dragon.getHealth() * 2.0) {
                world.spawnEntity(e.getDamager().getLocation(), EntityType.ZOMBIE);
                world.spawnEntity(e.getDamager().getLocation(), EntityType.ZOMBIE);
            }

            int random;
            if (maxHealth >= dragon.getHealth() * 4.0) {
                world.spawnEntity(e.getDamager().getLocation(), EntityType.SHULKER);
                world.spawnEntity(e.getDamager().getLocation(), EntityType.VEX);
                random = (int)(Math.random() * 5.0);
                if (random == 2) {
                    world.spawnEntity(e.getDamager().getLocation(), EntityType.RAVAGER);
                }
            }

            if (maxHealth >= dragon.getHealth() * 10.0) {
                EnderBOOM.startTask(dragon);
                if (e.getDamager() instanceof LivingEntity) {
                    LivingEntity damager = (LivingEntity)e.getDamager();
                    if (damager instanceof Arrow) {
                        Arrow arrow = (Arrow)damager;
                        if (arrow.getShooter() instanceof LivingEntity) {
                            damager = (LivingEntity)arrow.getShooter();
                        }
                    }

                    random = (int)(Math.random() * 8.0);
                    if (random == 5) {
                        e.setCancelled(true);
                        damager.damage(e.getDamage());
                    }

                    damager.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 200, 4));
                    damager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 600, 4));
                    damager.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 1200, 4));
                    damager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 6000, 0));
                    int random2 = (int)(Math.random() * 10.0);
                    if (random2 == 5) {
                        damager.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 30));
                    }
                }
            }

            if (maxHealth >= dragon.getHealth() * 100.0 && e.getDamager() instanceof Player) {
                Player p = (Player)e.getDamager();
                p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 600, 0));
                p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 600, 2));
            }
        }

    }

    @EventHandler
    public void NoDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof EnderDragon) {
            EnderDragon dragon = (EnderDragon)e.getEntity();
            if (dragon.getLocation().getNearbyEntitiesByType(EnderCrystal.class, 300.0).iterator().hasNext()) {
                e.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void CrystalDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof EnderCrystal) {
            if (e.getDamager() instanceof Player) {
                Player p = (Player)e.getDamager();
                p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 600, 2));
                p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 600, 2));
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 600, 0));
            } else if (e.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow)e.getDamager();
                if (arrow.getShooter() instanceof Player) {
                    Player p = (Player)arrow.getShooter();
                    p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 600, 2));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 600, 2));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 600, 0));
                }
            }
        }

    }

    @EventHandler
    public void DragonDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof EnderDragon) {
            if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {e.setCancelled(true);}
            if (e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {e.setCancelled(true);}
        }
    }

    @EventHandler
    public void Death(EntityDeathEvent e) {
        if (e.getEntity() instanceof EnderDragon) {
            EnderDragon dragon = (EnderDragon) e.getEntity();
            Location location = dragon.getLocation();
            Location amount = new Location(location.getWorld(), 0.0, -5.0, 0.0);

            for(int i = 0; i <= 10; i++) {
                location.createExplosion(100.0F, true, true);
                location.add(amount);
            }
        }

    }

    @EventHandler
    public void Fireball(EntitySpawnEvent e) {
        if (e.getEntity() instanceof AreaEffectCloud) {
            AreaEffectCloud effect = (AreaEffectCloud) e.getEntity();

            if (effect.getSource() instanceof EnderDragon) {effect.setDuration(60);}

        }
    }

}
