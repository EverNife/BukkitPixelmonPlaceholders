package br.com.finalcraft.bukkitpixelmonplaceholders.placeholder;

import br.com.finalcraft.bukkitpixelmonplaceholders.BukkitPixelmonPlaceholders;
import br.com.finalcraft.bukkitpixelmonplaceholders.placeholder.v1_16_5.ReforgedPixelmonParser_1_16_5;
import br.com.finalcraft.bukkitpixelmonplaceholders.placeholder.v1_16_5.ReforgedPlayerParser_1_16_5;
import br.com.finalcraft.evernifecore.config.playerdata.IPlayerData;
import br.com.finalcraft.evernifecore.config.playerdata.PlayerData;
import br.com.finalcraft.evernifecore.integration.placeholders.PAPIIntegration;
import br.com.finalcraft.evernifecore.placeholder.replacer.RegexReplacer;
import br.com.finalcraft.evernifecore.util.FCInputReader;
import br.com.finalcraft.evernifecore.version.MCVersion;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;


public class PixelmonPlaceholders {

    public static RegexReplacer<IPlayerData> MAIN_REPLACER;
    public static RegexReplacer<Pokemon> POKEMON_REPLACER;

    static {
        if (!MCVersion.isBellow1_13()){
            MAIN_REPLACER = ReforgedPlayerParser_1_16_5.createMainReplacer();
            POKEMON_REPLACER = ReforgedPixelmonParser_1_16_5.createPokemonReplacer();

            MAIN_REPLACER.setDefaultParser((iPlayerData, placeholder) -> {
                if (placeholder.startsWith("party_slot_")){
                    try {
                        String subPlaceholder = placeholder.substring(11); // "party_slot_".lengh() == 11
                        String[] split = subPlaceholder.split("_", 2); // split between "number" and "the_rest"
                        Integer slot = FCInputReader.parseInt(split[0]);
                        String pokemonPlaceholder = '%' + split[1] + '%';

                        PartyStorage partyStorage = StorageProxy.getParty(iPlayerData.getUniqueId());
                        Pokemon pokemon = partyStorage.get(slot - 1);
                        if (pokemon != null){
                            return POKEMON_REPLACER.apply(pokemonPlaceholder, pokemon);
                        }else {
                            try {
                                return POKEMON_REPLACER.apply(pokemonPlaceholder, null);
                            }catch (Exception e){
                                return ""; //Empty String when there is a Pokemon Dependent Placeholder
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        return "[Error Reading Placeholder]";
                    }
                }
                return null;
            });
        }
    }

    public static void registerToPAPI(){
        RegexReplacer<PlayerData> PAPI_REPLACER = PAPIIntegration.createPlaceholderIntegration(BukkitPixelmonPlaceholders.instance, "pixelmon", PlayerData.class);
        PAPI_REPLACER.setDefaultParser((playerData, placeholder) -> {
            // In here, '%pixelmon_custom_placeholder%' turns into '%custom_placeholder%'
            return MAIN_REPLACER.apply("%" + placeholder + "%", playerData);
        });
    }

}
