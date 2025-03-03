package me.secondanticheat.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebhookNotifier {

    private final JavaPlugin plugin;
    private final String webhookUrl;

    public WebhookNotifier(JavaPlugin plugin) {
        this.plugin = plugin;
        FileConfiguration config = plugin.getConfig();
        this.webhookUrl = config.getString("webhook_url", "");
    }

    public void sendCheatAlert(String playerName, String cheatType) {
        if (webhookUrl.isEmpty()) {
            return; // Pokud není webhook nastaven, neodesíláme nic
        }

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                URL url = new URL(webhookUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                String jsonPayload = "{\"content\": \"⚠️ **Detekován cheat!** \\n" +
                        "**Hráč:** " + playerName + " \\n" +
                        "**Typ cheatu:** " + cheatType + " \\n" +
                        "**Server:** " + Bukkit.getServer().getName() + "\"}";

                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonPayload.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int responseCode = connection.getResponseCode();
                if (responseCode != 204) {
                    plugin.getLogger().warning("Nepodařilo se odeslat webhook (kód: " + responseCode + ")");
                }

                connection.disconnect();
            } catch (Exception e) {
                plugin.getLogger().warning("Chyba při odesílání webhooku: " + e.getMessage());
            }
        });
    }
}
