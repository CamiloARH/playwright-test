package com.patrones.playwright.abstractfactory;

import com.patrones.playwright.builder.UserData;

public class DevEnvFactory implements EnvironmentFactory {

    @Override
    public URLProvider createURLProvider() {
        return () -> "https://www.exito.com/";
    }

    @Override
    public CredentialsProvider createCredentialsProvider() {

        return () -> UserData.builder("Carlos", "ImNot", "CarlosPass")
                .withEmail("carlos@yopmail.com")
                .asTester()
                .build();
    }
}