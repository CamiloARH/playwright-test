package com.patrones.playwright.factory;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

public class BrowserFactory {

    // El método Factory: Crea el objeto Browser
    public static Browser createBrowser(Playwright playwright, String browserName) {

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
