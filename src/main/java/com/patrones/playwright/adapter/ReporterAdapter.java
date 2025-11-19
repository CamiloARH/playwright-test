package com.patrones.playwright.adapter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReporterAdapter implements CustomLogger {

    // Obtenemos una instancia de Log4j.
    // Usaremos el nombre de la clase que lo usa (ReporterAdapter) como el nombre del Logger.
    private static final Logger log = LogManager.getLogger(ReporterAdapter.class);

    @Override
    public void logMessage(String level, String message) {

        // La lógica del Adapter ahora llama a los métodos nativos de Log4j
        switch (level.toUpperCase()) {
            case "INFO":
                log.info(message); // Envía un mensaje INFO a Log4j
                break;
            case "WARN":
                log.warn(message); // Envía un mensaje WARN
                break;
            case "ERROR":
                log.error(message); // Envía un mensaje ERROR
                break;
            default:
                log.debug(message); // Para cualquier otro nivel, usa DEBUG
                break;
        }
    }
}