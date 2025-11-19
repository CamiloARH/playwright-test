package com.patrones.playwright.tests;

import com.microsoft.playwright.Page;
import com.patrones.playwright.builder.UserData;
import com.patrones.playwright.singleton.PlaywrightDriver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LoginTest {

    private static Page page;

    // Setup: Obtener la única instancia de Page antes de todas las pruebas
    @BeforeAll
    public static void setup() {
        page = PlaywrightDriver.getInstance().getPage();
    }

    @Test
    public void testSuccessfulLogin() {
        System.out.println("Ejecutando Test de Login...");

        // La prueba usa la misma instancia de 'page' globalmente
        page.navigate("https://www.youtube.com/?app=desktop&hl=es");

        // Simular una acción de prueba
        page.click("text=Sign in");
        page.click("text=Create account");



        page.pause();

        // Verificar que la URL cambió
        String currentUrl = page.url();
        System.out.println("URL actual: " + currentUrl);

        // Agrega una aserción aquí: assertTrue(currentUrl.contains("intro"));
    }
    public void createUserData() {
        UserData userTest = UserData.builder("Camilo A", "Testpass123")
                .withEmail("tester@yopmail.com")
                .asTester()
                .build();
    }

    // Teardown: Cerrar la única instancia de Playwright al finalizar
    @AfterAll
    public static void teardown() {
        PlaywrightDriver.getInstance().closeDriver();
    }
}