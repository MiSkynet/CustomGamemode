package me.miskynet.customGamemode.custom.enchantments.mole;

import me.miskynet.customGamemode.utils.Debugger;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class MoleListener implements Listener {

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {


    }

    @EventHandler
    public void rightClick(PlayerInteractEvent event) {

        if (!(event.getAction().isRightClick())) return;

        ItemStack itemInMainHand = event.getPlayer().getInventory().getItemInMainHand();

        // Check if the item has the Mole enchantment
        if (itemInMainHand == null || itemInMainHand.getType() == Material.AIR) return;

        NamespacedKey key = new NamespacedKey("cc", "mole");
        Enchantment enchantment = Enchantment.getByKey(key);

        if (enchantment == null || !itemInMainHand.containsEnchantment(enchantment)) {
            Debugger.log("Item does not have the 'Mole' enchantment.");
            return;
        }

        int enchantmentLevel = itemInMainHand.getEnchantmentLevel(enchantment);

        Location blockLoc = event.getClickedBlock().getLocation();

        // define the radius of the area based on the enchantment level
        int checkSquareOfRadius = enchantmentLevel * 2 + 2;

        int radius = checkSquareOfRadius / 2;
        int centerX = blockLoc.getBlockX();
        int centerY = blockLoc.getBlockY();
        int centerZ = blockLoc.getBlockZ();
        int radiusSquared = radius * radius;

        for (int currentlyCheckingX = centerX - radius; currentlyCheckingX <= centerX + radius; currentlyCheckingX++) {
            for (int currentlyCheckingY = centerY - radius; currentlyCheckingY <= centerY + radius; currentlyCheckingY++) {

                if (event.getPlayer().getY() > currentlyCheckingY) continue;

                for (int currentlyCheckingZ = centerZ - radius; currentlyCheckingZ <= centerZ + radius; currentlyCheckingZ++) {

                    int distX = currentlyCheckingX - centerX;
                    int distY = currentlyCheckingY - centerY;
                    int distZ = currentlyCheckingZ - centerZ;

                    int distSquared = (distX * distX) + (distY * distY) + (distZ * distZ);

                    if (distSquared <= radiusSquared) {

                        Random random = new Random();
                        int chance = random.nextInt(100);

                        Block block = blockLoc.getWorld().getBlockAt(currentlyCheckingX, currentlyCheckingY, currentlyCheckingZ);

                        // leave every ore block and ancient debris block untouched
                        if (block.getType().toString().contains("_ORE") || block.getType().toString().contains("ANCIENT_DEBRIS")) continue;

                        // set the block to air if the chance is greater than 10, else break it naturally (so drop it)
                        if (chance < 10)  block.breakNaturally();
                        else block.setType(Material.AIR);
                    }
                }
            }
        }
    }


}
