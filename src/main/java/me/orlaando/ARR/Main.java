package me.orlaando.ARR;

import co.aikar.commands.PaperCommandManager;
import me.orlaando.ARR.commands.CommandPing;
import me.orlaando.ARR.configuration.MessageHandler;
import me.orlaando.ARR.listeners.InteractionListener;
import me.orlaando.ARR.storage.SQLiteStorage;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main extends JavaPlugin {

    private static SQLiteStorage sqLiteStorage;
    private static Main ARR;
    private MessageHandler messageHandler;
    private PaperCommandManager commandManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    @Override public void onEnable() {
        ARR = this;

        this.messageHandler = new MessageHandler(ARR);

        try {
            sqLiteStorage = new SQLiteStorage(ARR);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        if (sqLiteStorage == null || !sqLiteStorage.startStorage()) {
            LOGGER.error("Failed to start storage system");
        }

        commandManager = new PaperCommandManager(ARR);

        commandManager.registerCommand(new CommandPing());

        Bukkit.getPluginManager().registerEvents(new InteractionListener(), this);

    }

    @Override public void onDisable() {
        getSqLiteStorage().stopStorage();
    }

    @NotNull public static SQLiteStorage getSqLiteStorage() {
        return sqLiteStorage;
    }

    @NotNull public static Main getARR() {
        return ARR;
    }


}
