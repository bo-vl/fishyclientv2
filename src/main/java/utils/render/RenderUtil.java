package utils.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

import org.lwjgl.opengl.GL11;
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

    public static void renderFilledBB(EntityLivingBase entityLivingBase, Color color, float alpha) {
        AxisAlignedBB bb = ESPUtil.getInterpolatedBB(entityLivingBase);
        GlStateManager.pushMatrix();
        GLUtil.setup2DRendering();
        GLUtil.enableCaps(GL_BLEND, GL_POINT_SMOOTH, GL_POLYGON_SMOOTH, GL_LINE_SMOOTH);

        glDisable(GL_DEPTH_TEST);
        glDepthMask(false);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        float actualAlpha = .3f * alpha;
        glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, actualAlpha);

        renderFilledBoundingBox(bb);

        glDepthMask(true);
        glEnable(GL_DEPTH_TEST);

        GLUtil.disableCaps();
        GLUtil.end2DRendering();

        GlStateManager.popMatrix();
    }

    public static void renderTracer(Entity entity, float width, Color color, float alpha) {
        if (entity == null || mc.thePlayer == null) return;

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

    public static void RenderBlock(BlockPos pos, Color color, float alpha) {
        if (pos == null || mc.thePlayer == null) return;

        GLUtil.disableDepth();
        GLUtil.setup2DRendering();

        glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, alpha);

        AxisAlignedBB boundingBox = new AxisAlignedBB(
                pos.getX(), pos.getY(), pos.getZ(),
                pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1
        );

        GlStateManager.pushMatrix();
        GL11.glTranslated(-mc.getRenderManager().viewerPosX,
                -mc.getRenderManager().viewerPosY,
                -mc.getRenderManager().viewerPosZ);

        GLUtil.renderBoundingBox(boundingBox);

        GlStateManager.popMatrix();

        GLUtil.end2DRendering();
        GLUtil.enableDepth();
    }

    public static void RenderText(String text, float x, float y, Color color) {
        mc.fontRendererObj.drawStringWithShadow(text, x, y, color.getRGB());
    }


    public static boolean isHovered(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public static void renderFloatingText(String text, Entity entity, Color color, float scale, float yOffset) {
        GlStateManager.pushMatrix();
        double[] entityPos = getInterpolatedPos(entity);
        GlStateManager.translate(entityPos[0], entityPos[1] + entity.height + yOffset, entityPos[2]);
        GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0, 1, 0);
        GlStateManager.rotate(mc.getRenderManager().playerViewX, 1, 0, 0);
        GlStateManager.scale(-0.025F * scale, -0.025F * scale, 0.025F * scale);
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        int width = mc.fontRendererObj.getStringWidth(text) / 2;
        mc.fontRendererObj.drawStringWithShadow(text, -width, 0, color.getRGB());

        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void RenderTracerBlock(BlockPos blockPos, float width, Color color, float alpha) {
        GLUtil.disableDepth();
        GLUtil.setup2DRendering();

        glEnable(GL_LINE_SMOOTH);
        glLineWidth(width);

        glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, alpha);

        double[] playerPos = getInterpolatedPos(mc.thePlayer);
        double[] blockPosInterpolated = {
                blockPos.getX() - mc.getRenderManager().viewerPosX + 0.5,
                blockPos.getY() - mc.getRenderManager().viewerPosY + 0.5,
                blockPos.getZ() - mc.getRenderManager().viewerPosZ + 0.5
        };

        glBegin(GL_LINES);
        glVertex3d(playerPos[0], playerPos[1] + mc.thePlayer.getEyeHeight(), playerPos[2]);
        glVertex3d(blockPosInterpolated[0], blockPosInterpolated[1], blockPosInterpolated[2]);
        glEnd();

        glDisable(GL_LINE_SMOOTH);
        GLUtil.end2DRendering();
        GLUtil.enableDepth();
    }

    public static int getStringWidth(String text) {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
        return fontRenderer.getStringWidth(text);
    }

    public static void renderHighlight(int x, int y) {
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glLineWidth(2F);
        GL11.glColor4f(1F, 1F, 1F, 1F);

        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x + 16, y);
        GL11.glVertex2f(x + 16, y + 16);
        GL11.glVertex2f(x, y + 16);
        GL11.glEnd();

        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
