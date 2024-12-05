package modules.Render;

import Events.game.SpawnParticleEvent;
import gui.Modules;
import gui.element.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import utils.render.RenderUtil;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static utils.misc.MathUtils.basicallyEqual;

public class Endernode extends Modules {
    public static Map<BlockPos, Integer> positions = new HashMap<>();

    private static final String Withline = "line";
    public Endernode() {
        super("Endernode ESP", "Render");
        Modules.registerSetting(this, new Setting(Withline, "Draws a line to the block", false));
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        positions.entrySet().removeIf(entry -> {
            BlockPos pos = entry.getKey();
            int ticks = entry.getValue() - 1;

            IBlockState state = mc.theWorld.getBlockState(pos);
            if (state.getBlock() != Blocks.end_stone && state.getBlock() != Blocks.obsidian) {
                return true;
            }

            if (ticks <= 0) {
                return true;
            } else {
                entry.setValue(ticks);
                return false;
            }
        });
    }

    @SubscribeEvent
    public void onParticleSpawn(SpawnParticleEvent event) {
        if (event.particleType == EnumParticleTypes.PORTAL) {
            double x = event.position.xCoord;
            double y = event.position.yCoord;
            double z = event.position.zCoord;

            boolean xZero = basicallyEqual((x - 0.5) % 1, 0, 0.2);
            boolean yZero = basicallyEqual((y - 0.5) % 1, 0, 0.2);
            boolean zZero = basicallyEqual((z - 0.5) % 1, 0, 0.2);

            if (Math.abs(y % 1) == 0.25 && xZero && zZero) {
                addPositionIfValid(x, y - 1, z);
                return;
            }
            if (Math.abs(y % 1) == 0.75 && xZero && zZero) {
                addPositionIfValid(x, y + 1, z);
                return;
            }
            if (Math.abs(x % 1) == 0.25 && yZero && zZero) {
                addPositionIfValid(x + 1, y, z);
                return;
            }
            if (Math.abs(x % 1) == 0.75 && yZero && zZero) {
                addPositionIfValid(x - 1, y, z);
                return;
            }
            if (Math.abs(z % 1) == 0.25 && yZero && xZero) {
                addPositionIfValid(x, y, z + 1);
                return;
            }
            if (Math.abs(z % 1) == 0.75 && yZero && xZero) {
                addPositionIfValid(x, y, z - 1);
            }
        }
    }

    private void addPositionIfValid(double x, double y, double z) {
        BlockPos pos = new BlockPos(Math.floor(x), Math.floor(y), Math.floor(z));
        Block block = Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
        if (block == Blocks.end_stone || block == Blocks.obsidian) {
            positions.put(pos, 75);
        }
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        for (BlockPos pos : positions.keySet()) {
            RenderUtil.RenderBlock(pos, Color.WHITE, 1);
            if (Modules.getBool("Endernode ESP", Withline)) {
                RenderUtil.RenderTracerBlock(pos, 1, Color.WHITE, 1);
            }
        }
    }
}
