package modules.Render;

import gui.Modules;
import gui.element.Setting;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import utils.lists.Island;
import utils.render.RenderUtil;
import utils.skyblock.AreaUtil;

import java.awt.*;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Endernode extends Modules {
    public static Set<BlockPos> positions = ConcurrentHashMap.newKeySet();

    private static final String Withline = "line";
    private static final String RadiusCheck = "Radius";

    public Endernode() {
        super("Endernode ESP", "Render");
        Modules.registerSetting(this, new Setting(Withline, "Draws a line to the block", false));
        Modules.registerSetting(this, new Setting(RadiusCheck, "Radius of blocks", 10, 1, 50));
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        Island currentArea = AreaUtil.getArea();
        if (currentArea != Island.TheEnd) {
            return;
        }


        BlockPos playerPos = mc.thePlayer.getPosition();
        int radius = Modules.getSlider("Endernode ESP", RadiusCheck);

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos checkPos = playerPos.add(x, y, z);
                    IBlockState blockState = mc.theWorld.getBlockState(checkPos);
                    if (blockState.getBlock() == Blocks.stained_hardened_clay && blockState.getBlock().getMetaFromState(blockState) == 10) {
                        positions.add(checkPos);
                    } else {
                        positions.remove(checkPos);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        Island currentArea = AreaUtil.getArea();
        if (currentArea != Island.TheEnd) {
            return;
        }

        for (BlockPos pos : positions) {
            RenderUtil.RenderBlock(pos, Color.WHITE, 1);
            if (Modules.getBool("Endernode ESP", Withline)) {
                RenderUtil.RenderTracerBlock(pos, 1, Color.WHITE, 1);
            }
        }
    }
}