package me.miskynet.customGamemode.custom.item;

import me.miskynet.customGamemode.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Relic extends Item {

    private final float purity;

    /*
     * constructor
     * */

    public Relic() {
        this(getRandomPurity());
    }

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

    private static float getRandomPurity() {

        int randomInt = ThreadLocalRandom.current().nextInt(1, 100);
        float randomFloat = 0;

        if (randomInt < 2) randomFloat = (float) ThreadLocalRandom.current().nextDouble(4.0, 5.0);
        else if (randomInt < 8) randomFloat = (float) ThreadLocalRandom.current().nextDouble(3.0, 4.0);
        else if (randomInt < 25) randomFloat = (float) ThreadLocalRandom.current().nextDouble(2.0, 3.0);
        else if (randomInt < 50) randomFloat = (float) ThreadLocalRandom.current().nextDouble(1.0, 2.0);
        else if (randomInt < 100) randomFloat = (float) ThreadLocalRandom.current().nextDouble(0.1, 1.0);

        float purity = Math.round(randomFloat * 10.0f) / 10.0f;

        return purity;
    }

}
