package me.miskynet.customGamemode.experimental.structures.overworld;

import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.utils.Debugger;
import org.bukkit.Chunk;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.structure.Structure;
import org.bukkit.structure.StructureManager;

import java.util.List;
import java.util.Random;

public class CustomTestStructure extends BlockPopulator {

    /**
     * Places the structure in the world with a specific chance
     * */
    @Override
    public void populate(World world, Random random, Chunk source) {

        if (random.nextInt(100) >= 5) {
            return;
        }

        int x = source.getX() * 16 + random.nextInt(16);
        int z = source.getZ() * 16 + random.nextInt(16);

        int y = world.getHighestBlockYAt(x, z);

        // check if the biome is allowed
        Biome biome = world.getBiome(x, y, z);
        List<Biome> allowedBiomes = List.of(Biome.PLAINS, Biome.FOREST, Biome.DESERT);

        Debugger.log("Current Biome: " + biome.getKey());

        if (!allowedBiomes.contains(biome)) return;

        StructureManager structureManager = Main.getInstance().getServer().getStructureManager();

        NamespacedKey key = new NamespacedKey(Main.getInstance(), "my_structure");
        Structure structure = structureManager.getStructure(key);

        if (structure != null) {
            Debugger.log("Created structure at: " + x + ", " + y + ", " + z);
            structure.place(world.getBlockAt(x, y, z).getLocation(), true, StructureRotation.NONE, Mirror.NONE, 0, 1.0f, random);
        }else {
            Debugger.log("Structure is null, cannot place structure.");
        }
    }

}
