package utils.skyblock;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import utils.Utils;
import utils.lists.Island;

import java.util.Collection;

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

        NetHandlerPlayClient netHandlerPlayClient = mc.thePlayer != null ? mc.thePlayer.sendQueue : null;
        if (netHandlerPlayClient == null) {
            return Island.Unknown;
        }

        Collection<NetworkPlayerInfo> list = netHandlerPlayClient.getPlayerInfoMap();
        if (list == null) {
            return Island.Unknown;
        }

        String area = list.stream()
                .map(NetworkPlayerInfo::getDisplayName)
                .filter(displayName -> displayName != null && (displayName.getUnformattedText().startsWith("Area: ") || displayName.getUnformattedText().startsWith("Dungeon: ")))
                .map(displayName -> displayName.getFormattedText())
                .findFirst()
                .orElse(null);

        return Island.entries().stream()
                .filter(island -> area != null && area.contains(island.getDisplayName()))
                .findFirst()
                .orElse(Island.Unknown);
    }
}