package mixins.game;

import Events.game.RenderPartialTicks;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.renderer.EntityRenderer.class)
public class RenderPartialTicksMixin {
    @Inject(method = "updateCameraAndRender", at = @At("HEAD"), cancellable = true)
    public void onRenderPartialTicks(float partialTicks, long nanoTime, CallbackInfo ci) {
        RenderPartialTicks event = new RenderPartialTicks(partialTicks);

        MinecraftForge.EVENT_BUS.post(event);

        if (event.isCanceled()) {
            ci.cancel();
        }
    }
}
