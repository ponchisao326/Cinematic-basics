package ponchisaohosting.xyz.cinematicbasics;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ponchisaohosting.xyz.cinematicbasics.commands.Rules;



public class Cinematic_basics implements ModInitializer {

    public static final String MOD_ID = "cinematic-basics";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Cinematics-Basics: Iniciando...");
        LOGGER.info("Autor: PonchisaoHosting (Ponchisao326)");

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            server.getPlayerManager().getPlayerList().forEach(Rules::onPlayerMove);
        });

    }

}