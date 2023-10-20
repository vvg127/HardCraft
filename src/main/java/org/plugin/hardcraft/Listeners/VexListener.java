package org.plugin.hardcraft.Listeners;

import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Vex;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class VexListener implements Listener {

    @EventHandler
    public void Death(EntityDeathEvent e) {
        if (!(e.getEntity() instanceof Vex)) {
            World world = e.getEntity().getWorld();
            world.spawnEntity(e.getEntity().getLocation(), EntityType.VEX);
        }

    }


}
