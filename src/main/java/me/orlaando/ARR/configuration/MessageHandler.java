package me.orlaando.ARR.configuration;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import me.orlaando.ARR.Main;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

public class MessageHandler {

    private static final Gson GSON =
            new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageHandler.class);
    private static MessageHandler instance;
    private final Map<String, String> messages = Maps.newHashMap();

    public MessageHandler(@NotNull final Main ARR) {
        instance = this;
        // Add defaults
        try (final JsonReader reader = GSON.newJsonReader(new InputStreamReader(
                Objects.requireNonNull(ARR.getResource("messages.json"))))) {
            final JsonObject object = GSON.fromJson(reader, JsonObject.class);
            for (final Map.Entry<String, JsonElement> elements : object.entrySet()) {
                messages.put(elements.getKey(), elements.getValue().getAsString());
            }
        } catch (final IOException e) {
            LOGGER.error("Failed to load messages", e);
        }
        ARR.saveResource("messages.json", false);
        try (final JsonReader reader = GSON.newJsonReader(Files
                .newReader(new File(ARR.getDataFolder(), "messages.json"),
                        StandardCharsets.UTF_8))) {
            final JsonObject object = GSON.fromJson(reader, JsonObject.class);
            for (final Map.Entry<String, JsonElement> elements : object.entrySet()) {
                messages.put(elements.getKey(), elements.getValue().getAsString());
            }
        } catch (final IOException e) {
            LOGGER.error("Failed to load messages", e);
        }
    }

    public static MessageHandler getInstance() {
        return instance;
    }

    /**
     * Get a translation from a translation key
     *
     * @param key Translation Key
     * @return Translation
     * @throws IllegalArgumentException If the translation does not exist
     */
    @NotNull public String getTranslation(@NotNull final String key) {
        Preconditions.checkNotNull(key, "Key cannot be null");
        final String value = this.messages.get(key);
        if (value == null) {
            throw new IllegalArgumentException("There is no message with that key: " + key);
        }
        return value;
    }

}
