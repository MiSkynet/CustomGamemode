package me.miskynet.customGamemode.custom.item;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
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

    /*
     * setter, getter and everything else
     * */
    public PlayerHead setTexture(String textureHash) {
        this.textureHash = textureHash;
        return this;
    }

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
