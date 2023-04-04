package me.silentprogram.craftedlore.gui;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import me.silentprogram.craftedlore.MainClass;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ConfigGui {

    private MainClass plugin;

    public ConfigGui(MainClass plugin) {
        this.plugin = plugin;
    }

    public ChestGui createMainGui() {
        ChestGui gui = new ChestGui(3, "Config Settings");

        gui.setOnGlobalClick(event -> event.setCancelled(true));

        OutlinePane background = new OutlinePane(0, 0, 9, 3, Pane.Priority.LOWEST);
        ItemStack backGroundItem = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta backGroundItemMeta = backGroundItem.getItemMeta();
        backGroundItemMeta.setDisplayName(" ");
        backGroundItem.setItemMeta(backGroundItemMeta);
        background.addItem(new GuiItem(new ItemStack(backGroundItem)));
        background.setRepeat(true);

        gui.addPane(background);

        StaticPane navigationPane = new StaticPane(3, 1, 3, 1);

        ItemStack shop = new ItemStack(Material.CHEST);
        ItemMeta shopMeta = shop.getItemMeta();
        List<String> shopLore = new ArrayList<String>();
        shopLore.add(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Click on an empty space to add items!");
        shopMeta.setLore(shopLore);
        shopMeta.setDisplayName(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Add Items");
        shop.setItemMeta(shopMeta);

        navigationPane.addItem(new GuiItem(shop, event -> {
            //plugin.blocksGui.show(event.getWhoClicked());
        }), 0, 0);

        ItemStack beacon = new ItemStack(Material.REDSTONE_TORCH);
        ItemMeta beaconMeta = beacon.getItemMeta();
        beaconMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Settings");
        beacon.setItemMeta(beaconMeta);

        navigationPane.addItem(new GuiItem(beacon, event -> {
            Player plr = (Player) event.getWhoClicked();
            //plugin.settingsGui.show(plr);
        }), 1, 0);

        ItemStack bed = new ItemStack(Material.NAME_TAG);
        ItemMeta bedMeta = bed.getItemMeta();
        bedMeta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.MAGIC + "NBT ITEMS");
        List<String> bedLore = new ArrayList<String>();
        bedLore.add(ChatColor.DARK_RED + "" + ChatColor.BOLD + "NBT Support coming soon!");
        bedMeta.setLore(bedLore);
        bed.setItemMeta(bedMeta);

        navigationPane.addItem(new GuiItem(bed, event -> {
            Player plr = (Player) event.getWhoClicked();
            plr.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "NBT Support coming soon!");
        }), 2, 0);

        gui.addPane(navigationPane);

        return gui;

    }

    public ChestGui createBlocksGui() {
        ChestGui gui = new ChestGui(3, "Blocks");

        OutlinePane background = new OutlinePane(0, 0, 9, 3, Pane.Priority.LOWEST);
        ItemStack backGroundItem = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta backGroundItemMeta = backGroundItem.getItemMeta();
        backGroundItemMeta.setDisplayName(" ");
        backGroundItem.setItemMeta(backGroundItemMeta);
        background.addItem(new GuiItem(backGroundItem, event -> {
            event.setCancelled(true);
            ItemStack item = event.getCursor();
            if (item.getType() == null)
                return;
            if (item.getType() == Material.AIR)
                return;
            plugin.getConfig().set("blocks." + item.getType().toString(), item.getAmount());
            plugin.saveConfig();
            item.setAmount(0);
            plugin.reloadGuis();
            plugin.blocksGui.show(event.getWhoClicked());
        }));
        background.setRepeat(true);

        gui.addPane(background);

        OutlinePane navigationPane = new OutlinePane(0, 0, 9, 3);
        for (String i : plugin.getConfig().getStringList("blocks")) {
            Material mat = Material.matchMaterial(i);
            ItemStack item = new ItemStack(mat);
            List<String> lore = new ArrayList<String>();
            lore.add("");
            lore.add(ChatColor.RED + "" + ChatColor.BOLD + "Right-click to remove item.");
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
            item.setAmount(plugin.getConfig().getInt("blocks." + i));
            navigationPane.addItem(new GuiItem(item, event -> {
                event.setCancelled(true);
                if (event.isRightClick()) {
                    plugin.getConfig().set("blocks." + i, null);
                    plugin.saveConfig();
                    plugin.reloadGuis();
                    plugin.blocksGui.show(event.getWhoClicked());
                }
            }));
        }

        gui.addPane(navigationPane);

        return gui;

    }
}