package instadevelopment.kiriyaga.insta.mixin;

import instadevelopment.kiriyaga.api.event.impl.ItemEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static instadevelopment.kiriyaga.api.InstaApi.EVENT_SERVICE;
import static instadevelopment.kiriyaga.api.InstaApi.MC;

@Mixin(UseOnContext.class)
public final class MixinUseOnContext {
    @Inject(method = "getItemInHand", at = @At("RETURN"), cancellable = true)
    public void getStack(final CallbackInfoReturnable<ItemStack> info) {
        if (MC.player == null)
            return;

        ItemEvent event = new ItemEvent();
        EVENT_SERVICE.post(event);

        if (info.getReturnValue().equals(MC.player.getMainHandItem()) && event.isCancelled())
            info.setReturnValue(event.getStack());
    }
}