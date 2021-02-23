package me.lightdream.grapplinghook;

import net.md_5.bungee.api.ChatColor;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Utils {

    public static String color(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static void createFile(String path){

        try {
            FileUtils.copyInputStreamToFile(Grapplinghook.INSTANCE.getResource(path), new File(Grapplinghook.INSTANCE.getDataFolder(), path));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FileConfiguration loadFile(String path) {

        File file = new File(Bukkit.getServer().getPluginManager().getPlugin("Grapplinghook").getDataFolder(), path);
        if (file.exists()) {
            return YamlConfiguration.loadConfiguration(file);
        } else {
            createFile(path);
            return loadFile(path);
        }
    }


}
