package br.com.finalcraft.bukkitpixelmonplaceholders.pixelmon.v1_16_5.reforged;

import br.com.finalcraft.evernifecore.config.playerdata.IPlayerData;
import br.com.finalcraft.evernifecore.placeholder.replacer.RegexReplacer;
import br.com.finalcraft.evernifecore.time.FCTimeFrame;
import com.pixelmonmod.pixelmon.api.pokemon.species.Pokedex;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.spawning.PixelmonSpawning;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

//This class has an influence from https://github.com/EnvyWare/ForgePlaceholderAPI-Extensions
public class ReforgedPlayerParser_1_16_5 {

    public static RegexReplacer<IPlayerData> createMainReplacer(){
        final RegexReplacer<IPlayerData> MAIN_REPLACER = new RegexReplacer();
        final DecimalFormat PERCENTAGE = new DecimalFormat("#0.##");

        MAIN_REPLACER.addParser(
                "party_size_all",
                "Number of Pokemons In a player's party",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId());
                    return party.countAll();
                }
        );

        MAIN_REPLACER.addParser(
                "party_size_ableonly",
                "Number of Able Pokemons In a player's party",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId());
                    return party.countAblePokemon();
                }
        );

        MAIN_REPLACER.addParser(
                "party_size_eggonly",
                "Number of Egg Pokemons In a player's party",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId());
                    return party.countEggs();
                }
        );

        MAIN_REPLACER.addParser(
                "party_size_pokemononly",
                "Number of Normal Pokemons In a player's party",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId());
                    return party.countPokemon();
                }
        );

        MAIN_REPLACER.addParser(
                "dex_count_caught",
                "Dex Count Caught",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId());
                    return party.playerPokedex.countCaught();
                }
        );

        MAIN_REPLACER.addParser(
                "dex_count_seen",
                "Dex Count Seen",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId());
                    return party.playerPokedex.countSeen();
                }
        );

        MAIN_REPLACER.addParser(
                "dex_percentage",
                "Dex Percentage",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId());
                    return PERCENTAGE.format((party.playerPokedex.countCaught() / (Pokedex.pokedexSize * 1D)) * 100D) + "%";
                }
        );

        MAIN_REPLACER.addParser(
                "dex_total_size",
                "Dex Size",
                player -> {
                     return Pokedex.pokedexSize;
                }
        );

        MAIN_REPLACER.addParser(
                "losses",
                "Player's Losses",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId());
                    return party.stats != null ? party.stats.getLosses() : 0;
                }
        );

        MAIN_REPLACER.addParser(
                "wins",
                "Player's Wins",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId());
                    return party.stats != null ? party.stats.getWins() : 0;
                }
        );

        MAIN_REPLACER.addParser(
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

        MAIN_REPLACER.addParser(
                "average_level",
                "Avarage Level of the Party",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId());
                    return party.getAverageLevel();
                }
        );


        MAIN_REPLACER.addParser(
                "lowest_level",
                "Lowest Level of the Party",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId());
                    return party.getLowestLevel();
                }
        );

        MAIN_REPLACER.addParser(
                "highest_level",
                "Highest Level of the Party",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId());
                    return party.getHighestLevel();
                }
        );

        MAIN_REPLACER.addParser(
                "next_legendary",
                "The seconds until the next Legendary Spawn",
                player -> {
                    return TimeUnit.MILLISECONDS.toSeconds(PixelmonSpawning.legendarySpawner.nextSpawnTime - System.currentTimeMillis());
                }
        );

        MAIN_REPLACER.addParser(
                "next_legendary_formatted",
                "The formatted time until the next Legendary Spawn",
                player -> {
                    return FCTimeFrame.of(
                            PixelmonSpawning.legendarySpawner.nextSpawnTime - System.currentTimeMillis()
                    );
                }
        );


        return MAIN_REPLACER;
    }

}
