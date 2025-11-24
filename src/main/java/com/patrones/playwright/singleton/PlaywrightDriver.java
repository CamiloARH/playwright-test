package com.patrones.playwright.singleton;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Browser.NewContextOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.patrones.playwright.factory.BrowserFactory;
import com.patrones.playwright.factory.MobileProfiles;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.Map;

public class PlaywrightDriver {

    private static PlaywrightDriver instance;

    // 1. CAMPOS CENTRALIZADOS
    private Playwright playwright; // ⬅️ NUEVO CAMPO
    private Browser browser;
    private Page page;

    // 2. CONSTRUCTOR PRIVADO (patrón Singleton)
    private PlaywrightDriver() {}

    // 3. MÉTODO ESTATICO PARA OBTENER LA ÚNICA INSTANCIA
    public static PlaywrightDriver getInstance() {
        if (instance == null) {
            instance = new PlaywrightDriver();
            instance.initDriver();
        }
        return instance;
    }

    // 4. MÉTODO PARA INICIALIZAR PLAYWRIGHT Y EL NAVEGADOR
    private void initDriver() {
        // Cargar DotEnv y establecer propiedades del sistema
        Dotenv dotenv = Dotenv.load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

        // Crear la instancia central de Playwright
        this.playwright = Playwright.create();

        // Pasar la instancia a la Factory para crear el navegador (Local o Remoto)
        browser = BrowserFactory.createBrowser(this.playwright);

        // LÓGICA DE EMULACIÓN MÓVIL (Local)
        String localDevice = System.getProperty("LOCAL_DEVICE");
        NewContextOptions contextOptions = new NewContextOptions();

        if (localDevice != null && !"NONE".equalsIgnoreCase(localDevice)) {
            try {
                // USAMOS EL MAPA MANUALMENTE CREADO (MobileProfiles)
                Map<String, Object> deviceProfile = MobileProfiles.DEVICE_PROFILES.get(localDevice);

                if (deviceProfile != null) {
                    System.out.println("Emulación móvil local activada: " + localDevice);

                    // Aplicar las propiedades del perfil al contexto
                    contextOptions.setViewportSize((Integer) deviceProfile.get("viewport.width"),
                            (Integer) deviceProfile.get("viewport.height"));
                    contextOptions.setUserAgent((String) deviceProfile.get("userAgent"));
                    contextOptions.setIsMobile(true);
                    contextOptions.setHasTouch(true);
                }
            } catch (Exception e) {
                System.err.println("Advertencia: Error al aplicar perfil móvil. Usando configuración por defecto.");
            }
        }

        // Crear la página con las opciones de contexto (si son default, mobile o remoto)
        page = browser.newContext(contextOptions).newPage();
    }

    // 5. MÉTODOS PÚBLICOS PARA ACCEDER A PAGE Y BROWSER (getters)
    public Page getPage() {
        return page;
    }

    public Browser getBrowser() {
        return browser;
    }

    // 6. MÉTODO PARA CERRAR LOS RECURSOS
    public void closeDriver() {
        if (browser != null) {
            browser.close();
        }
        if (this.playwright != null) {
            this.playwright.close();
        }
        instance = null;
    }
}