package me.orlaando.ARR.adapters;

import net.minecraft.server.v1_16_R2.*;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Piston;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class mc1_16_2 implements NMSAdapter {

    @Override public void extendPiston(Block piston) {
        Bukkit.broadcastMessage("extend piston method called");
        World world = ((CraftWorld)piston.getWorld()).getHandle();
        BlockPosition blockPosition = new BlockPosition(piston.getX(),  piston.getY(), piston.getZ());
        IBlockData iBlockData = world.getType(blockPosition);
        BlockPiston blockPiston = (BlockPiston) iBlockData.getBlock();

        try {
            Method method = blockPiston.getClass().getDeclaredMethod("a", world.getClass(), blockPosition.getClass(), EnumDirection.class, boolean.class);
            method.setAccessible(true);
            Piston p = (Piston) piston.getBlockData();
            p.setExtended(true);
            piston.setBlockData(p);

            BlockFace bf = ((Directional) piston.getBlockData()).getFacing();

            boolean result = (boolean) method.invoke(world, blockPosition, getDirection(bf), true);
            if (result) {
                Bukkit.broadcastMessage("true");
            } else {
                Bukkit.broadcastMessage("false");
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
        }
    }

    private EnumDirection getDirection(BlockFace bf) {
        switch (bf) {
            case UP:
                return EnumDirection.UP;
            case DOWN:
                return EnumDirection.DOWN;
            case NORTH:
                return EnumDirection.NORTH;
            case EAST:
                return EnumDirection.EAST;
            case SOUTH:
                return EnumDirection.SOUTH;
            case WEST:
                return EnumDirection.WEST;
        }
        return null;
    }
}
