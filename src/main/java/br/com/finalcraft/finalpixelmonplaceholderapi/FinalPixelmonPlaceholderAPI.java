package br.com.finalcraft.finalpixelmonplaceholderapi;

import br.com.finalcraft.evernifecore.ecplugin.annotations.ECPlugin;
import br.com.finalcraft.finalpixelmonplaceholderapi.placeholder.PixelmonPlaceholders;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@ECPlugin(
        bstatsID = "15984"
)
public class FinalPixelmonPlaceholderAPI extends JavaPlugin{

    public static FinalPixelmonPlaceholderAPI instance;

    public static void info(String msg){
        instance.getLogger().info("[Info] " + msg);
    }

    public static void debug(String msg){
        instance.getLogger().info("[Debug] " + msg);
    }

    @Override
    public void onEnable() {
        instance = this;

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
