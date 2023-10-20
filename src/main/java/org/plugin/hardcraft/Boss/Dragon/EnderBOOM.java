package org.plugin.hardcraft.Boss.Dragon;

import org.bukkit.World;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.plugin.hardcraft.HardCraft;

public class EnderBOOM extends BukkitRunnable {

    private static boolean isWorking = false;

    private final EnderDragon dragon;

    public EnderBOOM(EnderDragon dragon) {
        this.dragon = dragon;
    }

    @Override
    public void run() {

        if (dragon == null || dragon.isDead()) {
            cancel();
            return;
        }

        World world = dragon.getWorld();

        TNTPrimed tnt = (TNTPrimed) world.spawnEntity(dragon.getLocation(), EntityType.PRIMED_TNT);
        tnt.setFuseTicks(200);
        tnt.setSource(dragon);
        tnt.setYield(8);

    }

    public static void startTask(EnderDragon dragon) {
        if (isWorking) {return;}
        isWorking = true;
        EnderBOOM task = new EnderBOOM(dragon);
        task.runTaskTimer(JavaPlugin.getPlugin(HardCraft.class), 0L, 20L);
    }

}

