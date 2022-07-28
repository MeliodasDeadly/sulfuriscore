package fr.sulfuris.dev.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.*;

public class mainscoreboard {
    // const new scoreboard
    public static void setscoreboard(Player player) {

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("1", "2");
        objective.setDisplayName("3");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        player.setScoreboard(board);

    }
}