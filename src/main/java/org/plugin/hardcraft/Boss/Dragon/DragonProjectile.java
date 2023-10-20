package org.plugin.hardcraft.Boss.Dragon;

import org.bukkit.Location;
import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.EnderDragon;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.plugin.hardcraft.HardCraft;

public class DragonProjectile extends BukkitRunnable {

    private final EnderDragon dragon;
    private static boolean running = false;

    public DragonProjectile(EnderDragon dragon) {
        this.dragon = dragon;
    }

    @Override
    public void run() {

        if (dragon == null || dragon.isDead()) {
            cancel();
            return;
        }

        Location dragonLocation = dragon.getLocation();

        DragonFireball fireball = dragon.launchProjectile(DragonFireball.class, dragonLocation.getDirection());
        fireball.setIsIncendiary(false);
        fireball.setYield(2.0F);

    }

    public static void startTask(EnderDragon dragon) {
        if (!running) {
            running = true;
            DragonProjectile task = new DragonProjectile(dragon);
            task.runTaskTimer(JavaPlugin.getPlugin(HardCraft.class), 0L, 1L);
        }
    }


}

