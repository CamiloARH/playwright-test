package com.patrones.playwright.factory;

import com.google.gson.Gson;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class BrowserFactory {

    public static Browser createBrowser(Playwright playwright) {
        // Lee el modo de ejecución (LOCAL por defecto)
        String executionMode = System.getProperty("EXECUTION_MODE");

        if ("BROWSERSTACK".equalsIgnoreCase(executionMode)) {
            return createBrowserStackBrowser(playwright);
        } else {

            String browserName = System.getProperty("BROWSER_NAME");
            return createLocalBrowser(playwright, browserName);
        }
    }

    private static Browser createBrowserStackBrowser(Playwright playwright) {

        String username = System.getProperty("BROWSERSTACK_USERNAME");
        String accessKey = System.getProperty("BROWSERSTACK_ACCESS_KEY");

        String remoteUrl = "wss://cdp.browserstack.com/playwright?caps=";

        if (username == null || accessKey == null) {
            throw new RuntimeException("BrowserStack credentials not provided.");
        }
        Map<String, Object> caps = new HashMap<>();

        //BrowserStack
        caps.put("browser", System.getProperty("BS_BROWSER", "chrome"));
        caps.put("browser_version", System.getProperty("BS_BROWSER_VERSION", "latest"));
        caps.put("project", System.getProperty("BS_PROJECT", "Patrones Playwright"));
        caps.put("build", System.getProperty("BS_BUILD", "Build Local"));
        caps.put("name", System.getProperty("BS_NAME", "Test Playwright"));

        String deviceId = System.getProperty("BS_DEVICE_ID", "1"); // 0 = Desktop by default

        switch (deviceId) {
            case "1":
                caps.put("device", "iPhone 14");
                caps.put("real_mobile", "true");
                caps.put("os_version", "16");
                break;
            case "2":
                caps.put("device", "Samsung Galaxy S23");
                caps.put("real_mobile", "true");
                caps.put("os_version", "13.0");
                break;
            default:
                System.out.println("Running on Desktop BrowserStack Browser...");
                break;
        }

        String capsJson = new Gson().toJson(caps);
        String connectUrl = remoteUrl + URLEncoder.encode(capsJson, StandardCharsets.UTF_8);

        System.out.println("Connecting to BrowserStack with caps:\n" + capsJson);

        return playwright.chromium().connect(
                connectUrl,
                new BrowserType.ConnectOptions()
                        .setHeaders(Map.of(
                                "Authorization", "Basic " + Base64.getEncoder()
                                        .encodeToString((username + ":" + accessKey).getBytes())
                        ))
        );
    }

    private static Browser createLocalBrowser(Playwright playwright, String browserName) {
        boolean headless = Boolean.parseBoolean(System.getProperty("HEADLESS", "false"));

        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(headless);

        return switch (browserName.toLowerCase()) {
            case "chromium" -> {
                System.out.println("On Chromium...");
                yield playwright.chromium().launch(options);
            }
            case "firefox" -> {
                System.out.println("On Firefox...");
                yield playwright.firefox().launch(options);
            }
            case "webkit" -> {
                System.out.println("On Safari...");
                yield playwright.webkit().launch(options);
            }
            default -> {
                System.out.println("You have provided an invalid browser name! Launching Chromium by default...");
                yield playwright.chromium().launch(options);
            }
        };
    }
}