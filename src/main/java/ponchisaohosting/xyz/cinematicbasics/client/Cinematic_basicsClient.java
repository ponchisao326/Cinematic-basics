package ponchisaohosting.xyz.cinematicbasics.client;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cinematic_basicsClient implements ClientModInitializer {

    public static final String MOD_ID = "cinematic-basics";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("Cinematics-Basics: Iniciando...");
        LOGGER.info("Autor: PonchisaoHosting (Ponchisao326)");

    }
}
