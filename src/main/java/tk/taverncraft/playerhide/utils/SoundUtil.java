package tk.taverncraft.playerhide.utils;

import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import tk.taverncraft.playerhide.Main;

public class SoundUtil {

    /**
     * Plays a sound for a player based on the configuration path.
     * Expected format in config: "SOUND_NAME;volume;pitch"
     * Example: "ENTITY_ARMADILLO_EAT;100;3"
     *
     * @param player The player to play the sound for
     * @param path The configuration path to the sound setting
     */
    public static void playSound(Player player, String path) {
        FileConfiguration config = Main.instance.getConfig();
        String soundConfig = config.getString(path);

        if (soundConfig == null || soundConfig.isEmpty()) {
            return;
        }

        try {
            String[] soundParts = soundConfig.split(";");
            if (soundParts.length != 3) {
                Main.instance.getLogger().warning("Invalid sound format at " + path + ". Expected format: SOUND_NAME;volume;pitch");
                return;
            }

            String soundName = soundParts[0].toUpperCase();
            float volume = Float.parseFloat(soundParts[1]) / 100f; // Convert percentage to decimal
            float pitch = Float.parseFloat(soundParts[2]);

            try {
                Sound sound = Sound.valueOf(soundName);
                player.playSound(player.getLocation(), sound, volume, pitch);
            } catch (IllegalArgumentException e) {
                Main.instance.getLogger().warning("Invalid sound name at " + path + ": " + soundName);
            }
        } catch (NumberFormatException e) {
            Main.instance.getLogger().warning("Invalid volume or pitch format at " + path);
        } catch (Exception e) {
            Main.instance.getLogger().warning("Error playing sound at " + path + ": " + e.getMessage());
        }
    }
}