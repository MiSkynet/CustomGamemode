package me.miskynet.customGamemode.custom.scoreboard;

import io.papermc.paper.scoreboard.numbers.NumberFormat;
import me.miskynet.customGamemode.Main;
import me.miskynet.customGamemode.utils.Debugger;
import me.miskynet.customGamemode.utils.Utils;
import me.miskynet.customGamemode.utils.customConfig.PlayerSettings;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;

public class ScoreboardManager {

    Scoreboard scoreboard;

    /**
     * Create the scoreboard for the player
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

    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    private ArrayList<String> getRows(Player player) {

        ArrayList<String> scores = new ArrayList<>();

        scores.add(Utils.coloredString(" "));
        scores.add(Utils.coloredString("&7Your Profile: "));
        scores.add(Utils.coloredString("  &7MiSkynet"));
        scores.add(Utils.coloredString(" "));
        scores.add(Utils.coloredString("&7Your Balance:"));
        scores.add(Utils.coloredString("&7  &a" + Main.economyManager.getBalance(player).toString() + Main.economyManager.getEcoSymbol()));
        scores.add(Utils.coloredString(" "));


        int repeat = 7;
        String centerIP = " ".repeat(repeat) + "&8michigames.net";
        scores.add(Utils.coloredString(centerIP));

        return scores;
    }

    /**
     * update the scoreboard so changes like balance will be visible
     * */
    public void updateScoreboard(Player player) {

        if (!((Boolean) PlayerSettings.get(player, "settings.scoreboardStatus"))) {
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
            Component scoreboardTitle = Utils.component(" ".repeat(repeat) + "&6Custom Gamemode" + " ".repeat(repeat));
            objective = scoreboard.registerNewObjective("sidePanel", Criteria.DUMMY, scoreboardTitle);
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        }

        for (String entry : scoreboard.getEntries()) {
            scoreboard.resetScores(entry);
        }

        ArrayList<String> rows = getRows(player);
        int finalSize = rows.size();

        for (String row : rows) {
            if (row.equals(" ")) {
                row = row.repeat(finalSize);
            }

            Score score = objective.getScore(row);
            score.setScore(finalSize);

            score.numberFormat(NumberFormat.blank());

            objective.getScore(row).setScore(finalSize);
            finalSize--;
        }

        if (player.getScoreboard() != scoreboard) {
            player.setScoreboard(scoreboard);
        }
        this.scoreboard = scoreboard;
    }

    public void runUpdates() {

        new BukkitRunnable() {
            @Override
            public void run() {

                Bukkit.getOnlinePlayers().forEach(player -> {
                    updateScoreboard(player);
                });
            }
        }.runTaskTimer(Main.getInstance(), 0, 5);

    }


}
