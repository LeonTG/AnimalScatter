package com.leontg77.animalscatter;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

/**
 * Utilities class.
 * 
 * @author LeonTG77
 */
public class Utils {
	private static Random rand = new Random();
	
	/**
	 * Get a random location in the given world.
	 * 
	 * @param world the world tp get it in.
	 * 
	 * @return A random locations.
	 */
	public static Location findRandomLocation(World world) {
		int radius = ((((int) world.getWorldBorder().getSize()) / 2) - 1);
		
		int x = rand.nextInt(radius * 2) - radius;
		int z = rand.nextInt(radius * 2) - radius;

		Location loc = new Location(world, x + 0.5, 0, z + 0.5);

		double y = highestTeleportableYAtLocation(loc);
		loc.setY(y + 2);
		
		return loc;
	}
	
	/**
     * Checks for the highest non-air block with 2 air blocks above it.
     *
     * @param location the location whose X,Z coordinates are used
     * @return -1 if no valid location found, otherwise coordinate with non-air Y coord with 2 air blocks above it
	 * 
	 * @author ghowden
     */
    public static int highestTeleportableYAtLocation(Location location) {
        Location startingLocation = location.clone();
        startingLocation.setY(location.getWorld().getMaxHeight());

        boolean above2WasAir = false;
        boolean aboveWasAir = false;
        Block currentBlock = startingLocation.getBlock();

        while (currentBlock.getY() >= 0) {
            if (currentBlock.getType() != Material.AIR) {
                if (above2WasAir && aboveWasAir) {
                    return currentBlock.getY();
                }

                above2WasAir = aboveWasAir;
                aboveWasAir = false;
            } else {
                above2WasAir = aboveWasAir;
                aboveWasAir = true;
            }

            currentBlock = currentBlock.getRelative(BlockFace.DOWN);
        }

        return -1;
    }
}