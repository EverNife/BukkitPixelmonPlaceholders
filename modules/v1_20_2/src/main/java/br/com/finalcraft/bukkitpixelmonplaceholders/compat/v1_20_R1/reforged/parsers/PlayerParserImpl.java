package br.com.finalcraft.bukkitpixelmonplaceholders.compat.v1_20_R1.reforged.parsers;

import br.com.finalcraft.evernifecore.config.playerdata.IPlayerData;
import br.com.finalcraft.evernifecore.placeholder.replacer.RegexReplacer;
import br.com.finalcraft.evernifecore.time.FCTimeFrame;
import com.pixelmonmod.pixelmon.api.pokemon.species.Pokedex;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.spawning.PixelmonSpawning;
import com.pixelmonmod.pixelmon.storage.playerData.CaptureCombo;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

//This class has an influence from https://github.com/EnvyWare/ForgePlaceholderAPI-Extensions
public class PlayerParserImpl {

    public static RegexReplacer<IPlayerData> createMainReplacer(){
        final RegexReplacer<IPlayerData> MAIN_REPLACER = new RegexReplacer();
        final DecimalFormat PERCENTAGE = new DecimalFormat("#0.##");

        MAIN_REPLACER.addParser(
                "party_size_all",
                "Number of Pokemons In a player's party",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId()).join();
                    return party.countAll();
                }
        );

        MAIN_REPLACER.addParser(
                "party_size_ableonly",
                "Number of Able Pokemons In a player's party",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId()).join();
                    return party.countAblePokemon();
                }
        );

        MAIN_REPLACER.addParser(
                "party_size_eggonly",
                "Number of Egg Pokemons In a player's party",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId()).join();
                    return party.countEggs();
                }
        );

        MAIN_REPLACER.addParser(
                "party_size_pokemononly",
                "Number of Normal Pokemons In a player's party",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId()).join();
                    return party.countPokemon();
                }
        );

        MAIN_REPLACER.addParser(
                "dex_count_caught",
                "Dex Count Caught",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId()).join();
                    return party.playerPokedex.countCaught();
                }
        );

        MAIN_REPLACER.addParser(
                "dex_count_seen",
                "Dex Count Seen",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId()).join();
                    return party.playerPokedex.countSeen();
                }
        );

        MAIN_REPLACER.addParser(
                "dex_percentage",
                "Dex Percentage",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId()).join();
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
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId()).join();
                    return party.stats != null ? party.stats.getLosses() : 0;
                }
        );

        MAIN_REPLACER.addParser(
                "wins",
                "Player's Wins",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId()).join();
                    return party.stats != null ? party.stats.getWins() : 0;
                }
        );

        MAIN_REPLACER.addParser(
                "kdr",
                "Player's Kill Death Ratio",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId()).join();

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
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId()).join();
                    return party.getAverageLevel();
                }
        );


        MAIN_REPLACER.addParser(
                "lowest_level",
                "Lowest Level of the Party",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId()).join();
                    return party.getLowestLevel();
                }
        );

        MAIN_REPLACER.addParser(
                "highest_level",
                "Highest Level of the Party",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId()).join();
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

        MAIN_REPLACER.addParser(
                "catchcombo_amount",
                "Player's CatchCombo's Amount",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId()).join();
                    CaptureCombo combo = party.transientData.captureCombo;
                    if (combo == null){
                        return 0;
                    }
                    return combo.getCurrentCombo();
                }
        );

        MAIN_REPLACER.addParser(
                "catchcombo_species",
                "Player's CatchCombo's Species Name",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId()).join();
                    CaptureCombo combo = party.transientData.captureCombo;
                    if (combo == null || combo.getCurrentSpecies() == null){
                        return "";
                    }
                    return combo.getCurrentSpecies();
                }
        );

        MAIN_REPLACER.addParser(
                "catchcombo_species_localized",
                "Player's CatchCombo's Species LocalizedName",
                player -> {
                    PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId()).join();
                    CaptureCombo combo = party.transientData.captureCombo;
                    if (combo == null || combo.getCurrentSpecies() == null){
                        return "";
                    }
                    return combo.getCurrentSpecies().getLocalizedName();
                }
        );

        return MAIN_REPLACER;
    }

}
