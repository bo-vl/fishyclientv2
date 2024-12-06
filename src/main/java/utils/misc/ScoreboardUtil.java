package utils.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;

import java.util.List;
import java.util.stream.Collectors;

public class ScoreboardUtil {
    public static List<String> getSidebarLines() {
        Minecraft mc = Minecraft.getMinecraft();
        Scoreboard scoreboard = mc.theWorld.getScoreboard();
        ScoreObjective objective = scoreboard.getObjectiveInDisplaySlot(1);
        if (objective == null) {
            return null;
        }
        return scoreboard.getSortedScores(objective).stream()
                .map(score -> score.getPlayerName())
                .collect(Collectors.toList());
    }

    public static boolean getName(String name) {
        List<String> sidebarLines = getSidebarLines();
        if (sidebarLines == null) {
            return false;
        }
        return sidebarLines.stream().anyMatch(line -> line.contains(name));
    }
}