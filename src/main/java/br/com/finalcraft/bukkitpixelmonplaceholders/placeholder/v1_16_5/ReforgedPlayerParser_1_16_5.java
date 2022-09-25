package br.com.finalcraft.bukkitpixelmonplaceholders.placeholder.v1_16_5;

import br.com.finalcraft.evernifecore.config.playerdata.IPlayerData;
import br.com.finalcraft.evernifecore.placeholder.replacer.RegexReplacer;
import com.pixelmonmod.pixelmon.api.pokemon.species.Pokedex;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.spawning.PixelmonSpawning;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

//This class has an influence from https://github.com/EnvyWare/ForgePlaceholderAPI-Extensions
public class ReforgedPlayerParser_1_16_5 {

    public static RegexReplacer<IPlayerData> createMainReplacer(){
        final RegexReplacer<IPlayerData> MAIN_REPLACER = new RegexReplacer<>();
        final DecimalFormat PERCENTAGE = new DecimalFormat("#0.##");

        MAIN_REPLACER.addMappedParser(
                "party_size_all",
                "Number of Pokemons In a player's party",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId());
                    return party.countAll();
                }
        );

        MAIN_REPLACER.addMappedParser(
                "party_size_ableonly",
                "Number of Able Pokemons In a player's party",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId());
                    return party.countAblePokemon();
                }
        );

        MAIN_REPLACER.addMappedParser(
                "party_size_eggonly",
                "Number of Egg Pokemons In a player's party",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId());
                    return party.countEggs();
                }
        );

        MAIN_REPLACER.addMappedParser(
                "party_size_pokemononly",
                "Number of Normal Pokemons In a player's party",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId());
                    return party.countPokemon();
                }
        );

        MAIN_REPLACER.addMappedParser(
                "dex_count_caught",
                "Dex Count Caught",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId());
                    return party.playerPokedex.countCaught();
                }
        );

        MAIN_REPLACER.addMappedParser(
                "dex_count_seen",
                "Dex Count Seen",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId());
                    return party.playerPokedex.countSeen();
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
                "dex_total_size",
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

        MAIN_REPLACER.addMappedParser(
                "average_level",
                "Avarage Level of the Party",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId());
                    return party.getAverageLevel();
                }
        );


        MAIN_REPLACER.addMappedParser(
                "lowest_level",
                "Lowest Level of the Party",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId());
                    return party.getLowestLevel();
                }
        );

        MAIN_REPLACER.addMappedParser(
                "highest_level",
                "Highest Level of the Party",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId());
                    return party.getHighestLevel();
                }
        );

        MAIN_REPLACER.addMappedParser(
                "next_legendary",
                "The seconds until the next Legendary Spawn",
                player -> {
                    return TimeUnit.MILLISECONDS.toSeconds(PixelmonSpawning.legendarySpawner.nextSpawnTime - System.currentTimeMillis());
                }
        );


        return MAIN_REPLACER;
    }

}
