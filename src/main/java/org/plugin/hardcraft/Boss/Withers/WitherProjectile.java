package org.plugin.hardcraft.Boss.Withers;

import org.bukkit.Location;
import org.bukkit.entity.Wither;
import org.bukkit.entity.WitherSkull;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.plugin.hardcraft.HardCraft;

public class WitherProjectile extends BukkitRunnable {

    private static boolean isWorking = false;

    private final Wither wither;

    public WitherProjectile(Wither wither) {
        this.wither = wither;
    }

    @Override
    public void run() {

        if (wither == null || wither.isDead()) {
            cancel();
            return;
        }

        Location witherLocation = wither.getLocation();

        WitherSkull witherHead = wither.launchProjectile(WitherSkull.class, witherLocation.getDirection());
        witherHead.setCharged(true);
        witherHead.setYield(2.0F);

    }

    public static void startTask(Wither wither) {
        if (isWorking) {return;}
        isWorking = true;
        WitherProjectile task = new WitherProjectile(wither);
        task.runTaskTimer(JavaPlugin.getPlugin(HardCraft.class), 0L, 2L);
    }

}
