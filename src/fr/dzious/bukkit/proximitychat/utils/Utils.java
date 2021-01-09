package fr.dzious.bukkit.proximitychat.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import fr.dzious.bukkit.proximitychat.ProximityChat;

public class Utils {
    private Utils() {}

    public static String concatCommand(String label, String[] args, String delimiter) {
        StringBuilder sb = new StringBuilder();
        sb.append(label);
        for (int i = 0; i < args.length; i++) {
            if (i > 0)
                sb.append(delimiter);
            sb.append(args[i]);
        }
        return (sb.toString());
    }

    public static boolean copy(String source , String destination) {
        boolean success = true;

        Logger.instance.debugConsole("Copying : " + source + " to : " + destination);

        try {
            InputStream stream = ProximityChat.INSTANCE.getClass().getResourceAsStream(source);
            if (stream != null) {
                Files.copy(stream, Paths.get(destination));
                stream.close();
            } else
                Logger.instance.debugConsole("Source stream is null");
        } catch (IOException e) {
            Logger.instance.warning("Unable to copy : " + source + " to : " + destination);
            success = false;
        }
        return (success);
    }
}
