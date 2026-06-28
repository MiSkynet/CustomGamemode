package me.miskynet.customGamemode.custom.entity.npc;

import com.destroystokyo.paper.profile.PlayerProfile;
import io.papermc.paper.datacomponent.item.ResolvableProfile;
import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.custom.menu.Menu;
import me.miskynet.customGamemode.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mannequin;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * The {@link NPC} is an {@link Entity} with the {@link EntityType} {@link Mannequin}.
 * It will have a custom name, no gravity, is invulnerable and silent
 * */
public class NPC {

    Component name;
    Location location;
    UUID uuid = null;
    InteractType interactType = null;
    ResolvableProfile resolvableProfile;

    public static NamespacedKey interactTypeKey = new NamespacedKey(Main.getInstance(), "interacttype");

    /**
     * This enum contains all the available types of Interact Menus that
     * an NPC can hold
     * */
    public enum InteractType {
        MENU,
        TEXTURE_MENU,
        SHOP,
        SETTINGS
    }

    /**
     * Creates a new {@link NPC}
     * @param name The name of the {@link NPC}
     * @param location The {@link Location} where the {@link NPC} should be spawned at
     * */
    public NPC(Component name, Location location) {
        this.name = name;
        this.location = location;
    }

    /**
     * Summons an NPC into the set location
     * */
    public void spawn() {
        World world = location.getWorld();
        Entity entity = Bukkit.getWorld(world.getKey()).spawnEntity(location, EntityType.MANNEQUIN);
        entity.setGravity(false);
        entity.setInvulnerable(true);
        entity.setSilent(true);
        entity.setCustomNameVisible(true);
        entity.customName(Utils.component("&dShop NPC"));

        Mannequin mannequin = (Mannequin) entity;
        mannequin.setDescription(null);
        mannequin.setProfile(this.resolvableProfile);

        this.uuid = entity.getUniqueId();

        if (this.interactType != null) {
            entity.getPersistentDataContainer().set(interactTypeKey, PersistentDataType.STRING, interactType.toString());
        }
    }

    /**
     * Sets an {@link InteractType} that is being used when interacted with the {@link NPC}
     * @param interactType The {@link InteractType} that will be opened when right-clicking the {@link NPC}
     * */
    public void setInteractMenu(InteractType interactType) {
        this.interactType = interactType;
    }

    /**
     * Gets the interact menu of the current npc
     * @return {@link Menu}
     * */
    public InteractType getInteractMenu() {
        return this.interactType;
    }

    /**
     * Gets the {@link UUID} of an entity
     * @return {@link UUID}
     * */
    public UUID getUuid() {
        return this.uuid;
    }

    public void setResolvableProfile(ResolvableProfile resolvableProfile) {
        this.resolvableProfile = resolvableProfile;
    }

    /**
     * Sets the skin texture of the {@link NPC}
     *
     * @param textureHash The texture hash of the skin
     * */
    public NPC setSkinHash(String textureHash) {
        PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
        PlayerTextures textureUrl = profile.getTextures();
        URL url;
        try {
            url = new URL("https://textures.minecraft.net/texture/" + textureHash);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        textureUrl.setSkin(url);
        profile.setTextures(textureUrl);
        ResolvableProfile resolvableProfile = ResolvableProfile.resolvableProfile(profile);
        this.setResolvableProfile(resolvableProfile);
        return this;
    }
}
