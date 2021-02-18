package me.lightdream.grapplinghook;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Grapplinghook extends JavaPlugin {

    public static Grapplinghook INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        this.getCommand("grapp").setExecutor(new GrappCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
