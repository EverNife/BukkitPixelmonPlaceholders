package br.com.finalcraft.bukkitpixelmonplaceholders;

import br.com.finalcraft.bukkitpixelmonplaceholders.commands.CommandRegisterer;
import br.com.finalcraft.bukkitpixelmonplaceholders.config.ConfigManager;
import br.com.finalcraft.bukkitpixelmonplaceholders.integration.ExternalCompatibilityChecker;
import br.com.finalcraft.bukkitpixelmonplaceholders.placeholder.PixelmonPlaceholders;
import br.com.finalcraft.evernifecore.ecplugin.annotations.ECPlugin;
import br.com.finalcraft.evernifecore.logger.ECLogger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@ECPlugin(
        bstatsID = "16405",
        spigotID = "105174"
)
public class BukkitPixelmonPlaceholders extends JavaPlugin{

    public static BukkitPixelmonPlaceholders instance; {instance = this;}

    private final ECLogger ecLogger = new ECLogger(this);

    public static ECLogger getLog() {
        return instance.ecLogger;
    }

    @Override
    public void onEnable() {
        getLogger().info("§aLoading Configuration...");
        ConfigManager.initialize(this);

        getLogger().info("§aRegistering Commands...");
        CommandRegisterer.registerCommands(this);

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            getLogger().info("Hooking into PlaceholderAPI");
            PixelmonPlaceholders.registerToPAPI();
        }

        ExternalCompatibilityChecker.checkForOtherPlugins();
    }

    @Override
    public void onDisable() {
        //
    }

    @ECPlugin.Reload
    public void onReload(){
        ConfigManager.initialize(this);
    }

}
