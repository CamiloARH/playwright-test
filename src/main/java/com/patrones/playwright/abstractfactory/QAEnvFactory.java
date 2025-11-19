package com.patrones.playwright.abstractfactory;

import com.patrones.playwright.builder.UserData;

public class QAEnvFactory implements EnvironmentFactory {

    @Override
    public URLProvider createURLProvider() {
        return () -> "https://www.youtube.com/?app=desktop&hl=us"; // URL de Producción (la que usas)
    }

    @Override
    public CredentialsProvider createCredentialsProvider() {
        // Devuelve un usuario para el ambiente de Producción (puedes ajustar el rol)
        return () -> UserData.builder("Camilo A", "Rodr","Testpass123")
                .withEmail("tester@yopmail.com")
                .build(); // Por defecto, rol Standard
    }
}