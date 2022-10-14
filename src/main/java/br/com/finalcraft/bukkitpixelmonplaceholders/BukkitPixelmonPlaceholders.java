package br.com.finalcraft.bukkitpixelmonplaceholders;

import br.com.finalcraft.bukkitpixelmonplaceholders.commands.CommandRegisterer;
import br.com.finalcraft.bukkitpixelmonplaceholders.placeholder.PixelmonPlaceholders;
import br.com.finalcraft.evernifecore.ecplugin.annotations.ECPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@ECPlugin(
        bstatsID = "16405",
        spigotID = "105174"
)
public class BukkitPixelmonPlaceholders extends JavaPlugin{

    public static BukkitPixelmonPlaceholders instance;

    public static void info(String msg){
        instance.getLogger().info("[Info] " + msg);
    }

    public static void debug(String msg){
        instance.getLogger().info("[Debug] " + msg);
    }

    @Override
    public void onEnable() {
        instance = this;

        CommandRegisterer.registerCommands(this);

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            info("Hooking into PlaceholderAPI");
            PixelmonPlaceholders.registerToPAPI();
        }
    }

    @Override
    public void onDisable() {
        //
    }

    @ECPlugin.Reload
    public void onReload(){
        //
    }

}
