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
    private String line1;
    @Getter
    private String line2;
    @Getter
    private String day;
    @Getter
    private String night;
    @Getter
    private String morning;
    @Getter
    private String evening;
    @Getter
    private int lineCount;
    @Getter
    private Boolean useRandomLine2;
    @Getter
    private JSONArray randomLine2;
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
                line1 = json.has("line1") ? json.getString("line1") : "A Minecraft Server";
                line2 = json.has("line2") ? json.getString("line2") : "Minecraft " + SharedConstants.getGameVersion().getName();
                day = json.has("day") ? json.getString("day") : "Day";
                night = json.has("night") ? json.getString("night") : "Night";
                morning = json.has("morning") ? json.getString("morning") : "Morning";
                evening = json.has("evening") ? json.getString("evening") : "Evening";
                lineCount = json.has("countLine") ? json.getInt("countLine") : 58;
                useRandomLine2 = json.has("useRandomLine2") ? json.getBoolean("useRandomLine2") : false;
                randomLine2 = json.has("randomLine2") ? json.getJSONArray("randomLine2") : new JSONArray().put("Hello, world!").put("Hi!");
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
