package br.com.finalcraft.bukkitpixelmonplaceholders.common.factory;

import br.com.finalcraft.evernifecore.config.playerdata.IPlayerData;
import br.com.finalcraft.evernifecore.placeholder.replacer.RegexReplacer;
import br.com.finalcraft.evernifecore.util.FCReflectionUtil;
import br.com.finalcraft.evernifecore.version.MCDetailedVersion;
import br.com.finalcraft.evernifecore.version.MCVersion;

public abstract class PixelmonPlaceholderFactory<POKEMON> {

    public static PixelmonPlaceholderFactory<?> create(){

        String versionName = MCVersion.getCurrent().name();
        if (MCVersion.isEqual(MCVersion.v1_20)){
            versionName = MCDetailedVersion.v1_20_R2.name();
        }

        PixelmonPlaceholderFactory<?> factory = (PixelmonPlaceholderFactory<?>) FCReflectionUtil.getConstructor(
                "br.com.finalcraft.bukkitpixelmonplaceholders.compat." + versionName +".reforged.factory.PixelmonPlaceholderFactoryImpl"
        ).invoke();

        return factory;
    }

    public abstract RegexReplacer<IPlayerData> getMainReplacer();

    public abstract RegexReplacer<POKEMON> getPokemonReplacer();

}
