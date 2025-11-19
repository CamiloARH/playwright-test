package com.patrones.playwright.builder;

public class UserData {
    private final String username;
    private final String password;
    private final String email;
    private final boolean isAdmin;

    // Constructor privado, solo accesible desde el Builder
    private UserData(UserBuilder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.email = builder.email;
        this.isAdmin = builder.isTester;
    }

    // Getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public boolean isAdmin() { return isAdmin; }

    @Override
    public String toString() {
        return "User(Username: " + username + ", Email: " + email + ", Admin: " + isAdmin + ")";
    }

    // Método estático para obtener la instancia del Builder
    public static UserBuilder builder(String username, String password) {
        return new UserBuilder(username, password);
    }

    // Clase interna Estática: El Constructor (Builder)
    public static class UserBuilder {
        // Campos requeridos
        private final String username;
        private final String password;

        // Campos opcionales (con valores por defecto)
        private String email = "default@yopmail.com";
        private boolean isTester = false;

        // Constructor del Builder (solo toma los campos requeridos)
        public UserBuilder(String username, String password) {
            this.username = username;
            this.password = password;
        }

        // Métodos para establecer los campos opcionales
        public UserBuilder withEmail(String email) {
            this.email = email;
            return this; // Importante: retorna la misma instancia de Builder
        }

        public UserBuilder asTester() {
            this.isTester = true;
            return this;
        }

        // Método final que construye y devuelve el objeto principal
        public UserData build() {
            return new UserData(this);
        }
    }
}
