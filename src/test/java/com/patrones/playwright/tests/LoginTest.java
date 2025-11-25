package com.patrones.playwright.tests;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.patrones.playwright.abstractfactory.*;
import com.patrones.playwright.adapter.*;

import com.patrones.playwright.builder.UserData;
import com.patrones.playwright.singleton.PlaywrightDriver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

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
    public static void realizarLogin(Page page, String baseUrl, UserData testUser) {
        page.navigate(baseUrl);

        page.click("#icon-cuenta");
        logger.logMessage("INFO", "Mi cuenta");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Ingresar con email y contrase")).click();

        page.getByPlaceholder("Ingresa tu email").fill(testUser.getEmail());
        logger.logMessage("INFO", "Llenando campo 'First name' con usuario: " + testUser.getEmail());
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("show-password")).fill(testUser.getPassword());

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Entrar")).click();
        logger.logMessage("INFO", "Click en 'Entrar' para Realizar el login.");
    }

    @Test
    public void testSuccessfulLogin() {
        logger.logMessage("INFO", "Ejecutando Test de Login en: " + baseUrl);

        realizarLogin(page, baseUrl, testUser);

        page.waitForLoadState();
//        page.getByText("Mi cuenta").waitFor();
        page.click("#icon-cuenta");

        assertThat(page.getByText("tester@yopmail.com")).containsText(testUser.getEmail());

        page.getByTestId("store-modal").getByRole(AriaRole.BUTTON).filter(new Locator.FilterOptions().setHasText(Pattern.compile("^$"))).click();

        page.pause();

    }

    // Teardown: Cerrar la única instancia de Playwright al finalizar
    @AfterAll
    public static void teardown() {
        PlaywrightDriver.getInstance().closeDriver();
    }
}