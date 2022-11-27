package br.com.finalcraft.bukkitpixelmonplaceholders.placeholder;

import br.com.finalcraft.bukkitpixelmonplaceholders.BukkitPixelmonPlaceholders;
import br.com.finalcraft.bukkitpixelmonplaceholders.placeholder.v1_16_5.ReforgedPixelmonParser_1_16_5;
import br.com.finalcraft.bukkitpixelmonplaceholders.placeholder.v1_16_5.ReforgedPlayerParser_1_16_5;
import br.com.finalcraft.evernifecore.config.playerdata.IPlayerData;
import br.com.finalcraft.evernifecore.config.playerdata.PlayerData;
import br.com.finalcraft.evernifecore.integration.placeholders.PAPIIntegration;
import br.com.finalcraft.evernifecore.placeholder.replacer.RegexReplacer;
import br.com.finalcraft.evernifecore.util.FCInputReader;
import br.com.finalcraft.evernifecore.util.numberwrapper.NumberWrapper;
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

            MAIN_REPLACER.addManipulator("party_slot_{slotNumber}_{pokemonPlaceholder}", POKEMON_REPLACER, (iPlayerData, pokemonRContext) -> {
                Integer slot = FCInputReader.parseInt(pokemonRContext.getString("{slotNumber}"));

                if (slot == null || !NumberWrapper.of(slot).isBounded(1,6)){
                    return "[Invalid Party Slot Number]";
                }

                String pokemonPlaceholder = pokemonRContext.getString("{pokemonPlaceholder}");

                PartyStorage partyStorage = StorageProxy.getParty(iPlayerData.getUniqueId());
                Pokemon pokemon = partyStorage.get(slot - 1);

                if (pokemon != null){
                    return pokemonRContext.quoteAndParse(pokemon, pokemonPlaceholder);
                }else {
                    try {
                        return pokemonRContext.quoteAndParse(null, pokemonPlaceholder);
                    }catch (Exception e){
                        return ""; //Empty String when there is a Pokemon Dependent Placeholder
                    }
                }
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
