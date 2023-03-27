package og.kel.motd;

import lombok.Getter;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main implements ModInitializer {
    public static final String MOD_ID = "smotd";
    public static final String prefix = "[" + MOD_ID + "] ";
    private static Config config;
    @Getter
    private static final Logger log = LogManager.getLogger(MOD_ID);
    @Getter
    private static MinecraftServer server;

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTED.register(this::start);
    }

    private void start(MinecraftServer minecraftServer) {
        log.info("sMOTD started");
        server = minecraftServer;
        config = new Config();
        setMetaData();
    }

    public static void setMetaData() {
        // Инициализация описания
        String descreption = Utils.fixFormatCodes(config.getLine1());

        // Подсчет времени. Если мир - null, ставит -1
        ServerWorld world = server.getWorld(World.OVERWORLD);
        long dayTime = world != null ? world.getLunarTime() % 24000L : -1;

        String time = dayTime < 0 ? "" : dayTime < 6000 ? config.getMorning() : dayTime < 12000 ?
                config.getDay() : dayTime < 16500 ? config.getEvening() : config.getNight();

        // Добавление типа времени, если время > 0
        if (time.length() != 0) {
            int countEnable = config.getLineCount() - Utils.clearFormatCodes(config.getLine1()).length() -
                    Utils.clearFormatCodes(time).length();
            descreption = countEnable <= 0 ? Utils.fixFormatCodes(config.getLine1()) :
                    Utils.fixFormatCodes(config.getLine1() + " ".repeat(countEnable) + time);
        }

        // В случае, если НЕ рандомная строка на 2 линии, происходит добавление 2 строки. Иначе: выбор рандомной строки.
        double random = Math.floor(Math.random() * config.getRandomLine2().length());
        descreption += !config.getUseRandomLine2() ? "\n" + Utils.fixFormatCodes(config.getLine2()) :
                config.getRandomLine2().length() == 0 ? "\n" + Utils.fixFormatCodes(config.getLine2()) :
                        "\n" + Utils.fixFormatCodes(config.getRandomLine2().getString((int) random));

        server.setMotd(descreption);
    }
}
