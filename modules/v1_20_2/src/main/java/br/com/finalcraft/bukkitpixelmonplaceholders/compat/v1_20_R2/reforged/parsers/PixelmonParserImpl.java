package br.com.finalcraft.bukkitpixelmonplaceholders.compat.v1_20_R2.reforged.parsers;

import br.com.finalcraft.bukkitpixelmonplaceholders.common.PokemonWrapper;
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
import com.pixelmonmod.pixelmon.api.pokemon.stats.extraStats.LakeTrioStats;
import com.pixelmonmod.pixelmon.api.pokemon.stats.extraStats.MewStats;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import com.pixelmonmod.pixelmon.api.util.helpers.SpriteItemHelper;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.pixelmonmod.pixelmon.battles.attacks.specialAttacks.basic.HiddenPower;
import net.minecraft.world.item.Items;
import org.bukkit.ChatColor;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//This class is an adaptaion of https://github.com/NickImpact/GTS-Pixelmon-Extension
//As the date of the creation of this class, there was still no support for Pixelmon 1.16.5
public class PixelmonParserImpl {

    public static RegexReplacer<Pokemon> createPokemonReplacer(){
        final RegexReplacer<Pokemon> POKEMON_REPLACER = new RegexReplacer(){
            @Override
            public String apply(String text, Object object) {
                if (object instanceof PokemonWrapper){
                    object = ((PokemonWrapper) object).getPokemon();
                }
                return super.apply(text, object);
            }
        };

        final DecimalFormat PERCENTAGE = new DecimalFormat("#0.##");

        POKEMON_REPLACER.addParser(
                "is_present",
                "Check if pokemon is present on the slot!",
                pokemon -> pokemon != null
        );

        POKEMON_REPLACER.addParser(
                "originaltrainer_name",
                "Pokemon's original trainer name",
                pokemon -> pokemon.getOriginalTrainer()
        );

        POKEMON_REPLACER.addParser(
                "originaltrainer_uuid",
                "Pokemon's original trainer UUID",
                pokemon -> pokemon.getOriginalTrainerUUID()
        );

        POKEMON_REPLACER.addParser(
                "name",
                "Pokemon's LocalizedName",
                pokemon -> pokemon.getLocalizedName()
        );

        POKEMON_REPLACER.addParser(
                "display_name",
                "Pokemon's Display Name",
                pokemon -> pokemon.getDisplayName()
        );

        POKEMON_REPLACER.addParser(
                "species",
                "Pokemon's Species",
                pokemon -> pokemon.getSpecies().getLocalizedName()
        );

        POKEMON_REPLACER.addParser(
                "level",
                "Pokemon's Level",
                pokemon -> pokemon.getPokemonLevel()
        );

        POKEMON_REPLACER.addParser(
                "pokedex_number",
                "Pokemon's Natinonal Index",
                pokemon -> {
                    return pokemon.getSpecies().getDex();
                }
        );

        POKEMON_REPLACER.addParser(
                "exp",
                "Pokemon's Exp",
                pokemon -> pokemon.getExperience()
        );

        POKEMON_REPLACER.addParser(
                "exp_to_level_up",
                "Pokemon's Exp",
                pokemon -> pokemon.getExperienceToLevelUp()
        );

        POKEMON_REPLACER.addParser(
                "form",
                "Pokemon's Form",
                pokemon -> {
                    return Optional.ofNullable(pokemon.getForm())
                            .map(form -> form.getLocalizedName())
                            .orElse("");
                }
        );

        POKEMON_REPLACER.addParser(
                "form_unlocalized",
                "Pokemon's Form Unlocalized Name",
                pokemon -> {
                    return Optional.ofNullable(pokemon.getForm())
                            .map(form -> form.getTranslationKey())
                            .orElse("");
                }
        );

        POKEMON_REPLACER.addParser(
                "shiny",
                "Pokemon Shiny State",
                pokemon -> pokemon.isShiny()
        );

        POKEMON_REPLACER.addParser(
                "shiny_special",
                "A Preformatted Representation of Shiny State",
                pokemon -> {
                    if(pokemon.isShiny()) {
                        return ChatColor.GRAY + "(" + ChatColor.GOLD + "Shiny" + ChatColor.GRAY + ")";
                    }
                    return "";
                }
        );

        POKEMON_REPLACER.addParser(
                "is_egg",
                "Pokemon is Egg",
                pokemon -> pokemon.isEgg()
        );

        POKEMON_REPLACER.addParser(
                "is_non_egg",
                "Pokemon is Egg",
                pokemon -> !pokemon.isEgg()
        );

        POKEMON_REPLACER.addParser(
                "is_ultrabeast",
                "Pokemon Ultrabeast State",
                pokemon -> pokemon.isUltraBeast()
        );

        POKEMON_REPLACER.addParser(
                "is_ultrabeast_special",
                "A Preformatted Representation of Ultrabeast State",
                pokemon -> {
                    if(pokemon.isUltraBeast()) {
                        return ChatColor.GRAY + "(" + ChatColor.LIGHT_PURPLE + "Ultrabeast" + ChatColor.GRAY + ")";
                    }
                    return "";
                }
        );

        POKEMON_REPLACER.addParser(
                "is_legendary",
                "Pokemon Legendary State",
                pokemon -> pokemon.isLegendary()
        );

        POKEMON_REPLACER.addParser(
                "is_legendary_special",
                "A Preformatted Representation of Legendary State",
                pokemon -> {
                    if(pokemon.isLegendary()) {
                        return ChatColor.GRAY + "(" + ChatColor.GOLD + "Legendary" + ChatColor.GRAY + ")";
                    }
                    return "";
                }
        );

        POKEMON_REPLACER.addParser(
                "ability",
                "Pokemon's Ability",
                pokemon -> {
                    return pokemon.getAbility().getLocalizedName();
                }
        );

        POKEMON_REPLACER.addParser(
                "gender",
                "Pokemon's Gender",
                pokemon -> {
                    Gender gender = pokemon.getGender();
                    ChatColor color = gender == Gender.MALE ? ChatColor.AQUA :
                            gender == Gender.FEMALE ? ChatColor.LIGHT_PURPLE : ChatColor.GRAY;

                    return color + pokemon.getGender().getLocalizedName();
                }
        );

        POKEMON_REPLACER.addParser(
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

        POKEMON_REPLACER.addParser(
                "type_all",
                "Pokemon's (All) Types",
                pokemon -> {
                    return pokemon.getForm().getTypes()
                            .stream()
                            .map(Element::getLocalizedName)
                            .collect(Collectors.joining(", "));
                }
        );

        POKEMON_REPLACER.addParser(
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

        POKEMON_REPLACER.addParser(
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

        POKEMON_REPLACER.addParser(
                new String[]{"growth", "size"},
                "Pokemon's Growth",
                pokemon -> pokemon.getGrowth().getLocalizedName()
        );

        POKEMON_REPLACER.addParser(
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

            POKEMON_REPLACER.addParser(
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

            POKEMON_REPLACER.addParser(
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
                POKEMON_REPLACER.addParser(
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

        POKEMON_REPLACER.addParser(
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

        POKEMON_REPLACER.addParser(
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

        POKEMON_REPLACER.addParser(
                "dynamax_level",
                "A Pokemon's Dynamax Level",
                pokemon -> pokemon.getDynamaxLevel()
        );

        POKEMON_REPLACER.addParser(
                "held_item",
                "A Pokemon's Held Item",
                pokemon -> {
                    if(pokemon.getHeldItem().getItem() == Items.AIR) {
                        return "";
                    }

                    // Getting the name of the item that the pokemon is holding.
                    return pokemon.getHeldItem().getItem().getName(pokemon.getHeldItem()).getString();
                }
        );

        POKEMON_REPLACER.addParser(
                "texture",
                "A Pokemon's Custom Texture path",
                pokemon -> {
                    return pokemon.getPalette() != null ? pokemon.getPalette().getTexture().toString() : "";
                }
        );

        POKEMON_REPLACER.addParser(
                "palette",
                "A Pokemon's Palette Name",
                pokemon -> {
                    return pokemon.getPalette() != null ? pokemon.getPalette().getLocalizedName() : "";
                }
        );

        POKEMON_REPLACER.addParser(
                "palette_unlocalized",
                "A Pokemon's Palette Name Unlocalized",
                pokemon -> {
                    return pokemon.getPalette() != null ? pokemon.getPalette().getName() : "";
                }
        );

        POKEMON_REPLACER.addParser(
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

        POKEMON_REPLACER.addParser(
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

        POKEMON_REPLACER.addParser(
                "hidden_power",
                "A Pokemon's Hidden Power",
                pokemon -> HiddenPower.getHiddenPowerType(pokemon.getIVs())
        );

        POKEMON_REPLACER.addParser(
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

        POKEMON_REPLACER.addParser(
                "egg_groups",
                "Name of all groups of this egg",
                pokemon -> {
                    if(pokemon.isEgg()) {
                        return pokemon.getForm().getEggGroups().stream()
                                .map(eggGroup -> eggGroup.getLocalizedName())
                                .collect(Collectors.toList());
                    }

                    return "";
                }
        );

        for(int i = 0; i < 4; i++) {
            final int index = i;
            POKEMON_REPLACER.addParser(
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

        POKEMON_REPLACER.addParser(
                "can_gmax",
                "Pokemon G-Max Potential",
                pokemon -> pokemon.hasGigantamaxFactor()
        );

        POKEMON_REPLACER.addParser(
                "sprite",
                "Pokemon Sprite",
                pokemon -> SpriteItemHelper.getPhoto(pokemon).getTag().toString()
        );

        POKEMON_REPLACER.addParser(
                "friendship",
                "Pokemon's Friendship",
                pokemon -> pokemon.getFriendship()
        );

        POKEMON_REPLACER.addParser(
                "friendship_percentage",
                "Pokemon's Friendship Percentage",
                pokemon -> PERCENTAGE.format(pokemon.getFriendship() / 255D * 100D) + "%"
        );

        POKEMON_REPLACER.addManipulator("specflag_contains_{flagName}", (pokemon, pokemonRContext) -> {
            String flagName = pokemonRContext.getString("{flagName}");
            return pokemon.hasFlag(flagName);
        });

        return POKEMON_REPLACER;
    }

}
