package com.patrones.playwright.singleton;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Page;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Map;

public class PlaywrightDriver {

    private static PlaywrightDriver instance;
    private Playwright playwright;
    private Browser browser;
    private Page page;

    // 3. Constructor privado para evitar instanciación externa
    private PlaywrightDriver() {
        // Inicialización aquí si la configuración es fija
    }

    // 4. Método estático para obtener la única instancia (Lazy Initialization)
    public static PlaywrightDriver getInstance() {
        if (instance == null) {
            instance = new PlaywrightDriver();
            instance.initDriver(); // Inicializar Playwright al crear la instancia
        }
        return instance;
    }

    // 5. Método para inicializar Playwright y el navegador
    private void initDriver() {
        Dotenv dotenv = Dotenv.load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
        this.playwright = Playwright.create();

        System.out.println("Inicializando Playwright");
        // Uso del Factory Method
        browser = com.patrones.playwright.factory.BrowserFactory.createBrowser(playwright);
        page = browser.newPage();
    }

    // 6. Métodos públicos para acceder a Page y Browser
    public Page getPage() {
        return page;
    }

    public Browser getBrowser() {
        return browser;
    }

    // 7. Método para cerrar los recursos
    public void closeDriver() {
        if (page != null) {
            page.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
        instance = null; // Reiniciar la instancia para futuras pruebas (si es necesario)
        System.out.println("Playwright cerrado.");
    }
}
