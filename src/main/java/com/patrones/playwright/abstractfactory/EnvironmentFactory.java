package com.patrones.playwright.abstractfactory;

public interface EnvironmentFactory {
    // Método para obtener el proveedor de URL para este ambiente
    URLProvider createURLProvider();

    // Método para obtener las credenciales para este ambiente
    // Usaremos el objeto UserData del patrón Builder que ya creaste.
    CredentialsProvider createCredentialsProvider();
}
