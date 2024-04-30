package br.com.finalcraft.bukkitpixelmonplaceholders.integration;

import br.com.finalcraft.bukkitpixelmonplaceholders.BukkitPixelmonPlaceholders;
import br.com.finalcraft.bukkitpixelmonplaceholders.common.settings.BPPSettings;
import br.com.finalcraft.evernifecore.util.FCReflectionUtil;
import org.bukkit.scheduler.BukkitRunnable;

public class ExternalCompatibilityChecker {

    public static void checkForOtherPlugins() {
        if (BPPSettings.BPP_PLACEHOLDER_PREFIX.equals("pixelmon")){
            new BukkitRunnable(){
                @Override
                public void run() {
                    if (FCReflectionUtil.isClassLoaded("me.neovitalism.pixelmonextension.PixelmonExtension")){
                        for (int i = 0; i < 6; i++) {
                            BukkitPixelmonPlaceholders.getLog().severe("It seems that you are using Neovitalism's PixelmonExtension at 'plugins/PlaceholderAPI/expansions/'! " +
                                    "I suggest you change default's BukkitPixelmonPlaceholder's prefix from 'pixelmon' to something else at it's config to prevent incompatibility!!");
                        }
                    }
                }
            }.runTaskLater(BukkitPixelmonPlaceholders.instance, 1L);
        }

    }

}
