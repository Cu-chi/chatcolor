package com.cuchi.chatcolor;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;

public class ColorGui implements Listener {
    private final Inventory inv;

    List<Object[]> colors = Arrays.asList(
            new Object[]{Material.RED_CONCRETE, Component.text("Red"), "#ff0000"},
            new Object[]{Material.BLUE_CONCRETE, Component.text("Blue"), "#5555ff"},
            new Object[]{Material.GREEN_CONCRETE, Component.text("Green"), "#55ff55"},
            new Object[]{Material.YELLOW_CONCRETE, Component.text("Yellow"), "#ffff55"},
            new Object[]{Material.PURPLE_CONCRETE, Component.text("Purple"), "#aa00aa"},
            new Object[]{Material.CYAN_CONCRETE, Component.text("Cyan"), "#00ffff"},
            new Object[]{Material.ORANGE_CONCRETE, Component.text("Orange"), "#ff9628"},
            new Object[]{Material.BLACK_CONCRETE, Component.text("Black"), "#000000"},
            new Object[]{Material.WHITE_CONCRETE, Component.text("White"), "#ffffff"}
    );

    public ColorGui() {
        // Create a new inventory, with no owner (as this isn't a real inventory), a size of nine, called example
        inv = Bukkit.createInventory(null, 9, Component.text("Chat Color"));

        // Put the items into the inventory
        initializeItems();
    }

    public void initializeItems() {
        for (Object[] data : colors) {
            Component itemName = (Component) data[1];
            inv.addItem(createGuiItem((Material) data[0], itemName.color(TextColor.fromHexString((String) data[2]))));
        }
    }

    // Nice little method to create a gui item with a custom name, and description
    protected ItemStack createGuiItem(Material material, Component name, Component... lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(name);
        itemMeta.lore(Arrays.asList(lore));
        item.setItemMeta(itemMeta);
        return item;
    }

    // You can open the inventory with this
    public void openInventory(HumanEntity ent) {
        ent.openInventory(inv);
    }

    // Check for clicks on items
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getInventory().equals(inv))) return;

        e.setCancelled(true);

        int indexed = e.getRawSlot();
        if (indexed >= 9) return; // then it's not a color from the menu

        final Player player = (Player) e.getWhoClicked();

        NamespacedKey key = new NamespacedKey(ChatColorMain.getInstance(), "chatcolor");

        String newHexValue = (String) colors.get(indexed)[2];
        Component colorName = ((Component) colors.get(indexed)[1]).color(TextColor.fromHexString(newHexValue));

        PersistentDataContainer playerDataContainer = player.getPersistentDataContainer();
        playerDataContainer.set(key, PersistentDataType.STRING, newHexValue);

        player.sendMessage(Component.text("Changed the color of your messages to: [").append(colorName).append(Component.text("]")));
    }

    // Cancel dragging in our inventory
    @EventHandler
    public void onInventoryClick(InventoryDragEvent e) {
        if (e.getInventory().equals(inv)) {
            e.setCancelled(true);
        }
    }
}
