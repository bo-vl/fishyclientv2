package utils.player;

import utils.Utils;

public class ChatUtil implements Utils {
    public static void SendChatMessage(String Type, String message) {
        if (Type.equals("Party")) {
            mc.thePlayer.sendChatMessage("/Party Chat " + message);
        } else if (Type.equals("Guild")) {
            mc.thePlayer.sendChatMessage("/Guild Chat " + message);
        } else if (Type.equals("Local")) {
            mc.thePlayer.sendChatMessage(message);
        }
    }
}