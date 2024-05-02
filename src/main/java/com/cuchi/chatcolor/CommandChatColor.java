package com.cuchi.chatcolor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandChatColor implements CommandExecutor {
    ColorGui colorGuiInstance;

    public CommandChatColor(ColorGui colorGui) {
        colorGuiInstance = colorGui;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();
            assert player != null;
            colorGuiInstance.openInventory(player);
            return true;
        }

        return false;
    }
}
