package gui.Animation;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class SlidingTextAnimation {
    private final Minecraft mc;
    private final List<String> enabledModules;
    private float xOffset;
    private boolean enabled;
    private static final int SLIDE_SPEED = 5;
    private static final int MAX_OFFSET = 0;
    private static final int MIN_OFFSET = -200;

    public SlidingTextAnimation(List<String> enabledModules) {
        this.mc = Minecraft.getMinecraft();
        this.enabledModules = enabledModules;
        this.xOffset = MIN_OFFSET;
        this.enabled = false;
    }

    public void slideOut() {
        this.enabled = true;
    }

    public void slideIn() {
        this.enabled = false;
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        if (enabled) {
            xOffset = Math.min(MAX_OFFSET, xOffset + SLIDE_SPEED);
        } else {
            xOffset = Math.max(MIN_OFFSET, xOffset - SLIDE_SPEED);
        }

        if (Math.abs(xOffset) >= 200) {
            return;
        }

        int y = 10;
        for (String module : enabledModules) {
            mc.fontRendererObj.drawString(module, 10 + (int)xOffset, y, 0xFFFFFF);
            y += mc.fontRendererObj.FONT_HEIGHT + 2;
        }
    }
}