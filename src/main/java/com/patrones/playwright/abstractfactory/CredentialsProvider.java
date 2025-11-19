package com.patrones.playwright.abstractfactory;

import com.patrones.playwright.builder.UserData;

public interface CredentialsProvider {
    UserData getDefaultUser();
}
