package utils.render;

import Events.RenderPartialTicks;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;

import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import utils.Utils;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;
import static utils.render.ESPUtil.getInterpolatedPos;
import static utils.render.GLUtil.*;

public class RenderUtil implements Utils {
    public static void renderBB(EntityLivingBase entityLivingBase, Color color, float alpha) {
        AxisAlignedBB bb = ESPUtil.getInterpolatedBB(entityLivingBase);
        GlStateManager.pushMatrix();
        GLUtil.setup2DRendering();
        GLUtil.enableCaps(GL_BLEND, GL_POINT_SMOOTH, GL_POLYGON_SMOOTH, GL_LINE_SMOOTH);

        glDisable(GL_DEPTH_TEST);
        glDepthMask(false);
        glLineWidth(3);

        float actualAlpha = .3f * alpha;
        glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, actualAlpha);

        renderBoundingBox(bb);

        glDepthMask(true);
        glEnable(GL_DEPTH_TEST);

        GLUtil.disableCaps();
        GLUtil.end2DRendering();

        GlStateManager.popMatrix();
    }

    public static void renderTracer(Entity entity, float width, Color color, float alpha) {
        if (entity == null || mc.thePlayer == null) return;

        float ticks = RenderPartialTicks.getPartialTicks();

        GLUtil.disableDepth();
        GLUtil.setup2DRendering();

        glEnable(GL_LINE_SMOOTH);
        glLineWidth(width);

        glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, alpha);

        double[] playerPos = getInterpolatedPos(mc.thePlayer);
        double[] entityPos = getInterpolatedPos(entity);

        glBegin(GL_LINES);
        glVertex3d(playerPos[0], playerPos[1] + mc.thePlayer.getEyeHeight(), playerPos[2]);

        glVertex3d(entityPos[0], entityPos[1] + entity.height / 2.0, entityPos[2]);
        glEnd();

        glDisable(GL_LINE_SMOOTH);
        GLUtil.end2DRendering();
        GLUtil.enableDepth();
    }

    public static void RenderText(String text, int x, int y, Color color) {
        mc.fontRendererObj.drawStringWithShadow(text, x, y, color.getRGB());
    }

    public static boolean isHovered(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
