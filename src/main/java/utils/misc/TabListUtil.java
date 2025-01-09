package utils.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.IChatComponent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TabListUtil {
    public static List<String> getTabListLines() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null) return new ArrayList<>();

        NetHandlerPlayClient netHandler = mc.thePlayer.sendQueue;
        if (netHandler == null) return new ArrayList<>();

        Collection<NetworkPlayerInfo> playerList = netHandler.getPlayerInfoMap();
        if (playerList == null) return new ArrayList<>();

        return playerList.stream()
                .map(NetworkPlayerInfo::getDisplayName)
                .filter(component -> component != null)
                .map(IChatComponent::getFormattedText)
                .collect(Collectors.toList());
    }
}