package me.orlaando.ARR.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CatchUnknown;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.HelpCommand;
import co.aikar.commands.annotation.Single;
import co.aikar.commands.annotation.Subcommand;
import me.orlaando.ARR.configuration.TranslatableMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("ping")
@CommandPermission("soc.parent")
public class CommandPing extends BaseCommand {

    @Subcommand("foo")
    @CommandPermission("soc.foo")
    public void onFoo1(Player player) {
        player.sendMessage("you foo'd");
    }

    @Subcommand("foo")
    public void onFoo2(CommandSender sender, @Single String foo) {
        sender.sendMessage("You foo'd with " + foo);
    }

    @CatchUnknown
    @HelpCommand
    public void onUnknown(CommandSender sender) {
        sender.sendMessage(
                String.format(
                TranslatableMessage.of("command.syntax").getMessage(),
                "/ping [foo] [player]")
        );
    }

    @Default
    public void test(Player player) {
        player.sendMessage(TranslatableMessage.of("command.ping").getMessage());
    }

}