package org.plugin.hardcraft;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.plugin.hardcraft.Boss.Dragon.DragonListener;
import org.plugin.hardcraft.Boss.Withers.WitherListener;
import org.plugin.hardcraft.Listeners.*;

public final class HardCraft extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this,this);
        getServer().getPluginManager().registerEvents(new ZombieListener(),this);
        getServer().getPluginManager().registerEvents(new SkeletonListener(),this);
        getServer().getPluginManager().registerEvents(new CreeperListener(),this);
        getServer().getPluginManager().registerEvents(new EnderManListener(),this);
        getServer().getPluginManager().registerEvents(new SpiderListener(),this);
        getServer().getPluginManager().registerEvents(new BlazeListener(this),this);
        getServer().getPluginManager().registerEvents(new PiglinListener(),this);
        getServer().getPluginManager().registerEvents(new WitherSkeletonListener(),this);
        getServer().getPluginManager().registerEvents(new DragonListener(),this);
        getServer().getPluginManager().registerEvents(new VillagerListener(),this);
        getServer().getPluginManager().registerEvents(new VexListener(),this);
        getServer().getPluginManager().registerEvents(new WitchListener(this),this);
        getServer().getPluginManager().registerEvents(new WitherListener(),this);
        getServer().getPluginManager().registerEvents(new WardenListener(),this);

        System.out.println("난이도 증가됨");


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("난이도 감소됨");
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent e) {
        Player p = e.getPlayer();
        PlayerInventory pi = p.getInventory();

        if (e.getItem().getType() == Material.MILK_BUCKET) {
            e.setCancelled(true);
            pi.removeItemAnySlot(new ItemStack(Material.MILK_BUCKET));
            pi.addItem(new ItemStack(Material.BUCKET));

            int random = (int) (Math.random() * 6);

            if (random == 0) {
                p.sendMessage("맛없어");
            } else if (random == 1) {
                p.sendMessage("우웩");
            } else if (random == 2) {
                p.sendMessage("( 하지만 아무 일도 일어나지 않았다 )");
            } else if (random == 3) {
                p.sendMessage("배부르다");
            } else if (random == 4) {
                p.sendMessage("나 유당불내증 있어");
            } else if (random == 5) {
                p.sendMessage("우유에서 막 짜낸 우유는 세균이 많다... 고 한다");
            }

        }
    }

    @EventHandler
    public void Duplicate(BlockBreakEvent e) {
        Block block = e.getBlock();
        Material material = block.getType();

        Location location = block.getLocation();

        int random = (int) (Math.random() * 10);
        if (random != 3) {return;}

        if (material == Material.DEEPSLATE_DIAMOND_ORE || material == Material.DIAMOND_ORE || material == Material.ANCIENT_DEBRIS) {

            int random1 = (int) (Math.random() * 2);
            int random2 = (int) (Math.random() * 3);

            int amount;

            if (random1 == 0) {amount = 1;} else {amount = -1;}

            if (random2 == 0) {
                location.setX(location.getX() + amount);
            } else if (random2 == 1) {
                location.setY(location.getY() + amount);
            } else {location.setZ(location.getZ() + amount);}

            location.getBlock().setType(material);

        }

    }

    @EventHandler
    public void Confuse(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        if (p.hasPotionEffect(PotionEffectType.CONFUSION)) {
            PotionEffect potion = p.getPotionEffect(PotionEffectType.CONFUSION);

            if (potion == null) {return;}

            if (potion.getAmplifier() == 30) {
                Vector change = e.getTo().toVector();

                Vector location = e.getFrom().toVector();

                Vector amount = change.subtract(location).multiply(-2);

                World world = p.getWorld();

                p.teleport(location.add(amount).toLocation(world));

            }

        }

    }


}
