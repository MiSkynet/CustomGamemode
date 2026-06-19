package me.miskynet.customGamemode.custom.economy;

import me.miskynet.customGamemode.utils.customConfig.CustomConfig;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.UUID;

public class EconomyManager {

    HashMap<UUID, Double> playerBalance = new HashMap<>();

    String ecoSymbol = "$";

    public EconomyManager() {
        syncBalanceMapWithConfig();
    }

    /**
     * Get the balance of a player
     * @param player Player whose balance should be got
     * @return Player balance as double
     * */
    public Double getBalance(Player player) {
        if (playerBalance.get(player.getUniqueId()) != null) {
            return playerBalance.get(player.getUniqueId());
        }else {
            return null;
        }
    }

    /**
     * Set the balance of a player
     * @param player Player whose balance should be changed
     * @param value New balance
     * */
    public void setBalance(Player player, Double value) {
        playerBalance.put(player.getUniqueId(), value);
        syncConfigWithUniquePlayer(player);
    }

    /**
     * Add an amount to a player Balance
     * @param player Player whose balance should be changed
     * @param value Value that should be added
     * */
    public void addBalance(Player player, Double value) {
        playerBalance.put(player.getUniqueId(), playerBalance.get(player.getUniqueId()) + value);
        syncConfigWithBalanceMap();
    }

    /**
     * Transfer an amount from one player to another
     * @param sender Player that send the amount
     * @param target Player that receives the amount
     * @param value Amount that is being transferred
     * */
    public void transfer(Player sender, Player target, Double value) {
        this.addBalance(sender, value * -1);
        this.addBalance(target, value);
    }

    /**
     * Load all balances into the {@link #playerBalance} HashMap <br>
     * Only used when starting the plugin!
     * */
    private void syncBalanceMapWithConfig() {
        for (String uuid : CustomConfig.get("economy/playerData.yml").getConfigurationSection("").getKeys(false)) {
            playerBalance.put(UUID.fromString(uuid), (Double) CustomConfig.get("economy/playerData.yml").get(uuid + ".balance"));
        }
    }

    /**
     * Sync a specific player Balance in the config with the {@link #playerBalance} HashMap
     * @param player Whose player balance should be saved in the config
     * */
    public void syncConfigWithUniquePlayer(Player player) {
        CustomConfig.set("economy/playerData.yml", player.getUniqueId() + ".balance", playerBalance.get(player.getUniqueId()));
    }

    /**
     * Sync the player balances in the config with the {@link #playerBalance}
     * */
    public void syncConfigWithBalanceMap() {
        for (UUID uuid : playerBalance.keySet()) {
            CustomConfig.set("economy/playerData.yml", uuid.toString() + ".balance", playerBalance.get(uuid));
        }
    }

    /**
     * Get the economy symbol for texts
     * @return String of the Symbol
     * */
    public String getEcoSymbol() {
        return ecoSymbol;
    }

    /**
     * Format a players balance into a more readable format
     * @param player Players balance that should be formated
     * */
    public String getBalanceDisplayFormat(Player player) {

        double balance = this.getBalance(player);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        DecimalFormat formatter = new DecimalFormat("#,##0.00", symbols);

        String formatedNumber = formatter.format(balance);

        return formatedNumber;
    }

}
