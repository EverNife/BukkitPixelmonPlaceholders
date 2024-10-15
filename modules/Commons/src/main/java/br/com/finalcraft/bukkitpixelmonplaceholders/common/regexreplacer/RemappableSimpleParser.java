package br.com.finalcraft.bukkitpixelmonplaceholders.common.regexreplacer;

import br.com.finalcraft.bukkitpixelmonplaceholders.common.placeholderremapper.PlaceholderRemapper;
import br.com.finalcraft.evernifecore.placeholder.parser.SimpleParser;

import javax.annotation.Nonnull;
import java.util.function.Function;

public class RemappableSimpleParser<O extends Object> extends SimpleParser<O> {

    private int remapCount = 0;
    private Function<String, String> remapper;
    private final PlaceholderRemapper.RemapType remapType;

    public RemappableSimpleParser(String id, String description, Function parser, PlaceholderRemapper.RemapType remapType) {
        super(id, description, parser);
        this.remapType = remapType;
    }

    public RemappableSimpleParser(String id, Function parser, PlaceholderRemapper.RemapType remapType) {
        super(id, parser);
        this.remapType = remapType;
    }

    public String remap(@Nonnull String input) {
        if (remapCount != PlaceholderRemapper.REMAP_RELOAD_COUNT){
            remapCount = PlaceholderRemapper.REMAP_RELOAD_COUNT;
            remapper = PlaceholderRemapper.getRemapper(this.getRemapType(), getId());
        }
        return remapper == null ? input : remapper.apply(input);
    }

    public PlaceholderRemapper.RemapType getRemapType() {
        return remapType;
    }

}
