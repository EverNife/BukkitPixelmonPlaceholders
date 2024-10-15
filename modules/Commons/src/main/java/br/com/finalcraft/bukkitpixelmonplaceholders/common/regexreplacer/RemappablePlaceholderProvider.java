package br.com.finalcraft.bukkitpixelmonplaceholders.common.regexreplacer;

import br.com.finalcraft.evernifecore.placeholder.base.PlaceholderProvider;

public class RemappablePlaceholderProvider<O extends Object> extends PlaceholderProvider<O> {

    @Override
    public String parse(O object, String parameters) {
        RemappableSimpleParser parser = (RemappableSimpleParser) getParserMap().get(parameters);

        Object result = parser == null ? null : parser.apply(object);
        if (result == null && getDefaultParser() != null){
            result = getDefaultParser().apply(object, parameters);
        }

        if (result == null){
            return null;
        }

        if (parser == null){
            return String.valueOf(result);
        }

        return parser.remap(String.valueOf(result));
    }

}
