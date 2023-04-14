package br.com.finalcraft.bukkitpixelmonplaceholders.integration;

import br.com.finalcraft.bukkitpixelmonplaceholders.BukkitPixelmonPlaceholders;
import br.com.finalcraft.evernifecore.util.FCReflectionUtil;
import org.bukkit.scheduler.BukkitRunnable;

public class ExternalCompatibilityChecker {

    public static void checkForOtherPlugins() {
        new BukkitRunnable(){
            @Override
            public void run() {
                if (FCReflectionUtil.isClassLoaded("me.neovitalism.pixelmonextension.PixelmonExtension")){
                    for (int i = 0; i < 6; i++) {
                        BukkitPixelmonPlaceholders.getLog().severe("It seems that you are using Neovitalism's PixelmonExtension at 'plugins/PlaceholderAPI/expansions/', which is not compatible with this plugin! I suggest you to remove one of them!");
                    }
                }
            }
        }.runTaskLater(BukkitPixelmonPlaceholders.instance, 1L);
    }

}
