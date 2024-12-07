package utils.player;

import net.minecraft.item.*;
import utils.Utils;

public class InventoryUtil implements Utils {

    public int getHotbarSlot(int slot) {
        return slot + 36;
    }

    public static void swapTo(Item item) {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);
            if (stack != null && stack.getItem() == item) {
                mc.thePlayer.inventory.currentItem = i;
                return;
            }
        }
    }

    public static void switchToSlot(int slot) {
        if (slot >= 0 && slot < 9) {
            mc.thePlayer.inventory.currentItem = slot;
        }
    }
}