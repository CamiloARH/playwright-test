package com.patrones.playwright.abstractfactory;

import com.patrones.playwright.builder.UserData;

public class DevEnvFactory implements EnvironmentFactory {

    @Override
    public URLProvider createURLProvider() {
        return () -> "https://staging.miaplicacion.com"; // URL ficticia de Staging
    }

    @Override
    public CredentialsProvider createCredentialsProvider() {
        // Devuelve un usuario preconfigurado para Staging
        return () -> UserData.builder("Carlos", "ImNot", "CarlosPass")
                .withEmail("carlos@yopmail.com")
                .asTester()
                .build();
    }
}