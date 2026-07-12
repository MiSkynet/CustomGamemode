package me.miskynet.customGamemode.custom.item;

import me.miskynet.customGamemode.utils.ComponentUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The {@link Relic} is a type of {@link Item}. It is a
 * custom, mythical item. No one really knows what it is or what it does
 * */
public class Relic extends Item {

    private final float purity;

    /*
     * constructor
     * */

    public Relic() {
        this(getRandomPurity());
    }

    /**
     * @param purity The purity of a {@link Relic}. The Value can be between 0.1 and 5.0,
     * where 5.0 is the highest/best value.
     * */
    public Relic(float purity) {
        super(Material.AMETHYST_SHARD, ComponentUtils.component(false, "§dAntic Relic"));
        this.purity = purity;

        super.setEnchantmentGlint(true);

        ArrayList<Component> lore = new ArrayList<>();
        lore.add(ComponentUtils.component(" "));
        lore.add(ComponentUtils.component("§7Purity: " + getPurity()));
        lore.add(ComponentUtils.component(" "));
        lore.add(ComponentUtils.component("§8Antic relic's are remains from"));
        lore.add(ComponentUtils.component("§8long before the player was a thing."));
        lore.add(ComponentUtils.component("§8No one knows, how exactly these were"));
        lore.add(ComponentUtils.component("§8created, but one is for sure, they"));
        lore.add(ComponentUtils.component("§8contain a great power within them..."));

        super.setLore(lore);
    }

    /*
     * setter, getter and everything else
     * */

    /**
     * Gets the purity of a {@link Relic}
     *
     * @return Purity as {@link Float}
     * */
    public Float getPurity() {
        return this.purity;
    }

    /**
     * Gets a random purity for the {@link Relic}
     *
     * @return Purity as {@link Float}
     * */
    private static Float getRandomPurity() {

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
