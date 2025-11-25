package com.patrones.playwright.tests;

import com.microsoft.playwright.Page;
import com.patrones.playwright.abstractfactory.DevEnvFactory;
import com.patrones.playwright.abstractfactory.EnvironmentFactory;
import com.patrones.playwright.abstractfactory.QAEnvFactory;
import com.patrones.playwright.adapter.CustomLogger;
import com.patrones.playwright.adapter.ReporterAdapter;
import com.patrones.playwright.builder.UserData;
import com.patrones.playwright.singleton.PlaywrightDriver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.patrones.playwright.tests.LoginTest.realizarLogin;


public class AgregarCarroCompras {

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
    public void testCarroCompras() {
        realizarLogin(page, baseUrl, testUser);
        logger.logMessage("INFO", "Login realizado con éxito.");
    }
}