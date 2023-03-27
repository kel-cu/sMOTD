package og.kel.motd;

import lombok.Getter;
import net.minecraft.SharedConstants;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config {
    @Getter
    private String line1 = "A Minecraft Server";
    @Getter
    private String line2 = "Minecraft " + SharedConstants.getGameVersion().getName();
    @Getter
    private String day = "Day";
    @Getter
    private String night = "Night";
    @Getter
    private String morning = "Morning";
    @Getter
    private String evening = "Evening";
    @Getter
    private int lineCount = 58;
    @Getter
    private Boolean useRandomLine2 = false;
    @Getter
    private JSONArray randomLine2 = new JSONArray().put("Hello, world!").put("Hi!");

    final Path configFile = Main.INSTANCE.server.getRunDirectory().toPath().resolve("config/smotd.json");
    public Config(){
        //final Path configFile = mc.runDirectory.toPath().resolve("SimplyStatus/servers/"+ serverAddress +".json");
        if(!configFile.toFile().exists()){
            save();
            Main.log.info(Main.prefix+"Please reload server for load my config");
            Main.server.exit();
        } else {
            try{
                JSONObject json = new JSONObject(Files.readString(configFile, StandardCharsets.UTF_8));
                if (json.has("line1")) {line1 = json.getString("line1");}
                if (json.has("line2")) {line2 = json.getString("line2");}
                if (json.has("day")) {day = json.getString("day");}
                if (json.has("night")) {night = json.getString("night");}
                if (json.has("morning")) {morning = json.getString("morning");}
                if (json.has("evening")) {evening = json.getString("evening");}
                if (json.has("countLine")) {lineCount = json.getInt("countLine");}
                if (json.has("useRandomLine2")) {useRandomLine2 = json.getBoolean("useRandomLine2");}
                if (json.has("randomLine2")) {randomLine2 = json.getJSONArray("randomLine2");}
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    private void save(){
        JSONObject json = new JSONObject();
        json.put("line1", "A Minecraft Server")
                .put("line2", "Minecraft " + SharedConstants.getGameVersion().getName())
                .put("day", "Day")
                .put("night", "Night")
                .put("morning", "Morning")
                .put("evening", "Evening")
                .put("countLine", 55)
                .put("useRandomLine2", false)
                .put("randomLine2", new JSONArray().put("Hello, world!").put("Hi!"));
        try {
            Files.createDirectories(configFile.getParent());
            Files.writeString(configFile, json.toString(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}