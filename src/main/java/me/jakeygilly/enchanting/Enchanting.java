package me.jakeygilly.enchanting;

import me.jakeygilly.enchanting.Listeners.OnInventoryClick;
import org.bukkit.plugin.java.JavaPlugin;

public final class Enchanting extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new OnInventoryClick(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
