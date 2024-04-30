package br.com.finalcraft.bukkitpixelmonplaceholders.common.settings;

import br.com.finalcraft.evernifecore.config.Config;

public class BPPSettings {

    public static String BPP_PLACEHOLDER_PREFIX = "pixelmon";
    public static String DEFAULT_PLACEHOLDER_WHEN_NO_POKEMON = "";

    public static void initialize(Config config){
        BPP_PLACEHOLDER_PREFIX = config.getOrSetDefaultValue(
                "Settings.PAPIPlaceholderPrefix",
                "pixelmon",
                "By default the prefix for all placeholders is 'pixelmon'!" +
                        "\nBut there are other plugins that use the same prefix, like neovitalism's one!" +
                        "\n" +
                        "\nHere you can change the prefix of placeholders to something else," +
                        "\nlike 'bpppixelmon' or 'pixelmon2'!" +
                        "\n" +
                        "\nThis configuration can only be changed by restarting the server!"
        );

        DEFAULT_PLACEHOLDER_WHEN_NO_POKEMON = config.getOrSetDefaultValue(
                "Settings.DEFAULT_PLACEHOLDER_WHEN_NO_POKEMON",
                "",
                "When you try to get data from a PokeSlot and that PokeSlot is empty," +
                        "\nwhat shold be returned as result?" +
                        "\n" +
                        "\nThis option is here because some players use DeluxeMenus and it" +
                        "\ndoes not behave well with empty placeholders!"
        );

        config.saveIfNewDefaults();
    }

    public static String getDefaultPlaceholderWhenNoPokemon() {
        return DEFAULT_PLACEHOLDER_WHEN_NO_POKEMON;
    }
}
