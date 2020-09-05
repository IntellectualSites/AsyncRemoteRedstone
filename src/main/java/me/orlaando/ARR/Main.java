package me.orlaando.ARR;

import co.aikar.commands.PaperCommandManager;
import me.orlaando.ARR.adapters.*;
import me.orlaando.ARR.commands.CommandARR;
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
    private static MessageHandler messageHandler;
    private PaperCommandManager commandManager;

    // since the proper NMS class was assigned onEnable, we are now backwards compatible!
    private static NMSAdapter nmsAdapter;

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    @Override public void onEnable() {
        ARR = this;

        setupNMS();

        messageHandler = new MessageHandler(ARR);

        try {
            sqLiteStorage = new SQLiteStorage(ARR);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        if (sqLiteStorage == null || !sqLiteStorage.startStorage()) {
            LOGGER.error("Failed to start storage system");
        }

        commandManager = new PaperCommandManager(ARR);

        commandManager.registerCommand(new CommandARR());

        Bukkit.getPluginManager().registerEvents(new InteractionListener(), this);

    }

    @Override public void onDisable() {
        getSqLiteStorage().stopStorage();
    }

    public void setupNMS() {
        String version;
        try {
            version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        } catch (ArrayIndexOutOfBoundsException exception) {
            LOGGER.error("You aren't using a supported Mincraft version..");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        LOGGER.info("Your server is running version " + version);

        switch (version) {
            case "v1_16_R2":
                nmsAdapter = new mc1_16_2();
            case "v1_15_R1":
                nmsAdapter = new mc1_15_2();
            case "v1_14_R1":
                nmsAdapter = new mc1_14_4();
            case "v1_13_R2":
                nmsAdapter = new mc1_13_2();
        }

        if (version == null) {
            LOGGER.error("You aren't using a supported Mincraft version..");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    public void reloadMessages() {
        messageHandler = new MessageHandler(ARR);
    }

    public static MessageHandler getMessageHandler() {
        return messageHandler;
    }

    @NotNull public static SQLiteStorage getSqLiteStorage() {
        return sqLiteStorage;
    }

    @NotNull public static Main getARR() {
        return ARR;
    }

    @NotNull public static NMSAdapter getNmsAdapter() {
        return nmsAdapter;
    }


}
