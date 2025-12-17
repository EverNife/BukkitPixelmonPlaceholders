package br.com.finalcraft.bukkitpixelmonplaceholders.compat.v1_16_R3.reforged.util;

import br.com.finalcraft.bukkitpixelmonplaceholders.common.logging.BPPDebugModule;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class FilterUtil {

    public static int totalWithFilters(String filters, Pokemon[] pokemons){
        Boolean egg = null;
        Boolean shiny = null;
        Boolean mega = null;
        Boolean ultrabeast = null;
        Boolean legendary = null;

        String[] parts = StringUtils.substring(filters,1, -1).split(Pattern.quote("|"));
        for (String part : parts) {
            String[] subparts = part.split("=");
            String key = subparts[0];
            String value = subparts[1];

            try {
                switch (key.toLowerCase()) {
                    case "egg":
                        egg = Boolean.parseBoolean(value);
                        break;
                    case "shiny":
                        shiny = Boolean.parseBoolean(value);
                        break;
                    case "mega":
                        mega = Boolean.parseBoolean(value);
                        break;
                    case "ultrabeast":
                        ultrabeast = Boolean.parseBoolean(value);
                        break;
                    case "legendary":
                        legendary = Boolean.parseBoolean(value);
                        break;
                }
            }catch (Exception e){
                throw new RuntimeException(String.format("Error while parsing filter-tuple ['%s':'%s'] from placeholder filter: %s", key, value, filters), e);
            }
        }

        Boolean finalEgg = egg;
        Boolean finalShiny = shiny;
        Boolean finalMega = mega;
        Boolean finalUltrabeast = ultrabeast;
        Boolean finalLegendary = legendary;

        int count = 0;

        for (Pokemon pokemon : pokemons) {
            if (pokemon == null) continue;
            BPPDebugModule.POKEMON_FILTER.debugModule(() -> String.format("Checking Pokemon '%s' against filters: Egg=%s | Shiny=%s | Mega=%s | Ultrabeast=%s | Legendary=%s",
                    pokemon.getSpecies().getName(),
                    finalEgg + " == isEgg " + pokemon.isEgg(),
                    finalShiny + " == isShiny " + pokemon.isShiny(),
                    finalMega + " == isMega " + pokemon.isMega(),
                    finalUltrabeast + " == isUltrabeast " + pokemon.getSpecies().isUltraBeast(),
                    finalLegendary + " == isLegendary " + pokemon.getSpecies().isLegendary(
                    )));
            if (finalEgg != null && pokemon.isEgg() != finalEgg) continue;
            if (finalShiny != null && pokemon.isShiny() != finalShiny) continue;
            if (finalMega != null && pokemon.isMega() != finalMega) continue;
            if (finalUltrabeast != null && pokemon.getSpecies().isUltraBeast() != finalUltrabeast) continue;
            if (finalLegendary != null && pokemon.getSpecies().isLegendary() != finalLegendary) continue;
            count++;
        }

        return count;
    }

}
