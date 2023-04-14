package br.com.finalcraft.bukkitpixelmonplaceholders.placeholder;

import br.com.finalcraft.bukkitpixelmonplaceholders.BukkitPixelmonPlaceholders;
import br.com.finalcraft.bukkitpixelmonplaceholders.common.factory.PixelmonPlaceholderFactory;
import br.com.finalcraft.evernifecore.config.playerdata.IPlayerData;
import br.com.finalcraft.evernifecore.config.playerdata.PlayerData;
import br.com.finalcraft.evernifecore.integration.placeholders.PAPIIntegration;
import br.com.finalcraft.evernifecore.placeholder.replacer.RegexReplacer;


public class PixelmonPlaceholders {

    public static PixelmonPlaceholderFactory<?> FACTORY = PixelmonPlaceholderFactory.create();

    public static RegexReplacer<IPlayerData> MAIN_REPLACER = FACTORY.getMainReplacer();

    public static RegexReplacer<Object> POKEMON_REPLACER = (RegexReplacer<Object>) FACTORY.getPokemonReplacer(); //previously was "RegexReplacer<Object>", but now it can receive both the Pokemon or the PokemonWrapper as argument!

    public static void registerToPAPI(){
        RegexReplacer<PlayerData> PAPI_REPLACER = PAPIIntegration.createPlaceholderIntegration(BukkitPixelmonPlaceholders.instance, "pixelmon", PlayerData.class);
        PAPI_REPLACER.setDefaultParser((playerData, placeholder) -> {
            // In here, '%pixelmon_custom_placeholder%' turns into '%custom_placeholder%'
            return MAIN_REPLACER.apply("%" + placeholder + "%", playerData);
        });
    }

}
