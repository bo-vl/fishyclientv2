package utils.skyblock;

import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import utils.Utils;
import utils.lists.Island;
import utils.misc.TabListUtil;

import java.util.List;

public class AreaUtil implements Utils {

    private static Island currentArea = Island.Unknown;

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Unload event) {
        currentArea = Island.Unknown;
    }

    @SubscribeEvent
    public void onDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        currentArea = Island.Unknown;
    }

    public static Island getArea() {
        if (mc.isSingleplayer()) {
            return Island.SinglePlayer;
        }

        if (mc.thePlayer == null) {
            return Island.Unknown;
        }

        List<String> tabList = TabListUtil.getTabListLines();
        if (tabList == null || tabList.isEmpty()) {
            return Island.Unknown;
        }

        String area = tabList.stream()
                .filter(line -> line != null)
                .map(line -> line.replaceAll("§[0-9a-fk-or]", "").trim())
                .filter(line -> line.contains("Area: ") || line.contains("Dungeon: "))
                .findFirst()
                .orElse(null);

        return Island.entries().stream()
                .filter(island -> area != null && area.contains(island.getDisplayName()))
                .findFirst()
                .orElse(Island.Unknown);
    }
}