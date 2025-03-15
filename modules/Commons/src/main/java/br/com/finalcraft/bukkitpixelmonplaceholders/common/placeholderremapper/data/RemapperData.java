package br.com.finalcraft.bukkitpixelmonplaceholders.common.placeholderremapper.data;

import br.com.finalcraft.evernifecore.config.fcconfiguration.annotation.FConfig;
import br.com.finalcraft.evernifecore.config.fcconfiguration.annotation.IFConfigComplex;
import br.com.finalcraft.evernifecore.config.yaml.section.ConfigSection;
import br.com.finalcraft.evernifecore.util.FCColorUtil;

@FConfig
public class RemapperData implements IFConfigComplex {

    @FConfig.Id
    private String key;
    private String value;

    @FConfig.Exclude
    private transient String valueParsed;

    public RemapperData() {

    }

    public RemapperData(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    @Override
    public void onConfigLoadPost(ConfigSection section) {
        valueParsed = FCColorUtil.colorfy(value);
    }

    public String remap(String input) {
        //TODO create custom remap functions like per-interval or whatever
        return valueParsed;
    }

    @Override
    public String toString() {
        return "RemapperData{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
