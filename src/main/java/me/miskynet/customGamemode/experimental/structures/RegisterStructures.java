package me.miskynet.customGamemode.experimental.structures;

import me.miskynet.customGamemode.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.structure.Structure;
import org.bukkit.structure.StructureManager;

import java.io.InputStream;

public class RegisterStructures {

    /**
     * Private function that registers a structure in the {@link StructureManager}
     *
     * @param key The key to register the structure with
     * @param filePath The path to the structure file in the resources folder
     * */
    private static void registerStructure(String key, String filePath) {
        StructureManager structureManager = Main.getInstance().getServer().getStructureManager();
        NamespacedKey Namespacedkey = new NamespacedKey(Main.getInstance(), key);

        try (InputStream is = Main.getInstance().getResource(filePath)) {
            if (is != null) {
                Structure structure = structureManager.loadStructure(is);
                structureManager.registerStructure(Namespacedkey, structure);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Registers all structures in the {@link StructureManager}
     * */
    public static void registerStructures() {
        // overworld structures
        registerStructure("test_structure", "structures/my_structure.nbt");
    }

}
