package og.kel.motd;

import lombok.Getter;
import net.minecraft.SharedConstants;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config {

    // Чтобы можно было менять в 1 месте и не вспоминать где нужно изменить.
    private final int defaultLineCount = 58;
    private final Path configFile = Main.getServer().getRunDirectory().toPath().resolve("config/smotd.json");
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
    private int lineCount = defaultLineCount;
    @Getter
    private Boolean useRandomLine2 = false;
    @Getter
    private JSONArray randomLine2 = new JSONArray().put("Hello, world!").put("Hi!");

    public Config() {
        if (!configFile.toFile().exists()) {
            save();
            Main.getLog().info(Main.prefix + "Please reload server for load my config");
            Main.getServer().exit();
        } else {
            try {
                JSONObject json = new JSONObject(Files.readString(configFile, StandardCharsets.UTF_8));
                for (String key : json.keySet()) {
                    switch (key) {
                        case "line1" -> line1 = json.getString(key);
                        case "line2" -> line2 = json.getString(key);
                        case "day" -> day = json.getString(key);
                        case "night" -> night = json.getString(key);
                        case "morning" -> morning = json.getString(key);
                        case "evening" -> evening = json.getString(key);
                        case "countLine" -> lineCount = json.getInt(key);
                        case "useRandomLine2" -> useRandomLine2 = json.getBoolean(key);
                        case "randomLine2" -> randomLine2 = json.getJSONArray(key);
                        default -> {
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void save() {
        JSONObject json = new JSONObject();
        json.put("line1", "A Minecraft Server")
                .put("line2", "Minecraft " + SharedConstants.getGameVersion().getName())
                .put("day", "Day")
                .put("night", "Night")
                .put("morning", "Morning")
                .put("evening", "Evening")
                .put("countLine", defaultLineCount)
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