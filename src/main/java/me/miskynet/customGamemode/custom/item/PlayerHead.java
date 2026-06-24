package me.miskynet.customGamemode.custom.item;

import com.destroystokyo.paper.profile.PlayerProfile;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;
import java.util.UUID;

/**
 * The {@link PlayerHead} is a type {@link Item}. It is used
 * to create a player head with a custom texture.
 * */
public class PlayerHead extends Item {

    private String textureHash;

    /*
    * constructor
    * */
    public PlayerHead(String textureHash) {
        this(null, null, 1, null, textureHash);
    }

    public PlayerHead(Component displayName, String textureHash) {
        this(displayName, null, 1, null, textureHash);
    }

    public PlayerHead(Component displayName, List<Component> lore, String textureHash) {
        this(displayName, lore, 1, null, textureHash);
    }

    public PlayerHead(Component displayName, List<Component> lore, int amount, String textureHash) {
        this(displayName, lore, amount, null, textureHash);
    }

    public PlayerHead(Component displayName, List<Component> lore, int amount, CustomModelDataComponent customModelDataComponent, String textureHash) {
        super(Material.PLAYER_HEAD, displayName, lore, amount, customModelDataComponent);
        this.textureHash = textureHash;
    }

    /**
     * Set the texture for the {@link PlayerHead}
     *
     * @return {@link PlayerHead}
     * */
    public PlayerHead setTexture(String textureHash) {
        this.textureHash = textureHash;
        return this;
    }

    /**
     * Convert the {@link PlayerHead} into an {@link ItemStack}
     *
     * @return {@link ItemStack}
     * */
    public ItemStack toItemStack() {

        ItemStack headItem = super.toItemStack();

        if (headItem == null || !(headItem.getItemMeta() instanceof SkullMeta)) {
            return headItem;
        }

        SkullMeta skullMeta = (SkullMeta) headItem.getItemMeta();

        PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
        PlayerTextures textures = profile.getTextures();

        try {
            String fullUrl = "https://textures.minecraft.net/texture/" + this.textureHash;
            textures.setSkin(URI.create(fullUrl).toURL());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Fehler beim Erstellen der URL", e);
        }

        profile.setTextures(textures);
        skullMeta.setPlayerProfile(profile);
        headItem.setItemMeta(skullMeta);

        return headItem;
    }
}
