package org.plugin.hardcraft.Boss.Withers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class WitherListener implements Listener {

    @EventHandler
    public void Spawn(EntitySpawnEvent e) {
        if (e.getEntity() instanceof Wither) {
            Wither wither = (Wither) e.getEntity();
            Location location = wither.getLocation();
            World world = wither.getWorld();

            if (world.getEnvironment() != World.Environment.NORMAL) {
                wither.remove();
                Component remove = Component.text("사실 위더는 오버월드에서만 소환 가능하답니다~");
                Component add = Component.text("추신 : 재료 안돌려줌 ㅋ");
                world.sendMessage(remove);
                world.sendMessage(add);
            }

            Component component = Component.text(
                    "경고! x: " + location.getX() +
                            " y: " + location.getY() +
                            " z: " + location.getZ() +
                            " " + location.getWorld().getEnvironment() + "에 위더 소환됨!"
            );
            world.sendMessage(component);
        }


    }

    @EventHandler
    public void NoDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Wither) {
            if (e.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {e.setCancelled(true);}
            if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {e.setCancelled(true);}
            if (e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {e.setCancelled(true);}
        }
    }

    @EventHandler
    public void Damage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Wither) {
            Wither wither = (Wither) e.getEntity();

            WitherProjectile.startTask(wither);

            wither.getWorld().spawnEntity(e.getDamager().getLocation(), EntityType.WITHER_SKELETON);

            Location location = e.getDamager().getLocation();
            location.createExplosion(3);

        }

    }

    @EventHandler
    public void Death(EntityDeathEvent e) {
        if (e.getEntity() instanceof Wither) {
            World world = e.getEntity().getWorld();
            world.spawnEntity(e.getEntity().getLocation(), EntityType.WITHER);

            Component component = Component.text("위더 스켈레톤 해골 여러 개 얻기 번거로우셨죠? ^^", Style.style(TextDecoration.ITALIC.withState(false)));
            world.sendMessage(component);
            Component component1 = Component.text("이제 위더가 무한 리필이 됩니다~ ^^", Style.style(TextDecoration.ITALIC.withState(false)));
            world.sendMessage(component1);
            Component component2 =
                    Component.text("[ 포기하기 ]", Style.style(TextDecoration.ITALIC.withState(false),
                            TextColor.color(255, 0, 0)))
                            .hoverEvent(HoverEvent.showText(
                                    Component.text("그딴거 없음 ^^",
                                            Style.style(TextDecoration.ITALIC.withState(false), TextColor.color(255, 0, 0)))));
            world.sendMessage(component2);

        }
    }




}
