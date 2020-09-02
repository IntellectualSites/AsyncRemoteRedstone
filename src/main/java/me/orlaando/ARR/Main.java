package me.orlaando.ARR;

import me.orlaando.ARR.storage.SQLiteStorage;
import org.bukkit.plugin.java.JavaPlugin;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main extends JavaPlugin {

    private static SQLiteStorage sqLiteStorage;

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    @Override public void onEnable() {

        try {
            sqLiteStorage = new SQLiteStorage();
        } catch (final Exception e) {
            e.printStackTrace();
        }

        if (sqLiteStorage == null || !sqLiteStorage.startStorage()) {
            LOGGER.error("Failed to start storage system");
        }

    }

    @Override public void onDisable() {
        getSqLiteStorage().startStorage();
    }

    @NotNull public SQLiteStorage getSqLiteStorage() {
        return sqLiteStorage;
    }


}
