package br.com.finalcraft.bukkitpixelmonplaceholders.pixelmon.v1_12_R1.reforged;

import br.com.finalcraft.evernifecore.config.playerdata.IPlayerData;
import br.com.finalcraft.evernifecore.placeholder.replacer.RegexReplacer;

import java.text.DecimalFormat;

public class ReforgedPlayerParser_1_12_2 {

    public static RegexReplacer<IPlayerData> createMainReplacer(){
        final RegexReplacer<IPlayerData> MAIN_REPLACER = new RegexReplacer<>();
        final DecimalFormat PERCENTAGE = new DecimalFormat("#0.##");

        return MAIN_REPLACER;
    }

}
