package br.com.finalcraft.bukkitpixelmonplaceholders.commands;

import br.com.finalcraft.bukkitpixelmonplaceholders.BukkitPixelmonPlaceholders;
import br.com.finalcraft.bukkitpixelmonplaceholders.PermissionNodes;
import br.com.finalcraft.bukkitpixelmonplaceholders.placeholder.PixelmonPlaceholders;
import br.com.finalcraft.evernifecore.commands.finalcmd.annotations.Arg;
import br.com.finalcraft.evernifecore.commands.finalcmd.annotations.CMDHelpType;
import br.com.finalcraft.evernifecore.commands.finalcmd.annotations.FinalCMD;
import br.com.finalcraft.evernifecore.config.playerdata.PlayerData;
import br.com.finalcraft.evernifecore.ecplugin.ECPluginManager;
import br.com.finalcraft.evernifecore.fancytext.FancyText;
import br.com.finalcraft.evernifecore.integration.placeholders.PAPIIntegration;
import br.com.finalcraft.evernifecore.pageviwer.PageViewer;
import br.com.finalcraft.evernifecore.placeholder.parser.SimpleParser;
import br.com.finalcraft.evernifecore.util.FCColorUtil;
import br.com.finalcraft.evernifecore.util.FCTextUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@FinalCMD(
        aliases = {"bukkitpixelmonplaceholders", "bpp"},
        useDefaultHelp = CMDHelpType.EXCEPT_EMPTY
)
public class CMDCoreCommand {

    @FinalCMD.SubCMD(
            subcmd = {"list","test"},
            permission = PermissionNodes.COMMAND_TEST
    )
    public void test(Player player, PlayerData playerData, @Arg(name = "[page]", context = "[1:*]") Integer page){

        List<SimpleParser> allPossiblePlaceholders = new ArrayList<>();

        allPossiblePlaceholders.addAll(
                PixelmonPlaceholders.MAIN_REPLACER.getProvider()
                        .getParserMap()
                        .values()
                        .stream()
                        .sorted(Comparator.comparing(SimpleParser::getId))
                        .collect(Collectors.toList())
        );

        allPossiblePlaceholders.addAll(
                PixelmonPlaceholders.POKEMON_REPLACER.getProvider()
                        .getParserMap()
                        .values()
                        .stream()
                        .sorted(Comparator.comparing(SimpleParser::getId))
                        .collect(Collectors.toList())
        );

        //Apply format on the placeholder based on its parser
        Function<SimpleParser, String> getPlaceholder = simpleParser -> {
            if (PixelmonPlaceholders.MAIN_REPLACER.getProvider().getParserMap().containsValue(simpleParser)){
                return "§6%pixelmon_§e" + simpleParser.getId() + "§6%";
            }else {
                return "§6%pixelmon_party_slot_1_§e" + simpleParser.getId() + "§6%";
            }
        };

        Map<String, Integer> simplePlaceholderOrder = new HashMap<>();

        for (int i = 0; i < allPossiblePlaceholders.size(); i++) {
            simplePlaceholderOrder.put(allPossiblePlaceholders.get(i).getId(), i);
        }

        PageViewer<SimpleParser, Integer> ALL_PLACEHOLDERS = PageViewer.targeting(SimpleParser.class)
                .withSuplier(() -> allPossiblePlaceholders)
                .extracting(simpleParser -> simplePlaceholderOrder.size() - simplePlaceholderOrder.get(simpleParser.getId())) //keep the same order it was inserted
                .setFormatHeader(
                        FCTextUtil.alignCenter("§6 §l[§ePixelmon Placeholders§6§l]§6 §r","§e§m-")
                )
                .setFormatLine(
                        FancyText.of("§7#  %number%:   §7%the_placeholder%§r - §b%the_result%")
                                .setHoverText("§bDescription" +
                                        "\n" +
                                        "\n§2-§a %description%" +
                                        "\n" +
                                        "\n§e[Click to Copy]")
                                .setSuggestCommandAction("%the_placeholder_striped%")
                )
                .addPlaceholder("%the_placeholder%", simpleParser -> getPlaceholder.apply(simpleParser))
                .addPlaceholder("%the_placeholder_striped%", simpleParser -> FCColorUtil.stripColor(getPlaceholder.apply(simpleParser)))
                .addPlaceholder("%the_result%", simpleParser -> {
                    String placeholder = getPlaceholder.apply(simpleParser);
                    try {
                        return PAPIIntegration.parse(player, FCColorUtil.stripColor(placeholder));
                    }catch (Throwable e) {
                        return "§cError on parsing it";
                    }
                })
                .addPlaceholder("%description%", simpleParser -> simpleParser.getDescription())
                .setLineEnd(-1)
                .setCooldown(2)
                .build();
        
        ALL_PLACEHOLDERS.send(page, player);
    }

    @FinalCMD.SubCMD(
            subcmd = {"reload"},
            permission = PermissionNodes.COMMAND_RELOAD,
            desc = "Reloads the plugin!"
    )
    public void reload(CommandSender sender) {
        ECPluginManager.reloadPlugin(sender, BukkitPixelmonPlaceholders.instance);
    }

}
