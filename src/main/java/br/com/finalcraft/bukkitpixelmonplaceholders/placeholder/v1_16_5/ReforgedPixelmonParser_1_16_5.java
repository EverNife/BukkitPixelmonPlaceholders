package br.com.finalcraft.bukkitpixelmonplaceholders.placeholder.v1_16_5;

import br.com.finalcraft.evernifecore.placeholder.replacer.RegexReplacer;
import com.google.common.collect.Lists;
import com.pixelmonmod.api.Flags;
import com.pixelmonmod.pixelmon.api.config.PixelmonConfigProxy;
import com.pixelmonmod.pixelmon.api.pokemon.Element;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.species.Species;
import com.pixelmonmod.pixelmon.api.pokemon.species.gender.Gender;
import com.pixelmonmod.pixelmon.api.pokemon.stats.BattleStatsType;
import com.pixelmonmod.pixelmon.api.pokemon.stats.EVStore;
import com.pixelmonmod.pixelmon.api.pokemon.stats.IVStore;
import com.pixelmonmod.pixelmon.api.pokemon.stats.Moveset;
import com.pixelmonmod.pixelmon.api.pokemon.stats.extraStats.LakeTrioStats;
import com.pixelmonmod.pixelmon.api.pokemon.stats.extraStats.MewStats;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.pixelmonmod.pixelmon.battles.attacks.specialAttacks.basic.HiddenPower;
import org.bukkit.ChatColor;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

//This class is an adaptaion of https://github.com/NickImpact/GTS-Pixelmon-Extension
//As the date of the creation of this class, there was still no support for Pixelmon 1.16.5
public class ReforgedPixelmonParser_1_16_5 {

    public static RegexReplacer<Pokemon> createPokemonReplacer(){
        final RegexReplacer<Pokemon> POKEMON_REPLACER = new RegexReplacer<>();
        final DecimalFormat PERCENTAGE = new DecimalFormat("#0.##");

        POKEMON_REPLACER.addMappedParser(
                "originaltrainer_name",
                "Pokemon's original trainer name",
                pokemon -> pokemon.getOriginalTrainer()
        );

        POKEMON_REPLACER.addMappedParser(
                "originaltrainer_uuid",
                "Pokemon's original trainer UUID",
                pokemon -> pokemon.getOriginalTrainerUUID()
        );

        POKEMON_REPLACER.addMappedParser(
                "name",
                "Pokemon's LocalizedName",
                pokemon -> pokemon.getLocalizedName()
        );

        POKEMON_REPLACER.addMappedParser(
                "display_name",
                "Pokemon's Display Name",
                pokemon -> pokemon.getDisplayName()
        );

        POKEMON_REPLACER.addMappedParser(
                "species",
                "Pokemon's Species",
                pokemon -> pokemon.getSpecies().getLocalizedName()
        );

        POKEMON_REPLACER.addMappedParser(
                "level",
                "Pokemon's Level",
                pokemon -> pokemon.getPokemonLevel()
        );

        POKEMON_REPLACER.addMappedParser(
                "exp",
                "Pokemon's Exp",
                pokemon -> pokemon.getExperience()
        );

        POKEMON_REPLACER.addMappedParser(
                "exp_to_level_up",
                "Pokemon's Exp",
                pokemon -> pokemon.getExperienceToLevelUp()
        );

        POKEMON_REPLACER.addMappedParser(
                "form",
                "Pokemon's Form",
                pokemon -> {
                    return Optional.ofNullable(pokemon.getForm())
                            .map(form -> form.getLocalizedName())
                            .orElse("N/A");
                }
        );

        POKEMON_REPLACER.addMappedParser(
                "shiny",
                "Pokemon Shiny State",
                pokemon -> pokemon.isShiny()
        );

        POKEMON_REPLACER.addMappedParser(
                "shiny_special",
                "A Preformatted Representation of Shiny State",
                pokemon -> {
                    if(pokemon.isShiny()) {
                        return ChatColor.GRAY + "(" + ChatColor.GOLD + "Shiny" + ChatColor.GRAY + ")";
                    }
                    return "";
                }
        );

        POKEMON_REPLACER.addMappedParser(
                "ability",
                "Pokemon's Ability",
                pokemon -> {
                    return pokemon.getAbility().getLocalizedName();
                }
        );

        POKEMON_REPLACER.addMappedParser(
                "gender",
                "Pokemon's Gender",
                pokemon -> {
                    Gender gender = pokemon.getGender();
                    ChatColor color = gender == Gender.MALE ? ChatColor.AQUA :
                            gender == Gender.FEMALE ? ChatColor.LIGHT_PURPLE : ChatColor.GRAY;

                    return color + pokemon.getGender().getLocalizedName();
                }
        );

        POKEMON_REPLACER.addMappedParser(
                "nature",
                "Pokemon's Nature",
                pokemon -> {
                    String result = pokemon.getBaseNature().getLocalizedName();
                    if(pokemon.getMintNature() != null) {
                        result = result + ChatColor.GRAY + " (" + ChatColor.GOLD + pokemon.getMintNature().getLocalizedName() + ChatColor.GRAY + ")";
                    }

                    return result;
                }
        );

        POKEMON_REPLACER.addMappedParser(
                "type_all",
                "Pokemon's (All) Types",
                pokemon -> {
                    return pokemon.getForm().getTypes()
                            .stream()
                            .map(Element::getLocalizedName)
                            .collect(Collectors.joining(", "));
                }
        );

        POKEMON_REPLACER.addMappedParser(
                "type_1",
                "Pokemon's First Type",
                pokemon -> {
                    List<Element> elements = pokemon.getForm().getTypes();
                    if (elements.size() > 0){
                        return elements.get(0).getLocalizedName();
                    }
                    return "";
                }
        );

        POKEMON_REPLACER.addMappedParser(
                "type_2",
                "Pokemon's Second Type",
                pokemon -> {
                    List<Element> elements = pokemon.getForm().getTypes();
                    if (elements.size() > 1){
                        return elements.get(1).getLocalizedName();
                    }
                    return "";
                }
        );

        Function<Pokemon, Object> getGrowth = pokemon -> pokemon.getGrowth().getLocalizedName();
        POKEMON_REPLACER.addMappedParser(
                "size",
                "Pokemon's Growth",
                getGrowth
        );

        POKEMON_REPLACER.addMappedParser(
                "growth",
                "Pokemon's Growth",
                getGrowth
        );

        POKEMON_REPLACER.addMappedParser(
                "unbreedable",
                "Whether a Pokemon is Breedable or not",
                pokemon -> {
                    if(pokemon.hasFlag(Flags.UNBREEDABLE)){
                        return "§cUnbreedable";
                    } else {
                        return "§aBreedable";
                    }
                }
        );

        for(String stat : Arrays.asList("ev", "iv")) {

            POKEMON_REPLACER.addMappedParser(
                    stat + "_total",
                    "A Pokemon's " + stat.toUpperCase() + " Stat Total",
                    pokemon -> {
                        if(stat.equals("ev")) {
                            return pokemon.getStats().getEVs().getTotal();
                        } else {
                            return pokemon.getStats().getIVs().getTotal();
                        }
                    }
            );

            POKEMON_REPLACER.addMappedParser(
                    stat + "_total_max",
                    "A Pokemon's " + stat.toUpperCase() + " Stat Total",
                    pokemon -> {
                        if(stat.equals("ev")) {
                            return EVStore.MAX_TOTAL_EVS;
                        } else {
                            return IVStore.MAX_IVS * BattleStatsType.getEVIVStatValues().length;
                        }
                    }
            );

            for (BattleStatsType type : BattleStatsType.getEVIVStatValues()) {
                POKEMON_REPLACER.addMappedParser(
                        stat + "_" + type.name().toLowerCase(),
                        "A Pokemon's " + type.getLocalizedName() + " " + stat.toUpperCase() + " Stat",
                        pokemon -> {
                            if(stat.equals("ev")) {
                                return pokemon.getStats().getEVs().getStat(type);
                            } else {
                                boolean hyper = pokemon.getStats().getIVs().isHyperTrained(type);
                                String result = String.valueOf(pokemon.getStats().getIVs().getStat(type));
                                if(hyper) {
                                    return ChatColor.AQUA + result;
                                }
                                return result;
                            }
                        }
                );
            }
        }

        POKEMON_REPLACER.addMappedParser(
                "ev_percentage",
                "A Pokemon's Percentage of Total EVs Gained",
                pokemon -> {
                    EVStore evs = pokemon.getEVs();
                    double sum = 0;
                    for(int stat : evs.getArray()) {
                        sum += stat;
                    }

                    return PERCENTAGE.format(sum / EVStore.MAX_TOTAL_EVS * 100D) + "%";
                }
        );

        POKEMON_REPLACER.addMappedParser(
                "iv_percentage",
                "A Pokemon's Percentage of Total IVs Gained",
                pokemon -> {
                    IVStore ivs = pokemon.getIVs();
                    int MAX_IVS = 0;
                    double sum = 0;
                    for(int stat : ivs.getArray()) {
                        MAX_IVS += IVStore.MAX_IVS;
                        sum += stat;
                    }

                    return PERCENTAGE.format(sum / MAX_IVS * 100D) + "%";
                }
        );

        POKEMON_REPLACER.addMappedParser(
                "dynamax_level",
                "A Pokemon's Dynamax Level",
                pokemon -> pokemon.getDynamaxLevel()
        );

        POKEMON_REPLACER.addMappedParser(
                "held_item",
                "A Pokemon's Held Item",
                pokemon -> {
                    if(pokemon.getHeldItem() == net.minecraft.item.ItemStack.EMPTY) {
                        return "";
                    }

                    // Getting the name of the item that the pokemon is holding.
                    return pokemon.getHeldItem().getItem().getName(pokemon.getHeldItem()).getString();
                }
        );

        POKEMON_REPLACER.addMappedParser(
                "texture",
                "A Pokemon's Custom Texture",
                pokemon -> {
                    String texture = pokemon.getPalette() != null ? pokemon.getPalette().getTexture().toString() : "";
                    if (!texture.isEmpty()) {
                        String firstChar = String.valueOf(texture.charAt(0)).toUpperCase();
                        String subTexture = texture.substring(1);
                        return firstChar + subTexture;
                    }
                    return "N/A";
                }
        );

        POKEMON_REPLACER.addMappedParser(
                "clones",
                "Number of Mew Clones",
                pokemon -> {
                    if(pokemon.getSpecies() == PixelmonSpecies.MEW.getValueUnsafe()) {
                        MewStats stats = (MewStats) pokemon.getExtraStats();
                        return stats.numCloned;
                    }

                    return "";
                }
        );

        POKEMON_REPLACER.addMappedParser(
                "enchantments",
                "Number of Lake Trio Enchantments",
                pokemon -> {
                    List<Species> options = Lists.newArrayList(PixelmonSpecies.AZELF.getValueUnsafe(), PixelmonSpecies.MESPRIT.getValueUnsafe(), PixelmonSpecies.UXIE.getValueUnsafe());

                    if(options.contains(pokemon.getSpecies())) {
                        LakeTrioStats stats = (LakeTrioStats) pokemon.getExtraStats();

                        return stats.numEnchanted;
                    }

                    return "";
                }
        );

        POKEMON_REPLACER.addMappedParser(
                "hidden_power",
                "A Pokemon's Hidden Power",
                pokemon -> HiddenPower.getHiddenPowerType(null, pokemon, pokemon.getIVs(), "PlaceholderAPI")
        );

        POKEMON_REPLACER.addMappedParser(
                "egg_steps",
                "Amount of steps remaining for an egg",
                pokemon -> {
                    if(pokemon.isEgg()) {
                        int total = (pokemon.getEggCycles() + 1) * PixelmonConfigProxy.getBreeding().getStepsPerEggCycle();
                        int walked = pokemon.getEggSteps() + ((pokemon.getEggCycles() - pokemon.getEggCycles()) * PixelmonConfigProxy.getBreeding().getStepsPerEggCycle());

                        return walked + "/" + total;
                    }

                    return "";
                }
        );

        for(int i = 0; i < 4; i++) {
            final int index = i;
            POKEMON_REPLACER.addMappedParser(
                    "move" + (i + 1),
                    "Pokemon's Move at index: " + (i + 1),
                    pokemon -> {
                        Attack attack = pokemon.getMoveset().get(index);
                        if(attack != null) {
                            return attack.getActualMove().getLocalizedName();
                        } else {
                            return "";
                        }
                    }
            );
        }

        POKEMON_REPLACER.addMappedParser(
                "can_gmax",
                "Pokemon G-Max Potential",
                pokemon -> pokemon.hasGigantamaxFactor()
        );

        return POKEMON_REPLACER;
    }

}
