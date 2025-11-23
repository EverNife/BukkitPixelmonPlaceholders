package br.com.finalcraft.bukkitpixelmonplaceholders.common.logging;

import br.com.finalcraft.evernifecore.logger.ECLogger;
import br.com.finalcraft.evernifecore.logger.debug.IDebugModule;
import br.com.finalcraft.evernifecore.util.FCReflectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public enum BPPDebugModule implements IDebugModule<Plugin, BPPDebugModule> {
    POKEMON_FILTER("Logs related to the Pokemon Filtering", true),
    ;

    private static Plugin bppPlugin;

    private static ECLogger<BPPDebugModule> ecLogger;

    private final String comment;
    private final boolean enabledByDefault;
    private boolean enabled = true;

    BPPDebugModule() {
        this.comment = null;
        this.enabledByDefault = false;
    }

    BPPDebugModule(String comment) {
        this.comment = comment;
        this.enabledByDefault = false;
    }

    BPPDebugModule(String comment, boolean enabledByDefault) {
        this.comment = comment;
        this.enabledByDefault = enabledByDefault;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isEnabledByDefault() {
        return enabledByDefault;
    }

    @Override
    public Plugin getPlugin() {
        if (bppPlugin == null) {
            bppPlugin = Bukkit.getPluginManager().getPlugin("BukkitPixelmonPlaceholders");
        }
        return bppPlugin;
    }

    @Override
    public ECLogger<BPPDebugModule> getLog() {
        if (ecLogger == null){
            getPlugin(); //Ensure plugin is loaded
            ecLogger = (ECLogger<BPPDebugModule>) FCReflectionUtil.getField(bppPlugin.getClass(), "ecLogger").get(bppPlugin);
        }
        return ecLogger;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String getComment() {
        return comment;
    }
}
