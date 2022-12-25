package og.kel.motd;

import com.google.gson.JsonObject;
import net.minecraft.SharedConstants;
import net.minecraft.server.MinecraftServer;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config {
    private String line1;
    private String line2;
    private String day;
    private String night;
    private String morning;
    private String evening;
    private int lineCount;
    final Path configFile = Main.INSTANCE.server.getRunDirectory().toPath().resolve("config/smotd.json");
    public Config(){
        //final Path configFile = mc.runDirectory.toPath().resolve("SimplyStatus/servers/"+ serverAddress +".json");
        if(!configFile.toFile().exists()){
            save();
            Main.log.info(Main.prefix+"Please reload server for load my config");
            Main.server.exit();
            return;
        } else {
            try{
                JSONObject json = new JSONObject(Files.readString(configFile, StandardCharsets.UTF_8));
                if(!json.isNull("line1")) line1 = json.getString("line1");
                else line1 = "A Minecraft Server";
                if(!json.isNull("line2")) line2 = json.getString("line2");
                else line2 = "Minecraft " + SharedConstants.getGameVersion().getName();
                if(!json.isNull("day")) day = json.getString("day");
                else day = "Day";
                if(!json.isNull("night")) night = json.getString("night");
                else night = "Night";
                if(!json.isNull("morning")) morning = json.getString("morning");
                else morning = "Morning";
                if(!json.isNull("evening")) evening = json.getString("evening");
                else evening = "Evening";
                if(!json.isNull("countLine")) lineCount = json.getInt("countLine");
                else lineCount = 58;
            } catch (Exception e){
                e.printStackTrace();
            }
        };
    }
    private void save(){
        JSONObject json = new JSONObject();
        json.put("line1", "A Minecraft Server")
                .put("line2", "Minecraft " + SharedConstants.getGameVersion().getName())
                .put("day", "Day")
                .put("night", "Night")
                .put("morning", "Morning")
                .put("evening", "Evening")
                .put("countLine", 55);
        try {
            Files.createDirectories(configFile.getParent());
            Files.writeString(configFile, json.toString(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    public int getLineCount() {
        return lineCount;
    }

    public String getDay() {
        return day;
    }

    public String getNight() {
        return night;
    }

    public String getMorning() {
        return morning;
    }

    public String getEvening() {
        return evening;
    }

    public String getLine1() {
        return line1;
    }

    public String getLine2() {
        return line2;
    }
}
