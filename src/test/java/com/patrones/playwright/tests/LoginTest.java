package com.patrones.playwright.tests;

import com.microsoft.playwright.Page;
import com.patrones.playwright.abstractfactory.*;
import com.patrones.playwright.adapter.*; // Importamos las clases del Adapter

import com.patrones.playwright.builder.UserData;
import com.patrones.playwright.singleton.PlaywrightDriver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LoginTest {

    private static Page page;
    private static EnvironmentFactory factory;
    private static UserData testUser;
    private static String baseUrl;

    // **NUEVO:** Instancia del Logger (usamos la interfaz)
    private static CustomLogger logger = new ReporterAdapter();

    @BeforeAll
    public static void setup() {

        //Si se quiere cambiar el navegador desde línea de comandos,
        //se puede descomentar este bloque.

//        String browserToLaunch = System.getProperty("browser", "firefox");
//        PlaywrightDriver.setBrowserName(browserToLaunch);

        page = PlaywrightDriver.getInstance().getPage();

        String environment = System.getProperty("env", "Qa");

        if (environment.equalsIgnoreCase("Dev")) {
            factory = new DevEnvFactory();
        } else {
            factory = new QAEnvFactory();
        }

        baseUrl = factory.createURLProvider().getBaseUrl();
        testUser = factory.createCredentialsProvider().getDefaultUser();

        // Uso del Adapter para reportar la configuración
        logger.logMessage("INFO", "Configurando el ambiente: " + environment);
        logger.logMessage("INFO", "URL Base obtenida: " + baseUrl);
    }

    @Test
    public void testSuccessfulLogin() {
        // Reemplaza los System.out.println() por llamadas al logger.
        logger.logMessage("INFO", "Ejecutando Test de Login en: " + baseUrl);

        // Paso 1: Navegación
        page.navigate(baseUrl);

        // Paso 2: Simular acciones de inicio de sesión
        page.click("text=Sign in");
        logger.logMessage("INFO", "Click en 'Sign in'");

        page.click("text=Create account");
        logger.logMessage("INFO", "Click en 'Create account'");

        // Uso del Builder y Playwright
        page.getByLabel("First name").fill(testUser.getUsername());
        logger.logMessage("INFO", "Llenando campo 'First name' con usuario: " + testUser.getUsername());

        page.getByLabel("Last name (optional)").fill(testUser.getLastname());
        page.click("text=Next");
        logger.logMessage("INFO", "Click en 'Next' para continuar registro.");

        page.pause();

        // Paso 3: Verificación (se puede mejorar con aserciones reales)
        String currentUrl = page.url();
        logger.logMessage("INFO", "URL actual al finalizar: " + currentUrl);

        if (!currentUrl.contains("youtube")) {
            logger.logMessage("ERROR", "La navegación no fue exitosa o la URL no es la esperada.");
        }
    }

    // Teardown: Cerrar la única instancia de Playwright al finalizar
    @AfterAll
    public static void teardown() {
        PlaywrightDriver.getInstance().closeDriver();
    }
}