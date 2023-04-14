package br.com.finalcraft.bukkitpixelmonplaceholders.pixelmon.v1_12_2.reforged;

import br.com.finalcraft.evernifecore.config.playerdata.IPlayerData;
import br.com.finalcraft.evernifecore.placeholder.replacer.RegexReplacer;

import java.text.DecimalFormat;

public class ReforgedPixelmonParser_1_12_2 {

    public static RegexReplacer<Object> createPokemonReplacer(){
        final RegexReplacer<Object> POKEMON_REPLACER = new RegexReplacer<>();
        final DecimalFormat PERCENTAGE = new DecimalFormat("#0.##");

        return POKEMON_REPLACER;
    }

}
