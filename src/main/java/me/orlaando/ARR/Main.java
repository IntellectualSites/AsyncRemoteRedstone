package me.orlaando.ARR;

import co.aikar.commands.PaperCommandManager;
import me.orlaando.ARR.commands.CommandPing;
import me.orlaando.ARR.configuration.MessageHandler;
import me.orlaando.ARR.storage.SQLiteStorage;
import org.bukkit.plugin.java.JavaPlugin;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main extends JavaPlugin {

    private static SQLiteStorage sqLiteStorage;
    private MessageHandler messageHandler;
    private static PaperCommandManager commandManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    @Override public void onEnable() {
        this.messageHandler = new MessageHandler(this);

        try {
            sqLiteStorage = new SQLiteStorage(this);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        if (sqLiteStorage == null || !sqLiteStorage.startStorage()) {
            LOGGER.error("Failed to start storage system");
        }

        commandManager = new PaperCommandManager(this);

        commandManager.registerCommand(new CommandPing());

    }

    @Override public void onDisable() {
        getSqLiteStorage().stopStorage();
    }

    @NotNull public SQLiteStorage getSqLiteStorage() {
        return sqLiteStorage;
    }


}
