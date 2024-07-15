package br.com.finalcraft.bukkitpixelmonplaceholders.compat.v1_20_R2.reforged.factory;

import br.com.finalcraft.bukkitpixelmonplaceholders.common.factory.PixelmonPlaceholderFactory;
import br.com.finalcraft.bukkitpixelmonplaceholders.common.settings.BPPSettings;
import br.com.finalcraft.bukkitpixelmonplaceholders.compat.v1_20_R2.reforged.parsers.PixelmonParserImpl;
import br.com.finalcraft.bukkitpixelmonplaceholders.compat.v1_20_R2.reforged.parsers.PlayerParserImpl;
import br.com.finalcraft.evernifecore.config.playerdata.IPlayerData;
import br.com.finalcraft.evernifecore.placeholder.replacer.RegexReplacer;
import br.com.finalcraft.evernifecore.util.FCInputReader;
import br.com.finalcraft.evernifecore.util.numberwrapper.NumberWrapper;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PixelmonPlaceholderFactoryImpl extends PixelmonPlaceholderFactory<Pokemon> {

    public RegexReplacer<IPlayerData> MAIN_REPLACER;
    public RegexReplacer<Pokemon> POKEMON_REPLACER;

    public PixelmonPlaceholderFactoryImpl() {
        this.MAIN_REPLACER = PlayerParserImpl.createMainReplacer();
        this.POKEMON_REPLACER = PixelmonParserImpl.createPokemonReplacer();

        MAIN_REPLACER.addManipulator("party_slot_{slotNumber}_{pokemonPlaceholder}", POKEMON_REPLACER, (iPlayerData, pokemonRContext) -> {
            Integer slot = FCInputReader.parseInt(pokemonRContext.getString("{slotNumber}"));

            if (slot == null || !NumberWrapper.of(slot).isBounded(1,6)){
                String slotNumber = pokemonRContext.getString("{slotNumber}");

                List<String> charsCodes = new ArrayList<>();
                if (slotNumber != null){
                    for (char c : slotNumber.toCharArray()) {
                        charsCodes.add(String.format("'%s' -> '%s'", c, (int) c));
                    }
                }

                return "[Invalid Party Slot Number] Received '" + slotNumber + "' as PartySlot with codes: " + charsCodes.stream().collect(Collectors.joining(" | "));
            }

            String pokemonPlaceholder = pokemonRContext.getString("{pokemonPlaceholder}");

            PartyStorage partyStorage = StorageProxy.getParty(iPlayerData.getUniqueId()).join();
            Pokemon pokemon = partyStorage.get(slot - 1);

            if (pokemon != null){
                return pokemonRContext.quoteAndParse(pokemon, pokemonPlaceholder);
            }else {
                try {
                    return pokemonRContext.quoteAndParse(null, pokemonPlaceholder);
                }catch (Exception e){
                    if (pokemon == null){
                        return BPPSettings.DEFAULT_PLACEHOLDER_WHEN_NO_POKEMON;
                    }
                    return ""; //Empty String when there is a Pokemon Dependent Placeholder
                }
            }
        });
    }

    @Override
    public RegexReplacer<IPlayerData> getMainReplacer() {
        return MAIN_REPLACER;
    }

    @Override
    public RegexReplacer<Pokemon> getPokemonReplacer() {
        return POKEMON_REPLACER;
    }

}
