package me.miskynet.customGamemode.custom.economy;

import me.miskynet.customGamemode.utils.customConfig.CustomConfig;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class EconomyManager {

    HashMap<UUID, Integer> playerBalance = new HashMap<>();

    public EconomyManager() {
        syncBalanceMapWithConfig();
    }

    private void syncBalanceMapWithConfig() {
        for (String uuid : CustomConfig.get("economy/playerData.yml").getConfigurationSection("").getKeys(false)) {
            playerBalance.put(UUID.fromString(uuid), (Integer) CustomConfig.get("economy/playerData.yml", uuid + ".balance"));
        }
    }

    public Integer getBalance(Player player) {
        if (playerBalance.get(player.getUniqueId()) != null) {
            return playerBalance.get(player.getUniqueId());
        }else {
            return null;
        }
    }

    public void setBalance(Player player, Integer value) {
        playerBalance.put(player.getUniqueId(), value);
        syncConfigWithUniquePlayer(player);
    }

    public void addBalance(Player player, Integer value) {
        playerBalance.put(player.getUniqueId(), playerBalance.get(player.getUniqueId()) + value);
        syncConfigWithBalanceMap();
    }

    public void syncConfigWithUniquePlayer(Player player) {
        CustomConfig.set("economy/playerData.yml", player.getUniqueId() + ".balance", playerBalance.get(player.getUniqueId()));
    }

    public void syncConfigWithBalanceMap() {
        for (UUID uuid : playerBalance.keySet()) {
            CustomConfig.set("economy/playerData.yml", uuid.toString() + ".balance", playerBalance.get(uuid));
        }
    }

}
