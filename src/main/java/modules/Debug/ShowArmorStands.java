package modules.Debug;

import gui.Modules;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashSet;
import java.util.Set;

public class ShowArmorStands extends Modules {
    private final Set<EntityArmorStand> modifiedArmorStands = new HashSet<>();

    public ShowArmorStands() {
        super("ShowArmorStands", "Debug");
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        if (mc.theWorld == null) return;

        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity instanceof EntityArmorStand) {
                EntityArmorStand armorStand = (EntityArmorStand) entity;
                if (armorStand.isInvisible()) {
                    armorStand.setInvisible(false);
                    modifiedArmorStands.add(armorStand);
                }
            }
        }
    }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
        if (!active) {
            revertArmorStandsVisibility();
        }
    }

    private void revertArmorStandsVisibility() {
        for (EntityArmorStand armorStand : modifiedArmorStands) {
            armorStand.setInvisible(true);
        }
        modifiedArmorStands.clear();
    }
}