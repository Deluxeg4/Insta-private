package instadevelopment.kiriyaga.insta.mixin;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import instadevelopment.kiriyaga.api.event.impl.PacketReceiveEvent;
import instadevelopment.kiriyaga.api.event.impl.PacketSendEvent;
import instadevelopment.kiriyaga.insta.impl.feature.miscellaneous.NoPacketKick;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static instadevelopment.kiriyaga.insta.Insta.*;
import static instadevelopment.kiriyaga.api.InstaApi.*;
@Mixin(Connection.class)
public abstract class MixinConnection {
    private boolean b = false;

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    public void onPacketReceive(ChannelHandlerContext ctx, Packet<?> packet, CallbackInfo ci) {
        if (b) return;
        PacketReceiveEvent event = new PacketReceiveEvent(packet);
        EVENT_SERVICE.post(event);

        if (event.isCancelled()) {
            ci.cancel();
            return;
        }
        if (event.getPacket() != packet) {
            ci.cancel();

            b = true;
            try {
                ctx.fireChannelRead(event.getPacket());
            } finally {
                b = false;
            }
        }
    }

    @Inject(method = "sendPacket", at = @At("HEAD"), cancellable = true)
    private void onPacketSend(Packet<?> packet, ChannelFutureListener listener, boolean flush, CallbackInfo ci) {
        PacketSendEvent event = new PacketSendEvent(packet);
        EVENT_SERVICE.post(event);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(method = "exceptionCaught", at = @At("HEAD"), cancellable = true)
    private void exceptionCaught(ChannelHandlerContext context, Throwable exception, CallbackInfo ci) {
        NoPacketKick Feature = FEATURE_SERVICE.getStorage().getByClass(NoPacketKick.class);
        if (Feature != null && Feature.isEnabled()) {
            LOGGER.error("Packet exception caught: ", exception);
            ci.cancel();
        }
    }
}