package modules.Render;

import gui.element.Setting;
import gui.element.ToggleModule;
import gui.util.ModuleSettings;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class PeltHelper extends ToggleModule {
    private static final Random random = new Random();

    private static final String BOOLEAN_KEY = "Auto Warp back";

    public PeltHelper() {
        super("Pelt Helper", "Render");

        ModuleSettings.registerSetting(this, new Setting(BOOLEAN_KEY, "Makes you automatically warp back",true),BOOLEAN_KEY);
    }

    @SubscribeEvent
    public void onChatMessage(ClientChatReceivedEvent event) {
        if (!ModuleSettings.getBool("Pelt Helper", BOOLEAN_KEY)) {
            return;
        }

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