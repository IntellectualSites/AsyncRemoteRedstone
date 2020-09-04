package me.orlaando.ARR.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Lightable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractionListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(final PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        if (block.getType().equals(Material.REDSTONE_LAMP)) {
            event.setCancelled(true);

            Lightable lightable = (Lightable) block.getBlockData();
            lightable.setLit(!lightable.isLit());
            block.setBlockData(lightable);
        }
    }
}