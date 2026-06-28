package me.miskynet.customGamemode.custom.scoreboard;

import io.papermc.paper.scoreboard.numbers.NumberFormat;
import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.utils.Utils;
import me.miskynet.customGamemode.custom.config.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;

public class ScoreboardManager {

    Scoreboard scoreboard;

    /**
     * Creates the scoreboard
     *
     * @param player The Scoreboard the player is for
     * */
    public void createScoreboard(Player player) {
        org.bukkit.scoreboard.ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();

        int repeat = 5;
        Component scoreboardTitle = Utils.component(" ".repeat(repeat) + "&6Custom Gamemode" + " ".repeat(repeat));
        Objective objective = scoreboard.registerNewObjective("sidePanel",Criteria.DUMMY, scoreboardTitle);

        int rowSize = getRows(player).size();
        int finalSize = rowSize;
        for (String row : getRows(player)) {

            if (row.equals(" ")) {
                row = row.repeat(finalSize);
            }

            Score score = objective.getScore(row);
            score.setScore(finalSize);

            score.numberFormat(NumberFormat.blank());

            objective.getScore(row).setScore(finalSize);
            finalSize--;
        }

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        player.setScoreboard(scoreboard);
        this.scoreboard = scoreboard;
    }

    /**
     * Gets the current scoreboard
     *
     * @return {@link Scoreboard}
     * */
    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    /**
     * Gets the rows of the scoreboard
     *
     * @return Rows as a {@link ArrayList}
     * */
    private ArrayList<String> getRows(Player player) {

        ArrayList<String> scores = new ArrayList<>();

        scores.add(Utils.coloredString(" "));
        scores.add(Utils.coloredString("&7Your Profile: "));
        scores.add(Utils.coloredString("  &6" + player.getName()));
        scores.add(Utils.coloredString(" "));
        scores.add(Utils.coloredString("&7Your Balance:"));
        scores.add(Utils.coloredString("&7  &6" + Main.economyManager.getDisplayFormat(Main.economyManager.getBalance(player)) + Main.economyManager.getEcoSymbol()));
        scores.add(Utils.coloredString(" "));

        int repeat = 7;
        String centerIP = " ".repeat(repeat) + "&8michigames.net";
        scores.add(Utils.coloredString(centerIP));

        return scores;
    }

    /**
     * Updates the scoreboard so changes like balance will be visible
     * @param player {@link Player} the scoreboard should be updated for
     * */
    public void updateScoreboard(Player player) {

        if (!((Boolean) PlayerData.get(PlayerData.FileType.SETTINGS, player.getUniqueId(), "settings.scoreboardStatus"))) {
            player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
            return;
        }

        Scoreboard scoreboard = player.getScoreboard();
        org.bukkit.scoreboard.ScoreboardManager manager = Bukkit.getScoreboardManager();

        if (scoreboard == manager.getMainScoreboard() || scoreboard.getObjective("sidePanel") == null) {
            scoreboard = manager.getNewScoreboard();
        }

        Objective objective = scoreboard.getObjective("sidePanel");
        if (objective == null) {
            int repeat = 5;
            Component scoreboardTitle = Utils.component(" ".repeat(repeat) + "&6&lCustom Gamemode" + " ".repeat(repeat));
            objective = scoreboard.registerNewObjective("sidePanel", Criteria.DUMMY, scoreboardTitle);
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        }

        ArrayList<String> rows = getRows(player);
        int finalSize = rows.size();
        ArrayList<String> newEntries = new ArrayList<>();

        for (String row : rows) {
            if (row.equals(" ")) {
                row = row.repeat(finalSize + 1);
            }

            Score score = objective.getScore(row);
            score.setScore(finalSize);
            score.numberFormat(NumberFormat.blank());

            newEntries.add(row);
            finalSize--;
        }

        for (String oldEntry : scoreboard.getEntries()) {
            if (!newEntries.contains(oldEntry)) {
                scoreboard.resetScores(oldEntry);
            }
        }

        if (player.getScoreboard() != scoreboard) {
            player.setScoreboard(scoreboard);
        }
        this.scoreboard = scoreboard;
    }

    /**
     * Runs the updates for the scoreboard
     * */
    public void runUpdates() {

        new BukkitRunnable() {
            @Override
            public void run() {

                Bukkit.getOnlinePlayers().forEach(player -> {
                    updateScoreboard(player);
                });
            }
        }.runTaskTimer(Main.getInstance(), 0, 20);

    }


}
