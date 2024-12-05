package Events.game;

import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class RenderPartialTicks extends Event {
    private static float partialTicks;

    public RenderPartialTicks(float partialTicks) {
        RenderPartialTicks.partialTicks = partialTicks;
    }

    public static float getPartialTicks() {
        return partialTicks;
    }

    public static void setPartialTicks(float partialTicks) {
        RenderPartialTicks.partialTicks = partialTicks;
    }
}
