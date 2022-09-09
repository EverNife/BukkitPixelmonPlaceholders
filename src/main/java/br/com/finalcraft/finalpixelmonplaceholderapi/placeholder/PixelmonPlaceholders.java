package br.com.finalcraft.finalpixelmonplaceholderapi.placeholder;

import br.com.finalcraft.evernifecore.config.playerdata.IPlayerData;
import br.com.finalcraft.evernifecore.config.playerdata.PlayerData;
import br.com.finalcraft.evernifecore.integration.placeholders.PAPIIntegration;
import br.com.finalcraft.evernifecore.placeholder.replacer.RegexReplacer;
import br.com.finalcraft.evernifecore.util.FCInputReader;
import br.com.finalcraft.evernifecore.version.MCVersion;
import br.com.finalcraft.finalpixelmonplaceholderapi.FinalPixelmonPlaceholderAPI;
import br.com.finalcraft.finalpixelmonplaceholderapi.placeholder.v1_16_5.ReforgedPixelmonParser_1_16_5;
import br.com.finalcraft.finalpixelmonplaceholderapi.placeholder.v1_16_5.ReforgedPlayerParser_1_16_5;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;

import java.text.DecimalFormat;


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
                        String subPlaceholder = placeholder.substring(11);
                        String[] split = subPlaceholder.split("_");
                        Integer slot = FCInputReader.parseInt(split[0]);
                        String pokemonPlaceholder = '%' + split[1] + '%';

                        PartyStorage partyStorage = StorageProxy.getParty(iPlayerData.getUniqueId());
                        Pokemon pokemon = partyStorage.get(slot);
                        if (pokemon != null){
                            return POKEMON_REPLACER.apply(pokemonPlaceholder, pokemon);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        return "[Error reading placeholder]";
                    }
                }
                return null;
            });
        }
    }

    public static void registerToPAPI(){
        RegexReplacer<PlayerData> PAPI_REPLACER = PAPIIntegration.createPlaceholderIntegration(FinalPixelmonPlaceholderAPI.instance, "pixelmon", PlayerData.class);
        PAPI_REPLACER.setDefaultParser((playerData, placeholder) -> {
            // In here, '%pixelmon_custom_placeholder%' turns into '%custom_placeholder%'
            return MAIN_REPLACER.apply("%" + placeholder + "%", playerData);
        });
    }

}
