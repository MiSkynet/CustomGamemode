package me.miskynet.customGamemode.custom.item;

import me.miskynet.customGamemode.custom.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

import java.util.ArrayList;

public class Relic extends Item {

    private final float purity;

    /*
     * constructor
     * */
    public Relic(float purity) {
        super(Material.AMETHYST_SHARD, Utils.component(false, "§dAntic Relic"));
        this.purity = purity;

        super.setEnchantmentGlint(true);

        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Utils.component(" "));
        lore.add(Utils.component("§7Purity: " + getPurity()));
        lore.add(Utils.component(" "));
        lore.add(Utils.component("§8Antic relic's are remains from"));
        lore.add(Utils.component("§8long before the player was a thing."));
        lore.add(Utils.component("§8No one knows, how exactly these were"));
        lore.add(Utils.component("§8created, but one is for sure, they"));
        lore.add(Utils.component("§8contain a great power within them..."));

        super.setLore(lore);
    }

    /*
     * setter, getter and everything else
     * */
    public float getPurity() {
        return this.purity;
    }

}
