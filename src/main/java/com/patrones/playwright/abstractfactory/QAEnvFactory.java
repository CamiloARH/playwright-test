package com.patrones.playwright.abstractfactory;

import com.patrones.playwright.builder.UserData;

public class QAEnvFactory implements EnvironmentFactory {

    @Override
    public URLProvider createURLProvider() {
        return () -> "https://www.exito.com/";
    }

    @Override
    public CredentialsProvider createCredentialsProvider() {

        return () -> UserData.builder("Camilo A", "Rodr","Testpass123Exito")
                .withEmail("tester@yopmail.com")
                .build();
    }
}