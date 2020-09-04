package me.orlaando.ARR.configuration;

import com.google.common.base.Preconditions;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public final class TranslatableMessage {

    private final String key;

    private TranslatableMessage(@NotNull final String key) {
        this.key = key;
    }

    /**
     * Create a new translatable message with a given message key
     *
     * @param key Message key
     * @return Message instance
     */
    public static String of(@NotNull final String key) {
        return ChatColor.translateAlternateColorCodes('&', MessageHandler.getInstance().getTranslation("prefix") +
                MessageHandler.getInstance().getTranslation(key));
    }

}
