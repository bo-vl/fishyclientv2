package modules.pelt;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import gui.element.ToggleModule;
import java.util.Random;

public class AutoWarpBack extends ToggleModule {
    private static final Random random = new Random();

    public AutoWarpBack() {
        super("AutoWarpBack", "Helper");
    }

    @SubscribeEvent
    public void onChatMessage(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText().replaceAll("§.", "");

        if (message.contains("Return to the Trapper soon to get a new animal to hunt!")) {
            new Thread(() -> {
                try {
                    Thread.sleep(random.nextInt(401) + 100);
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/warp trapper");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}