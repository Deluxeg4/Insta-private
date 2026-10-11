package instadevelopment.kiriyaga.insta.mixin;

import instadevelopment.kiriyaga.insta.impl.feature.miscellaneous.NameProtectFeature;
import net.minecraft.util.StringDecomposer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static instadevelopment.kiriyaga.api.InstaApi.MC;
import static instadevelopment.kiriyaga.api.InstaApi.FEATURE_SERVICE;

@Mixin(StringDecomposer.class)
public class MixinStringDecomposer {

    @ModifyVariable(method = "iterateFormatted(Ljava/lang/String;ILnet/minecraft/network/chat/Style;Lnet/minecraft/network/chat/Style;Lnet/minecraft/util/FormattedCharSink;)Z", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private static String replaceText(String value) {
        if (FEATURE_SERVICE.getStorage() == null || FEATURE_SERVICE.getStorage().getByClass(NameProtectFeature.class) == null)
            return value;

        if (FEATURE_SERVICE.getStorage().getByClass(NameProtectFeature.class).isEnabled()) return value.replaceAll(MC.getUser().getName(), "InstaClient"); // TODO unhardcode that. the day im gonna write runtime lists
        return value;
    }
}