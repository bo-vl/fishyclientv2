package utils.player;

import net.minecraft.client.settings.KeyBinding;
import utils.Utils;

public class PlayerUtil implements Utils {
    public void rightClick() {
        KeyBinding.onTick(mc.gameSettings.keyBindUseItem.getKeyCode());
    }

    public void leftClick() {
        KeyBinding.onTick(mc.gameSettings.keyBindAttack.getKeyCode());
    }

    public void dropItem() {
        KeyBinding.onTick(mc.gameSettings.keyBindDrop.getKeyCode());
    }
}