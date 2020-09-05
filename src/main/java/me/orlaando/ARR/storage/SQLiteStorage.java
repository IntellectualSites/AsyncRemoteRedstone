package me.orlaando.ARR.storage;

import me.orlaando.ARR.Main;
import org.bukkit.Bukkit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.*;
import java.util.concurrent.CompletableFuture;

public class SQLiteStorage {
    private static final Logger LOGGER = LoggerFactory.getLogger(SQLiteStorage.class);

    //todo: actually setup table structure
    private static final String DDL1 = "CREATE TABLE IF NOT EXISTS `devices` ";
    private static final String DDL2 = "CREATE TABLE IF NOT EXISTS hjghjhjkhjk";

    private final Object statementLock = new Object();

    private final File file;
    private Connection connection;
    private final Main ARR;

    public SQLiteStorage(Main ARR) throws Exception {
        this.ARR = ARR;
        Class.forName("org.sqlite.JDBC");
        this.file = new File(ARR.getDataFolder(), "database.db");
        if (!file.exists()) {
            if (!file.createNewFile()) {
                throw new RuntimeException("Could not create database.db");
            }
        }
    }

    public PreparedStatement createStatement(String sql) {
        try {
            return this.connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public CompletableFuture<ResultSet> query( final PreparedStatement statement) {
            CompletableFuture<ResultSet> completableFuture = new CompletableFuture<>();
            Bukkit.getScheduler().runTaskAsynchronously(ARR, () -> {
                synchronized (this.statementLock) {
                    ResultSet resultSet = null;
                    try {
                        resultSet = statement.executeQuery();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    completableFuture.complete(resultSet);
                }
            });
            return completableFuture;
    }

    public void update(final PreparedStatement statement) {
        Bukkit.getScheduler().runTaskAsynchronously(ARR, () -> {
            synchronized (this.statementLock) {
                try {
                    statement.executeUpdate();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }

    public boolean startStorage() {
        final String connectionString = String.format("jdbc:sqlite:%s", this.file.getPath());
        try {
            this.connection = DriverManager.getConnection(connectionString);
        } catch (final Exception e) {
            LOGGER.error("Failed to initialize SQLite connection", e);
        }
        try {
            final Statement statement = this.getConnection().createStatement();
            statement.addBatch(DDL1);
            statement.addBatch(DDL2);
            //statement.executeBatch();
        } catch (final Exception e) {
            LOGGER.error("Failed to create event table", e);
        }
        return this.connection != null;
    }

    public void stopStorage() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (final Throwable throwable) {
                LOGGER.error("Failed to close SQLite connection", throwable);
            }
        }
    }

    private Connection getConnection() {
        return this.connection;
    }
}
