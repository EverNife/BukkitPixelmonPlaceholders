package br.com.finalcraft.bukkitpixelmonplaceholders.placeholder;

import br.com.finalcraft.bukkitpixelmonplaceholders.BukkitPixelmonPlaceholders;
import br.com.finalcraft.bukkitpixelmonplaceholders.pixelmon.v1_16_5.reforged.ReforgedPlaceholderFactory_1_16_5;
import br.com.finalcraft.evernifecore.config.playerdata.IPlayerData;
import br.com.finalcraft.evernifecore.config.playerdata.PlayerData;
import br.com.finalcraft.evernifecore.integration.placeholders.PAPIIntegration;
import br.com.finalcraft.evernifecore.placeholder.replacer.RegexReplacer;
import br.com.finalcraft.evernifecore.version.MCVersion;


public class PixelmonPlaceholders {

    public static RegexReplacer<IPlayerData> MAIN_REPLACER;
    public static RegexReplacer<Object> POKEMON_REPLACER; //previously was "RegexReplacer<Object>", but now it can receive both the Pokemon or the PokemonWrapper as argument!

    static {
        if (MCVersion.isEqual(MCVersion.v1_16)){
            MAIN_REPLACER = ReforgedPlaceholderFactory_1_16_5.MAIN_REPLACER;
            POKEMON_REPLACER = (RegexReplacer<Object>) (Object) ReforgedPlaceholderFactory_1_16_5.POKEMON_REPLACER;
        }
    }

    public static void registerToPAPI(){
        RegexReplacer<PlayerData> PAPI_REPLACER = PAPIIntegration.createPlaceholderIntegration(BukkitPixelmonPlaceholders.instance, "pixelmon", PlayerData.class);
        PAPI_REPLACER.setDefaultParser((playerData, placeholder) -> {
            // In here, '%pixelmon_custom_placeholder%' turns into '%custom_placeholder%'
            return MAIN_REPLACER.apply("%" + placeholder + "%", playerData);
        });
    }

}
