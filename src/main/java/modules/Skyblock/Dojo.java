package modules.Skyblock;

import gui.Modules;
import gui.element.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import utils.player.InventoryUtil;

public class Dojo extends Modules {
    private static final String TOD = "Dicipline";
    private static final Minecraft mc = Minecraft.getMinecraft();

    public Dojo() {
        super("Dojo Helper", "Skyblock");
        Modules.registerSetting(this, new Setting(TOD, "Auto Test of Dicipline", false));
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START || !Modules.getBool("Dojo Helper", TOD)) {
            return;
        }
        if (mc.objectMouseOver == null || mc.objectMouseOver.entityHit == null) {
            return;
        }
        Entity entity = mc.objectMouseOver.entityHit;
        if (entity instanceof EntityZombie) {
            ItemStack helmet = ((EntityZombie) entity).getCurrentArmor(3);
            if (helmet != null) {
                Item sword = null;
                Item item = helmet.getItem();
                if (item.equals(Items.leather_helmet)) {
                    sword = Items.wooden_sword;
                } else if (item.equals(Items.iron_helmet)) {
                    sword = Items.iron_sword;
                } else if (item.equals(Items.golden_helmet)) {
                    sword = Items.golden_sword;
                } else if (item.equals(Items.diamond_helmet)) {
                    sword = Items.diamond_sword;
                } else {
                    return;
                }
                InventoryUtil.swapTo(sword);
            }
        }
    }
}