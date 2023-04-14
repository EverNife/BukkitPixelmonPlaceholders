package br.com.finalcraft.bukkitpixelmonplaceholders.common.factory;

import br.com.finalcraft.evernifecore.config.playerdata.IPlayerData;
import br.com.finalcraft.evernifecore.placeholder.replacer.RegexReplacer;
import br.com.finalcraft.evernifecore.util.FCReflectionUtil;
import br.com.finalcraft.evernifecore.version.MCVersion;

public abstract class PixelmonPlaceholderFactory<POKEMON> {

    public static PixelmonPlaceholderFactory<?> create(){
        return (PixelmonPlaceholderFactory<?>) FCReflectionUtil.getConstructor(
                "br.com.finalcraft.bukkitpixelmonplaceholders.compat." + MCVersion.getCurrent().name() +".reforged.factory.PixelmonPlaceholderFactoryImpl"
        ).invoke();
    }

    public abstract RegexReplacer<IPlayerData> getMainReplacer();

    public abstract RegexReplacer<POKEMON> getPokemonReplacer();

}
