package com.cuchi.chatcolor;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ChatListener implements Listener {
    @EventHandler
    public void onChat(AsyncChatEvent event) {
        event.renderer((source, sourceDisplayName, message, viewer) -> {
            NamespacedKey key = new NamespacedKey(ChatColorMain.getInstance(), "chatcolor");
            Player player = event.getPlayer();
            PersistentDataContainer playerDataContainer = player.getPersistentDataContainer();

            String value = playerDataContainer.get(key, PersistentDataType.STRING);

            sourceDisplayName = sourceDisplayName.color(value != null ? TextColor.fromHexString(value) : null);

            Style defaultStyle = Style.style(TextColor.color(255, 255, 255));
            return sourceDisplayName
                    .append(Component.text(": ", defaultStyle)
                    .append(message.style(defaultStyle)));
        });
    }
}
