package br.com.finalcraft.bukkitpixelmonplaceholders.common;

import lombok.Data;

@Data
public class PokemonWrapper {

    private final Object pokemon;

    public static PokemonWrapper of(Object pokemon){
        return new PokemonWrapper(pokemon);
    }

}
