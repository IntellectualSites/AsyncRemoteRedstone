package me.orlaando.ARR.listeners;

import me.orlaando.ARR.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Lightable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractionListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteract(final PlayerInteractEvent event) {
        
        Block block = event.getClickedBlock();
        if (block == null) return;
        Bukkit.broadcastMessage("event fired");

        if (block.getType().equals(Material.PISTON)) {
            Bukkit.broadcastMessage("type is piston");
            Main.getNmsAdapter().extendPiston(event.getClickedBlock());
            event.setCancelled(true);
        }
    }
}