package br.com.finalcraft.bukkitpixelmonplaceholders.config;

import br.com.finalcraft.bukkitpixelmonplaceholders.BukkitPixelmonPlaceholders;
import br.com.finalcraft.bukkitpixelmonplaceholders.common.placeholderremapper.PlaceholderRemapper;
import br.com.finalcraft.bukkitpixelmonplaceholders.common.settings.BPPSettings;
import br.com.finalcraft.evernifecore.config.Config;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {

    private static Config mainConfig;
    private static Config remapperConfig;

    public static Config getMainConfig(){
        return mainConfig;
    }

    public static Config getRemapperConfig(){
        return remapperConfig;
    }

    public static void initialize(JavaPlugin instance){
        mainConfig = new Config(instance,"config.yml");
        remapperConfig = new Config(instance,"Remappings.yml");

        BPPSettings.initialize(BukkitPixelmonPlaceholders.getLog(), mainConfig);
        PlaceholderRemapper.initialize(BukkitPixelmonPlaceholders.getLog(), remapperConfig);
    }

}
