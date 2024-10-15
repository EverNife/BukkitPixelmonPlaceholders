package br.com.finalcraft.bukkitpixelmonplaceholders.common.regexreplacer;

import br.com.finalcraft.bukkitpixelmonplaceholders.common.placeholderremapper.PlaceholderRemapper;
import br.com.finalcraft.evernifecore.placeholder.replacer.Closures;
import br.com.finalcraft.evernifecore.placeholder.replacer.RegexReplacer;

import java.util.function.Function;

public class RemappableRegexReplacer<O extends Object> extends RegexReplacer<O> {

    private final PlaceholderRemapper.RemapType remapType;
    private final RemappablePlaceholderProvider<O> providerOverride;

    public RemappableRegexReplacer(PlaceholderRemapper.RemapType remapType) {
        super(Closures.PERCENT.getPattern());
        this.remapType = remapType;
        this.providerOverride = new RemappablePlaceholderProvider<>();
    }

    @Override
    public RemappablePlaceholderProvider<O> getProvider() {
        return providerOverride;
    }

    @Override
    public RegexReplacer<O> addParser(String name, String description, Function<O, Object> parser) {
        getProvider().getParserMap().put(name, new RemappableSimpleParser(name, description, parser, getRemapType()));
        return this;
    }

    @Override
    public RegexReplacer<O> addParser(String name, Function<O, Object> parser) {
        getProvider().getParserMap().put(name, new RemappableSimpleParser(name, parser, getRemapType()));
        return this;
    }

    public PlaceholderRemapper.RemapType getRemapType() {
        return remapType;
    }
}
