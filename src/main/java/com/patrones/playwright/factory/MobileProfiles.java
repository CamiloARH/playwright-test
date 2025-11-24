package com.patrones.playwright.factory;

import java.util.HashMap;
import java.util.Map;

public class MobileProfiles {

    public static final Map<String, Map<String, Object>> DEVICE_PROFILES = new HashMap<>();

    static {
        // PERFIL: IPHONE 13
        Map<String, Object> iphone13 = new HashMap<>();
        iphone13.put("viewport.width", 390);
        iphone13.put("viewport.height", 844);
        // User Agent de iOS 15
        iphone13.put("userAgent", "Mozilla/5.0 (iPhone; CPU iPhone OS 15_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.0 Mobile/15E148 Safari/604.1");
        DEVICE_PROFILES.put("iPhone 13", iphone13);

        // PERFIL: PIXEL 5
        Map<String, Object> pixel5 = new HashMap<>();
        pixel5.put("viewport.width", 393);
        pixel5.put("viewport.height", 851);
        // User Agent de Android 11/Pixel
        pixel5.put("userAgent", "Mozilla/5.0 (Linux; Android 11; Pixel 5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.210 Mobile Safari/537.36");
        DEVICE_PROFILES.put("Pixel 5", pixel5);

        // Puedes añadir más perfiles aquí si los necesitas
    }
}