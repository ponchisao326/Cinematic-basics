package ponchisaohosting.xyz.cinematicbasics;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
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

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            // Obtener el jugador que se ha conectado
            ServerPlayerEntity player = handler.player;

            Rules.onPlayerJoin(player);
        });

        // Registrar el evento de fin de tick del servidor para el manejo del movimiento del jugador
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            server.getPlayerManager().getPlayerList().forEach(Rules::onPlayerMove);
        });

        CommandRegistrationCallback.EVENT.register(new CommandRegistrationCallback() {
            @Override
            public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
                Rules.registerCommands(dispatcher);
            }
        });

    }

}