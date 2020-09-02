package me.orlaando.ARR.configuration;

import com.google.common.base.Preconditions;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public final class TranslatableMessage implements Message {

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
    public static TranslatableMessage of(@NotNull final String key) {
        return new TranslatableMessage(Preconditions.checkNotNull(key, "Key may not be null"));
    }

    /**
     * Get the translated message
     *
     * @return Translated message
     */
    public String toString() {
        return this.key;
    }

    @Override public String getMessage() {
        return ChatColor.translateAlternateColorCodes('&', MessageHandler.getInstance().getTranslation("prefix") +
                MessageHandler.getInstance().getTranslation(this.key));
    }

}
