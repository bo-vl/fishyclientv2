package modules.Settings;

import gui.Modules;
import gui.element.Module;
import gui.element.Setting;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import utils.render.RenderUtil;
import utils.misc.ColorUtil;

import java.awt.*;
import java.util.List;

public class HUD extends Modules {
    private static String rainbow = "Rainbow";
    private static String RainbowSpeed = "Speed";
    private static String Background = "Background";
    private static String Line = "Line";
    private static String MoreInfo = "MoreInfo";

    public HUD() {
        super("HUD", "Settings");
        Modules.registerSetting(this, new Setting(rainbow, "Rainbow", false));
        Modules.registerSetting(this, new Setting(RainbowSpeed, "Speed of rainbow", 1, 1, 10));
        Modules.registerSetting(this, new Setting(Background, "add background", false));
        Modules.registerSetting(this, new Setting(Line, "Line", true));
        Modules.registerSetting(this, new Setting(MoreInfo, "Show more info", false));
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        renderHUD();
    }

    public void renderHUD() {
        if (!isEnabled()) return;
        float offset = 3;

        List<Module> modules = Modules.getAllModules();
        modules.sort((m1, m2) -> Integer.compare(m2.getName().length(), m1.getName().length()));

        boolean showMoreInfo = Modules.getBool("HUD", MoreInfo);

        for (Module module : modules) {
            if (module.isEnabled()) {
                int textWidth = RenderUtil.getStringWidth(module.getName());
                Color textColor = Color.WHITE;
                if (Modules.getBool("HUD", rainbow)) {
                    textColor = ColorUtil.rainbow(Modules.getSlider("HUD", RainbowSpeed) * 10, (int) offset, 1.0f, 1.0f, 1.0f);
                }
                if (Modules.getBool("HUD", Background)) {
                    Gui.drawRect(2, (int) offset - 2, 2 + textWidth + 2, (int) offset + 10, new Color(0, 0, 0, 128).getRGB());
                }
                if (Modules.getBool("HUD", Line)) {
                    Gui.drawRect(0, (int) offset - 2, 2, (int) offset + 10, textColor.getRGB());
                }
                RenderUtil.RenderText(module.getName(), 3, offset, textColor);
                offset += 12;

                if (showMoreInfo) {
                    List<Setting> settings = module.getSettings();
                    for (Setting setting : settings) {
                        String settingText = "-" + setting.getName() + ": " + setting.getValue();
                        int settingTextWidth = RenderUtil.getStringWidth(settingText);
                        Color settingTextColor = Color.LIGHT_GRAY;
                        if (Modules.getBool("HUD", rainbow)) {
                            settingTextColor = ColorUtil.rainbow(Modules.getSlider("HUD", RainbowSpeed) * 10, (int) offset, 1.0f, 1.0f, 1.0f);
                        }
                        if (Modules.getBool("HUD", Background)) {
                            Gui.drawRect(2, (int) offset - 2, 2 + settingTextWidth + 2, (int) offset + 10, new Color(0, 0, 0, 128).getRGB());
                        }
                        if (Modules.getBool("HUD", Line)) {
                            Gui.drawRect(0, (int) offset - 2, 2, (int) offset + 10, textColor.getRGB());
                        }
                        RenderUtil.RenderText(settingText, 3, offset, settingTextColor);
                        offset += 12;
                    }
                }
            }
        }
    }
}