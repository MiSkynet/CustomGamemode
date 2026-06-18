package me.miskynet.customGamemode.custom.economy;

import me.miskynet.customGamemode.utils.customConfig.CustomConfig;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class EconomyManager {

    HashMap<UUID, Double> playerBalance = new HashMap<>();

    String ecoSymbol = "$";

    public EconomyManager() {
        syncBalanceMapWithConfig();
    }

    private void syncBalanceMapWithConfig() {
        for (String uuid : CustomConfig.get("economy/playerData.yml").getConfigurationSection("").getKeys(false)) {
            playerBalance.put(UUID.fromString(uuid), (Double) CustomConfig.get("economy/playerData.yml").get(uuid + ".balance"));
        }
    }

    public Double getBalance(Player player) {
        if (playerBalance.get(player.getUniqueId()) != null) {
            return playerBalance.get(player.getUniqueId());
        }else {
            return null;
        }
    }

    public void setBalance(Player player, Double value) {
        playerBalance.put(player.getUniqueId(), value);
        syncConfigWithUniquePlayer(player);
    }

    public void addBalance(Player player, Double value) {
        playerBalance.put(player.getUniqueId(), playerBalance.get(player.getUniqueId()) + value);
        syncConfigWithBalanceMap();
    }

    public void transfer(Player sender, Player target, Double value) {
        this.addBalance(sender, value * -1);
        this.addBalance(target, value);
    }

    public void syncConfigWithUniquePlayer(Player player) {
        CustomConfig.set("economy/playerData.yml", player.getUniqueId() + ".balance", playerBalance.get(player.getUniqueId()));
    }

    public void syncConfigWithBalanceMap() {
        for (UUID uuid : playerBalance.keySet()) {
            CustomConfig.set("economy/playerData.yml", uuid.toString() + ".balance", playerBalance.get(uuid));
        }
    }

    public String getEcoSymbol() {
        return ecoSymbol;
    }

    public String getBalanceDisplayFormat(Player player) {

        Double balance = this.getBalance(player);

        if (balance > 999_999 && balance < 1_000_000) {

        }

        return "";
    }

}
