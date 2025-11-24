package com.patrones.playwright.tests;

import com.microsoft.playwright.Page;
import com.patrones.playwright.abstractfactory.*;
import com.patrones.playwright.adapter.*;

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

    private static CustomLogger logger = new ReporterAdapter();

    @BeforeAll
    public static void setup() {

        page = PlaywrightDriver.getInstance().getPage();

        String environment = System.getProperty("env", "Qa");

        if (environment.equalsIgnoreCase("Dev")) {
            factory = new DevEnvFactory();
        } else {
            factory = new QAEnvFactory();
        }

        baseUrl = factory.createURLProvider().getBaseUrl();
        testUser = factory.createCredentialsProvider().getDefaultUser();

        logger.logMessage("INFO", "Configurando el ambiente: " + environment);
        logger.logMessage("INFO", "URL Base obtenida: " + baseUrl);
    }

    @Test
    public void testSuccessfulLogin() {
        logger.logMessage("INFO", "Ejecutando Test de Login en: " + baseUrl);

        page.navigate(baseUrl);

        page.click("text=Sign in");
        logger.logMessage("INFO", "Click en 'Sign in'");

        page.click("text=Create account");
        logger.logMessage("INFO", "Click en 'Create account'");

        page.getByLabel("First name").fill(testUser.getUsername());
        logger.logMessage("INFO", "Llenando campo 'First name' con usuario: " + testUser.getUsername());

        page.getByLabel("Last name (optional)").fill(testUser.getLastname());
        page.click("text=Next");
        logger.logMessage("INFO", "Click en 'Next' para continuar registro.");

        page.pause();

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