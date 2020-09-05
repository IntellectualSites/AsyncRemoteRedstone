package me.orlaando.ARR.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.orlaando.ARR.Main;
import me.orlaando.ARR.configuration.TranslatableMessage;
import org.bukkit.command.CommandSender;

@CommandAlias("arr")
@CommandPermission("arr.plugin")
public class CommandARR extends BaseCommand {

    @Subcommand("reload")
    @CommandPermission("arr.reload")
    public void reload(CommandSender sender) {
        try {
            Main.getARR().reloadConfig();
            Main.getARR().reloadMessages();
            sender.sendMessage(TranslatableMessage.of("plugin.reload.success"));
        } catch (final Exception e) {
            sender.sendMessage(TranslatableMessage.of("plugin.reload.failure"));
            e.printStackTrace();
        }
    }

    @CatchUnknown
    @HelpCommand
    @Default
    public void onUnknown(CommandSender sender) {
        sender.sendMessage(
                String.format(
                        TranslatableMessage.of("command.syntax"),
                        "/arr reload")
        );
    }


}
