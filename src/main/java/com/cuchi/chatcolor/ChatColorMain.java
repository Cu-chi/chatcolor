package com.cuchi.chatcolor;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChatColorMain extends JavaPlugin {
    private static ChatColorMain instance;

    @Override
    public void onEnable() {
        ColorGui colorGui = new ColorGui();

        PluginCommand command = getCommand("chatcolor");
        if (command != null) command.setExecutor(new CommandChatColor(colorGui));

        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(colorGui, this);

        instance = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static ChatColorMain getInstance() {
        return instance;
    }
}
