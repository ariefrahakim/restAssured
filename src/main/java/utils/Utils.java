package utils;

import java.util.UUID;

public class Utils {

    // Generate random title tanpa prefix
    public static String generateRandomTitle() {
        // Ambil 8 karakter pertama dari UUID
        return "Title-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
