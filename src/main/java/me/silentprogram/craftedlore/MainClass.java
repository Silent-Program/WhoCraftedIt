package me.silentprogram.craftedlore;

import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import me.silentprogram.craftedlore.gui.ConfigGui;
import me.silentprogram.craftedlore.listeners.Listeners;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class MainClass extends JavaPlugin {
    public ConfigGui configGui;
    public ChestGui mainGui;
    public ChestGui blocksGui;
    Listeners listeners;
    public void onEnable(){
        saveDefaultConfig();
        configGui = new ConfigGui(this);
        listeners = new Listeners(this);
        reloadGuis();
        Bukkit.getServer().getPluginManager().registerEvents(listeners, this);
    }
    public void onDisable(){

    }
    public String checkMessage(String msg, Player plr, Location loc, ItemStack item) {
        int locX = (int) loc.getX();
        int locZ = (int) loc.getZ();
        int locY = (int) loc.getY();

        return msg.replace("%player%", plr.getName()).replace("%location%", locX + ", " + locZ + ", " + locY)
                .replace("%amount%", item.getAmount() + "").replace("%item%", item.getType().toString());
    }
    public void reloadGuis() {
        mainGui = configGui.createMainGui();
        blocksGui = configGui.createBlocksGui();
    }
}
