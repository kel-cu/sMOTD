package og.kel.motd;

public class Utils {
    public static String[] formatCodes =  {
            "§4",
            "§c",
            "§6",
            "§e",
            "§z",
            "§a",
            "§b",
            "§3",
            "§1",
            "§9",
            "§d",
            "§5",
            "§f",
            "§7",
            "§8",
            "§0",
            "§r",
            "§l",
            "§o",
            "§n",
            "§m",
            "§k"
    };
    public static String clearFormatCodes(String originalText){
        String clearFormatText = originalText;
        for(int i=0;i<formatCodes.length;i++){
            clearFormatText = clearFormatText.replace(formatCodes[i], "").replace(formatCodes[i].replace("§", "&"), "");
        }
        return clearFormatText;
    }
    public static String fixFormatCodes(String originalText){
        String fixFormatText = originalText;
        for(int i=0;i<formatCodes.length;i++){
            fixFormatText = fixFormatText.replace(formatCodes[i].replace("§", "&"), formatCodes[i]);
        }
        return fixFormatText;
    }
}
