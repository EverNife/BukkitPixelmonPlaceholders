package br.com.finalcraft.bukkitpixelmonplaceholders.compat.v1_21_R1.reforged.parsers;

import br.com.finalcraft.bukkitpixelmonplaceholders.common.placeholderremapper.PlaceholderRemapper;
import br.com.finalcraft.bukkitpixelmonplaceholders.common.regexreplacer.RemappableRegexReplacer;
import br.com.finalcraft.bukkitpixelmonplaceholders.compat.v1_21_R1.reforged.util.FilterUtil;
import br.com.finalcraft.evernifecore.config.playerdata.IPlayerData;
import br.com.finalcraft.evernifecore.nms.util.NMSUtils;
import br.com.finalcraft.evernifecore.placeholder.replacer.RegexReplacer;
import br.com.finalcraft.evernifecore.time.FCTimeFrame;
import com.pixelmonmod.pixelmon.api.pokedex.PokeDexStorageProxy;
import com.pixelmonmod.pixelmon.api.pokedex.PokedexStorage;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.battles.BattleRegistry;
import com.pixelmonmod.pixelmon.battles.controller.BattleController;
import com.pixelmonmod.pixelmon.spawning.PixelmonSpawning;
import com.pixelmonmod.pixelmon.storage.playerData.CaptureCombo;
import net.minecraft.world.entity.player.Player;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

//This class has an influence from https://github.com/EnvyWare/ForgePlaceholderAPI-Extensions
public class PlayerParserImpl {

    public static RegexReplacer<IPlayerData> createMainReplacer(){
        final RemappableRegexReplacer<IPlayerData> MAIN_REPLACER = new RemappableRegexReplacer(PlaceholderRemapper.RemapType.NORMAL);
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
                    PokedexStorage pokedexStorage = PokeDexStorageProxy.getStorage(player.getUniqueId()).join();
                    return pokedexStorage.countCaught();
                }
        );

        MAIN_REPLACER.addParser(
                "dex_count_seen",
                "Dex Count Seen",
                player -> {
                    PokedexStorage pokedexStorage = PokeDexStorageProxy.getStorage(player.getUniqueId()).join();
                    return pokedexStorage.countSeen();
                }
        );

        MAIN_REPLACER.addParser(
                "dex_percentage",
                "Dex Percentage",
                player -> {
                    PokedexStorage pokedexStorage = PokeDexStorageProxy.getStorageNow(player.getUniqueId());

                    int caught = pokedexStorage.countCaught();
                    int pokedexSize = PixelmonSpecies.getAll().size();

                    BigDecimal percent = BigDecimal.valueOf(caught)
                            .divide(
                                    BigDecimal.valueOf(pokedexSize),
                                    new MathContext(5, RoundingMode.HALF_EVEN)
                            )
                            .multiply(BigDecimal.valueOf(100L));

                    return PERCENTAGE.format(percent) + "%";
                }
        );

        MAIN_REPLACER.addParser(
                "dex_total_size",
                "Dex Size",
                player -> {
                    int pokedexSize = PixelmonSpecies.getAll().size();
                    return pokedexSize;
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
                    // Pixelmon only advances nextSpawnTime when the spawner completes a cycle; while it
                    // can't run (e.g. capacity full) the value falls behind now, so clamp to avoid negatives.
                    return TimeUnit.MILLISECONDS.toSeconds(Math.max(0L, PixelmonSpawning.legendarySpawner.nextSpawnTime - System.currentTimeMillis()));
                }
        );

        MAIN_REPLACER.addParser(
                "next_legendary_minutes",
                "The minutes until the next Legendary Spawn",
                player -> {
                    return TimeUnit.MILLISECONDS.toMinutes(Math.max(0L, PixelmonSpawning.legendarySpawner.nextSpawnTime - System.currentTimeMillis()));
                }
        );

        MAIN_REPLACER.addParser(
                "next_legendary_formatted",
                "The formatted time until the next Legendary Spawn",
                player -> {
                    return FCTimeFrame.of(
                            Math.max(0L, PixelmonSpawning.legendarySpawner.nextSpawnTime - System.currentTimeMillis())
                    );
                }
        );

        MAIN_REPLACER.addParser(
                "next_megaboss",
                "The seconds until the next Mega Boss Spawn",
                player -> {
                    // Clamp as in next_legendary: nextSpawnTime freezes while the spawner can't run, so avoid negatives.
                    return TimeUnit.MILLISECONDS.toSeconds(Math.max(0L, PixelmonSpawning.megaBossSpawner.nextSpawnTime - System.currentTimeMillis()));
                }
        );

        MAIN_REPLACER.addParser(
                "next_megaboss_minutes",
                "The minutes until the next Mega Boss Spawn",
                player -> {
                    return TimeUnit.MILLISECONDS.toMinutes(Math.max(0L, PixelmonSpawning.megaBossSpawner.nextSpawnTime - System.currentTimeMillis()));
                }
        );

        MAIN_REPLACER.addParser(
                "next_megaboss_formatted",
                "The formatted time until the next Mega Boss Spawn",
                player -> {
                    return FCTimeFrame.of(
                            Math.max(0L, PixelmonSpawning.megaBossSpawner.nextSpawnTime - System.currentTimeMillis())
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

        MAIN_REPLACER.addParser(
                "is_in_battle",
                "Player Is In Battle",
                player -> {
                    Object minecraftEntity = NMSUtils.get().asMinecraftEntity(player.getPlayer());
                    Player entityPlayer = (Player) minecraftEntity;
                    BattleController battle = BattleRegistry.getBattle(entityPlayer);
                    return battle != null;
                }
        );

        MAIN_REPLACER.addParser(
                "is_in_battle_pvp",
                "Player Is In a PVP Battle",
                player -> {
                    Object minecraftEntity = NMSUtils.get().asMinecraftEntity(player.getPlayer());
                    Player entityPlayer = (Player) minecraftEntity;
                    BattleController battle = BattleRegistry.getBattle(entityPlayer);
                    if (battle == null){
                        return false;
                    }

                    return battle.isPvP();
                }
        );

        MAIN_REPLACER.addParser(
                "is_in_battle_pve",
                "Player Is In a PvE Battle",
                player -> {
                    Object minecraftEntity = NMSUtils.get().asMinecraftEntity(player.getPlayer());
                    Player entityPlayer = (Player) minecraftEntity;
                    BattleController battle = BattleRegistry.getBattle(entityPlayer);
                    if (battle == null){
                        return false;
                    }

                    return !battle.isPvP();
                }
        );

        MAIN_REPLACER.addManipulator("party_contains_spec_{filters}", (player, context) -> {
            String filters = context.getString("{filters}");

            PlayerPartyStorage party = StorageProxy.getParty(player.getUniqueId()).join();

            List<Pokemon> pokemons = new ArrayList<>();

            for (Pokemon pokemon : party.getAll()) {
                if (pokemon == null) continue;
                pokemons.add(pokemon);
            }

            return FilterUtil.totalWithFilters(filters, pokemons.toArray(new Pokemon[0]));
        });

        return MAIN_REPLACER;
    }

}
