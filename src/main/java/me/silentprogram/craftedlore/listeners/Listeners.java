package me.silentprogram.craftedlore.listeners;

import me.silentprogram.craftedlore.MainClass;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Listeners implements Listener {
    MainClass plugin;

    public Listeners(MainClass plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerCraft(CraftItemEvent event) {
        if (plugin.getConfig().getBoolean("enableblocks")) {
            addSomelore((Player) event.getWhoClicked(), event.getCurrentItem(), event);
        } else {
            addAllLore((Player) event.getWhoClicked(), event.getCurrentItem(), event);
        }
    }

    public void addAllLore(Player plr, ItemStack item, CraftItemEvent event) {
        //int amount = checkItems(event);
        ItemMeta itemMeta = item.getItemMeta();

        List<String> oldLore = plugin.getConfig().getStringList("craftedmessage");
        List<String> newLore = new ArrayList<String>();
        for (String i : oldLore) {
            i = plugin.checkMessage(i, plr, plr.getLocation(), item);
            newLore.add(ChatColor.translateAlternateColorCodes('&', i));
        }
        itemMeta.setLore(newLore);
        item.setItemMeta(itemMeta);
        //item.setAmount(amount);
    }

    public void addSomelore(Player plr, ItemStack item, CraftItemEvent event) {
        //int amount = checkItems(event);
        if (plugin.getConfig().getList("blocks").contains(item.getType().toString())) {
            ItemMeta itemMeta = item.getItemMeta();

            List<String> oldLore = plugin.getConfig().getStringList("craftedmessage");
            List<String> newLore = new ArrayList<String>();
            for (String i : oldLore) {
                i = plugin.checkMessage(i, plr, plr.getLocation(), item);
                newLore.add(ChatColor.translateAlternateColorCodes('&', i));
            }
            itemMeta.setLore(newLore);
            item.setItemMeta(itemMeta);
            //item.setAmount(amount);
            //return item;
        }
    }

    public int checkItems(CraftItemEvent event) {
        int itemsChecked = 0;
        int possibleCreations = 1;
        if (event.isShiftClick())
            for (ItemStack item : event.getInventory().getContents()) {
                if (item != null && !item.getType().equals(Material.AIR)) {
                    if (itemsChecked == 0)
                        possibleCreations = item.getAmount();
                    else
                        possibleCreations = Math.min(possibleCreations, item.getAmount());

                    itemsChecked++;
                }
            }
        int amountOfItems = event.getRecipe().getResult().getAmount() * possibleCreations;
        return amountOfItems;
    }
}
