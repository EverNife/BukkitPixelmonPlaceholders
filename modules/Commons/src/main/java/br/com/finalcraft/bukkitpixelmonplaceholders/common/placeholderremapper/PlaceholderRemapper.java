package br.com.finalcraft.bukkitpixelmonplaceholders.common.placeholderremapper;

import br.com.finalcraft.bukkitpixelmonplaceholders.common.placeholderremapper.data.RemapperData;
import br.com.finalcraft.evernifecore.config.Config;
import br.com.finalcraft.evernifecore.logger.ECLogger;
import lombok.Data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Data
public class PlaceholderRemapper {

    public static int REMAP_RELOAD_COUNT = 0;

    private static boolean ENABLED = false;
    private static Map<String, RemapperData> GLOBAL_REMAPPER = new HashMap<>();
    private static Map<String,Map<String, RemapperData>> NORMAL_REMAPS = new HashMap<>();
    private static Map<String,Map<String, RemapperData>> POKEMON_SPECIFIC_REMAPS = new HashMap<>();

    public static void initialize(ECLogger ecLogger, Config config){
        GLOBAL_REMAPPER.clear();
        NORMAL_REMAPS.clear();
        POKEMON_SPECIFIC_REMAPS.clear();

        //Increasing the ReloadCount will force all RemappableRegexReplacer to reload their remappings on demand
        REMAP_RELOAD_COUNT = REMAP_RELOAD_COUNT == 999
                ? 0 :
                REMAP_RELOAD_COUNT + 1;

        ENABLED = config.getOrSetDefaultValue(
                "PlaceholderRemapperSystem.Enabled",
                false,
                "Enable/Disable the Placeholder Remapper System"
        );

        List<RemapperData> GLOBAL_REMAPPER = config.getOrSetDefaultValue(
                "GlobaRemapper",
                Arrays.asList(
                        new RemapperData("true", "&aTrue"),
                        new RemapperData("false", "&cFalse")
                ),
                "This is the Global Remapper, it will remap the results of any placeholder" +
                        "\nthat does not have a specific remapper!" +
                        "\nIt's usefull to define global remaps for common results like true/false" +
                        "\n" +
                        "\nThe mapping is Case Sensitive! Make sure to respect upper and lower case!"
        );

        GLOBAL_REMAPPER.forEach(remapperData -> {
            PlaceholderRemapper.GLOBAL_REMAPPER.put(remapperData.getKey(), remapperData);
        });

        if (!config.contains("NormalRemaps")){
            config.setDefaultValue("NormalRemaps.%pixelmon_party_size_all%", Arrays.asList(
                    new RemapperData("1", "One"),
                    new RemapperData("2", "Two"),
                    new RemapperData("3", "Three"),
                    new RemapperData("4", "Four"),
                    new RemapperData("5", "Five"),
                    new RemapperData("6", "Six")
            ));
        }

        config.getKeys("NormalRemaps").forEach(placeholderName -> {
            List<RemapperData> remapperDataList = config.getLoadableList("NormalRemaps." + placeholderName, RemapperData.class);
            Map<String, RemapperData> remapperDataMap = new HashMap<>();
            remapperDataList.forEach(remapperData -> {
                remapperDataMap.put(remapperData.getKey(), remapperData);
            });
            NORMAL_REMAPS.put(placeholderName.substring("%pixelmon_".length() + 1, placeholderName.length() - 1), remapperDataMap);
        });


        if (!config.contains("PokemonSpecificRemaps")){
            config.setDefaultValue("PokemonSpecificRemaps.%pixelmon_party_slot_X_is_present%", Arrays.asList(
                    new RemapperData("true", "&aIs Present"),
                    new RemapperData("false", "&cIs Empty")
            ));
            config.setDefaultValue("PokemonSpecificRemaps.%pixelmon_party_slot_X_species%", Arrays.asList(
                    new RemapperData("Pikachu", "&aSpecies is Pikachu")
            ));
        }

        config.getKeys("PokemonSpecificRemaps").forEach(placeholderName -> {
            List<RemapperData> remapperDataList = config.getLoadableList("PokemonSpecificRemaps." + placeholderName, RemapperData.class);
            Map<String, RemapperData> remapperDataMap = new HashMap<>();
            remapperDataList.forEach(remapperData -> {
                remapperDataMap.put(remapperData.getKey(), remapperData);
            });
            POKEMON_SPECIFIC_REMAPS.put(placeholderName.substring("%pixelmon_party_slot_X_".length(), placeholderName.length() - 1), remapperDataMap);
        });

        if (ecLogger.getEcPluginData().isDebugEnabled()){
            NORMAL_REMAPS.forEach((key, value) -> {
                ecLogger.debug("NormalRemaps: " + key + " -> " + value.entrySet().stream().map(entry -> entry.getKey() + " -> " + entry.getValue()).reduce((a,b) -> a + "\n" + b).orElse("Empty"));
            });

            POKEMON_SPECIFIC_REMAPS.forEach((key, value) -> {
                ecLogger.debug("PokemonSpecificRemaps: " + key + " -> " + value.entrySet().stream().map(entry -> entry.getKey() + " -> " + entry.getValue()).reduce((a,b) -> a + "\n" + b).orElse("Empty"));
            });
        }

        config.saveIfNewDefaults();
    }

    /**
     * Get a remapper for a specific replacer
     * if the replacer does not exist, it will return null
     * if the replacer exists but the result does not exist, it will return the initial result
     *
     * @param replacerName,
     *          like %pixelmon_party_size_all%
     *          like %pixelmon_party_slot_X_is_present%
     *
     * @return a remapper function
     */
    public static Function<String,String> getRemapper(PlaceholderRemapper.RemapType remapType, String replacerName){
        if (ENABLED == false){
            return null;
        }

        Map<String, RemapperData> resultMap = remapType == RemapType.NORMAL
                ? NORMAL_REMAPS.get(replacerName)
                : POKEMON_SPECIFIC_REMAPS.get(replacerName);

//        System.out.println("Getting Remapper for: " + replacerName + " - " + remapType);
//        System.out.println("ResultMap: " + (resultMap == null ? new HashMap<>() : resultMap).entrySet().stream().map(entry -> entry.getKey() + " -> " + entry.getValue()).reduce((a,b) -> a + "\n" + b).orElse("Empty"));

        if (resultMap == null){
            return initialResult -> {
                RemapperData remapperData = GLOBAL_REMAPPER.get(initialResult);
                return remapperData == null
                        ? initialResult
                        : remapperData.remap(initialResult);
            };
        }

        return initialResult -> {
            RemapperData remapperData = resultMap.get(initialResult);

            if (remapperData == null){
                remapperData = GLOBAL_REMAPPER.get(initialResult);
            }

            return remapperData == null
                    ? initialResult
                    : remapperData.remap(initialResult);
        };
    }

    public static enum RemapType {
        NORMAL,
        POKEMON_SPECIFIC,
        ;
    }
}
