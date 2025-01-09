package mixins.game;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import utils.Utils;
import utils.render.RenderUtil;

@Mixin(RenderItem.class)
public class RenderItemMixin implements Utils {

    @Inject(method = "renderItemAndEffectIntoGUI", at = @At("RETURN"))
    private void onRenderItemPost(final ItemStack stack, int x, int y, CallbackInfo ci) {
        if (shouldHighlightItem(stack)) {
            RenderUtil.renderHighlight(x, y);
        }
    }

    private boolean shouldHighlightItem(ItemStack stack) {
        return stack != null && stack.getItem() == Items.diamond;
    }
}
