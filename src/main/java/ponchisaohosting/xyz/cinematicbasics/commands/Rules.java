package ponchisaohosting.xyz.cinematicbasics.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.Map;

public class Rules {
    private static final Map<ServerPlayerEntity, Vec3d> previousPlayerPositions = new HashMap<>();
    private static boolean movementEnabled = true;


    public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("togglemovement")
                .requires(source -> source.hasPermissionLevel(2))
                .executes(context -> {
                    toggleMovement(context.getSource());
                    return 1;
                }));
    }

    private static void toggleMovement(ServerCommandSource source) {
        movementEnabled = !movementEnabled; // Alternar el estado del movimiento
        String message = "El movimiento de los NO-OPERADORES está ahora " + (movementEnabled ? "deshabilitado" : "habilitado");
        source.sendFeedback(Text.of(message), false);
    }

    public static void onPlayerMove(ServerPlayerEntity player) {
        // Verifica si el jugador no es creativo, no es espectador y no es operador (OP)
        if (movementEnabled && !player.isCreative() && !player.isSpectator() && !player.hasPermissionLevel(2)) {
            // Obtiene la posición anterior del jugador
            Vec3d previousPos = previousPlayerPositions.getOrDefault(player, null);
            // Comprueba si la posición anterior existe y si la distancia entre la posición actual y la anterior es mayor que 0.1
            if (previousPos != null && player.getPos().distanceTo(previousPos) > 0.1) {
                // Si la distancia es mayor que 0.1, teletransporta al jugador a la posición anterior
                player.teleport(previousPos.x, previousPos.y, previousPos.z);
            } else {
                // Si la distancia es menor o igual a 0.1, actualiza la posición anterior del jugador
                previousPlayerPositions.put(player, player.getPos());
            }
        }
    }

    public static void onPlayerJoin(ServerPlayerEntity player) {
        String joinMessage = "§7[§c§lBLANCHINIILAND§7] §b» §f El movimiento está deshabilitado para una mejor experiencia de juego, procedemos a aplicarle la invisibilidad para no estorbar a los demas jugadores.";
        player.sendMessage(Text.of(joinMessage), false);

        // Apply a potion effect to the player without particles
        StatusEffectInstance effect = new StatusEffectInstance(StatusEffects.INVISIBILITY, Integer.MAX_VALUE, 1, false, false);
        player.addStatusEffect(effect);
        player.getInventory().clear();
    }
}
