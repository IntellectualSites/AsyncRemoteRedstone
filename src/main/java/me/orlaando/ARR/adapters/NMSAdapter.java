package me.orlaando.ARR.adapters;

import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NMSAdapter {

    public static String getMinecraftVersion() {
        Matcher matcher = Pattern.compile("(\\(MC: )([\\d\\.]+)(\\))").matcher(Bukkit.getVersion());
        if (matcher.find()) {
            return matcher.group(2);
        }
        return null;
    }


}
