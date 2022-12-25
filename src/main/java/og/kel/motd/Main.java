package og.kel.motd;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.CommandSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerMetadata;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.world.World;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Main implements ModInitializer {
    public static Main INSTANCE;
    static MinecraftServer server;
    public static String MOD_ID = "smotd";
    public static String prefix = "["+MOD_ID+"] ";
    public static final Logger log = LogManager.getLogger(MOD_ID);
    @Override
    public void onInitialize() {
        INSTANCE = this;
        ServerLifecycleEvents.SERVER_STARTED.register(this::start);
    }

    public Config config;
    private void start(MinecraftServer minecraftServer) {
        log.info("Server loaded");
        server = minecraftServer;
        config = new Config();
        setMetaData();
    }
    public static void setMetaData(){
        String time = "";
        ServerMetadata metadata = server.getServerMetadata();
        long worldTime = server.getWorld(World.OVERWORLD).getLunarTime();
        long worldDays;
        long daysPerTick;
        long dayTime;
        if(worldTime > 24000){
            worldDays = (int) (worldTime / 24000);
            daysPerTick = worldDays * 24000;
            dayTime = (int) (worldTime - daysPerTick);
        }else{
            dayTime = (int) worldTime;
        }
        if (dayTime < 6000 && dayTime > 0) {
            time = INSTANCE.config.getMorning();
        } else if (dayTime < 12000 && dayTime > 6000) {
            time = INSTANCE.config.getDay();
        } else if (dayTime < 16500 && dayTime > 12000) {
            time = INSTANCE.config.getEvening();
        } else if (dayTime < 24000 && dayTime > 16500) {
            time = INSTANCE.config.getNight();
        }
        if(time.length() == 0){
            metadata.setDescription(MutableText.of(new LiteralTextContent(Utils.fixFormatCodes(INSTANCE.config.getLine1()+"\n"+INSTANCE.config.getLine2()))));
        } else {
            String line1Time = "";
            int countEnable = INSTANCE.config.getLineCount() - Utils.clearFormatCodes(INSTANCE.config.getLine1()).length() - Utils.clearFormatCodes(time).length();
            if(countEnable <= 0){
                metadata.setDescription(MutableText.of(new LiteralTextContent(Utils.fixFormatCodes(INSTANCE.config.getLine1()+"\n"+INSTANCE.config.getLine2()))));
            } else {
                line1Time += INSTANCE.config.getLine1();
                for (int i = 0; i < countEnable; i++) {
                    line1Time += " ";
                }
                line1Time += time;
                metadata.setDescription(MutableText.of(new LiteralTextContent(Utils.fixFormatCodes(line1Time + "\n" + INSTANCE.config.getLine2()))));
            }
        }
    }
}
