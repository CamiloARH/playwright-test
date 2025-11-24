package com.patrones.playwright.factory;

import com.google.gson.Gson;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import java.util.HashMap;
import java.util.Map;

public class BrowserFactory {

    // Método principal para crear el objeto Browser
    public static Browser createBrowser(Playwright playwright) {
        String executionMode = System.getProperty("EXECUTION_MODE");

        if ("BROWSERSTACK".equalsIgnoreCase(executionMode)) {
            return createBrowserStackBrowser(playwright);
        } else {
            // Lógica para LOCAL
            String browserName = System.getProperty("browser");
            if (browserName == null) {
                browserName = System.getProperty("BROWSER_NAME", "chromium"); // Valor por defecto
            }
            return createLocalBrowser(playwright, browserName);
        }
    }

    private static Browser createBrowserStackBrowser(Playwright playwright) {
        // Obtener credenciales del .env
        String username = System.getProperty("BROWSERSTACK_USERNAME");
        String accessKey = System.getProperty("BROWSERSTACK_ACCESS_KEY");
        String deviceName = System.getProperty("DEVICE_NAME");

        // Construir el WebSocket Endpoint
        String browserstackWSEndpoint = String.format(
                "wss://%s:%s@hub-cloud.browserstack.com/playwright/v1/ws",
                username,
                accessKey
        );

        // 2. Definir las capacidades (BrowserStack Options)
        Map<String, Object> browserstackOptions = new HashMap<>();
        browserstackOptions.put("deviceName", deviceName != null ? deviceName : "Google Pixel 8");
        browserstackOptions.put("osVersion", "14.0");
        browserstackOptions.put("browser", "chrome");
        browserstackOptions.put("buildName", "Playwright Java Mobile Test");

        // 3. Crear las opciones de conexión remota
        BrowserType.ConnectOptions connectOptions = new BrowserType.ConnectOptions();
        connectOptions.setHeaders(Map.of("browserStackOptions", new Gson().toJson(browserstackOptions)));

        // 4. Conectar a BrowserStack
        return playwright.chromium().connect(
                browserstackWSEndpoint,
                connectOptions
        );
    }

    private static Browser createLocalBrowser(Playwright playwright, String browserName) {

        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(false);

        switch (browserName.toLowerCase()) {
            case "chromium":
                System.out.println("On Chromium...");
                return playwright.chromium().launch(options);
            case "firefox":
                System.out.println("On Firefox...");
                return playwright.firefox().launch(options);
            case "webkit":
                System.out.println("On Safari...");
                return playwright.webkit().launch(options);
            default:
                System.out.println("You have provided an invalid browser name! Launching Chromium by default...");
                return playwright.chromium().launch(options);
        }
    }
}