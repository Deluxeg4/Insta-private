package instadevelopment.kiriyaga.api;

import instadevelopment.kiriyaga.api.core.*;
import instadevelopment.kiriyaga.api.core.breakprediction.BreakPredictionService;
import instadevelopment.kiriyaga.api.core.cat.InstaFormatService;
import instadevelopment.kiriyaga.api.core.command.CommandService;
import instadevelopment.kiriyaga.api.core.config.ConfigService;
import instadevelopment.kiriyaga.api.core.feature.FeatureService;
import instadevelopment.kiriyaga.api.core.font.FontService;
import instadevelopment.kiriyaga.api.core.input.InputService;
import instadevelopment.kiriyaga.api.core.inventory.InventoryService;
import instadevelopment.kiriyaga.api.core.macro.MacroService;
import instadevelopment.kiriyaga.api.core.rotation.RotationService;
import instadevelopment.kiriyaga.api.core.socials.SocialsService;
import instadevelopment.kiriyaga.api.util.CatStyles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InstaApi implements ClientModInitializer {

    public static String NAME = "Insta";
    public static final Logger LOGGER = LogManager.getLogger(NAME);
    public static final Minecraft MC = Minecraft.getInstance();
    public static final String API_VERSION;
    static {
        ModContainer mod = FabricLoader.getInstance().getModContainer("insta-api").orElse(null);
        if (mod != null) {
            API_VERSION = mod.getMetadata().getVersion().getFriendlyString();
        } else {
            API_VERSION = "dev-environment";
        }
    }

    public static final EventService EVENT_SERVICE = new EventService();
    public static final MacroService MACRO_SERVICE = new MacroService();
    public static final ConfigService CONFIG_SERVICE = new ConfigService();
    public static final FeatureService FEATURE_SERVICE = new FeatureService();
    public static final FontService FONT_SERVICE = new FontService();
    public static final CommandService COMMAND_SERVICE = new CommandService();
    public static final ChatService CHAT_SERVICE = new ChatService();
    public static final SocialsService SOCIALS_SERVICE = new SocialsService(CONFIG_SERVICE);
    public static final RotationService ROTATION_SERVICE = new RotationService();
    public static final InventoryService INVENTORY_SERVICE = new InventoryService();
    public static final ServerService SERVER_SERVICE = new ServerService();
    public static final InputService INPUT_SERVICE = new InputService();
    public static final PluginService PLUGIN_SERVICE = new PluginService();
    public static final BreakPredictionService BREAKPREDICT_SERVICE = new BreakPredictionService();
    public static final TotemCounterService TOTEMCOUNTER_SERVICE = new TotemCounterService();
    public static final KeyBindService KEYBIND_SERVICE = new KeyBindService();

    public static InstaFormatService CAT_FORMAT = new InstaFormatService();

    @Override
    public void onInitializeClient() {

        //  FEATURE_SERVICE.init();
        //COMMAND_SERVICE.init();
        //COMMAND_SERVICE.getSuggester().updateDispatcher();
        //FONT_SERVICE.init();
        ROTATION_SERVICE.init();
        INVENTORY_SERVICE.init();
        SERVER_SERVICE.init();
        CHAT_SERVICE.init();
        INPUT_SERVICE.init();
        KEYBIND_SERVICE.init();
        BREAKPREDICT_SERVICE.init();
        TOTEMCOUNTER_SERVICE.init();


        CAT_FORMAT.add(new CatStyles());

        SOCIALS_SERVICE.load();

        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            FONT_SERVICE.init(); // font is making glyph textures, it should be after game loaded not on initialize
        });
    }
}
