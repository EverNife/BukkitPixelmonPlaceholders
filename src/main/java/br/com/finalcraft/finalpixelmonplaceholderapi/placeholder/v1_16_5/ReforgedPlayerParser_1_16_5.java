package br.com.finalcraft.finalpixelmonplaceholderapi.placeholder.v1_16_5;

import br.com.finalcraft.evernifecore.config.playerdata.IPlayerData;
import br.com.finalcraft.evernifecore.placeholder.replacer.RegexReplacer;
import com.pixelmonmod.pixelmon.api.pokemon.species.Pokedex;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;

import java.text.DecimalFormat;

//This class has an influence from https://github.com/EnvyWare/ForgePlaceholderAPI-Extensions
public class ReforgedPlayerParser_1_16_5 {

    public static RegexReplacer<IPlayerData> createMainReplacer(){
        final RegexReplacer<IPlayerData> MAIN_REPLACER = new RegexReplacer<>();
        final DecimalFormat PERCENTAGE = new DecimalFormat("#0.##");

        MAIN_REPLACER.addMappedParser(
                "dex_count",
                "Dex Count",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId());
                    return party.playerPokedex.countCaught();
                }
        );

        MAIN_REPLACER.addMappedParser(
                "dex_percentage",
                "Dex Percentage",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId());
                    return PERCENTAGE.format((party.playerPokedex.countCaught() / (Pokedex.pokedexSize * 1D)) * 100D) + "%";
                }
        );

        MAIN_REPLACER.addMappedParser(
                "dexsize",
                "Dex Size",
                player -> {
                     return Pokedex.pokedexSize;
                }
        );

        MAIN_REPLACER.addMappedParser(
                "losses",
                "Player's Losses",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId());
                    return party.stats != null ? party.stats.getLosses() : 0;
                }
        );

        MAIN_REPLACER.addMappedParser(
                "wins",
                "Player's Wins",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId());
                    return party.stats != null ? party.stats.getWins() : 0;
                }
        );

        MAIN_REPLACER.addMappedParser(
                "kdr",
                "Player's Kill Death Ratio",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId());

                    if (party.stats == null || party.stats.getLosses() == 0) {
                        return "0";
                    }

                    return PERCENTAGE.format(((party.stats.getWins() * 1D) / party.stats.getLosses()) * 100D);
                }
        );

        return MAIN_REPLACER;
    }

}
