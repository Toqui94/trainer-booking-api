package com.trainer.trainer_booking_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// @RestController le dice a Spring: "Esta clase recibe peticiones HTTP y devuelve JSON"
@RestController

// @RequestMapping define la URL base para TODOS los metodos de esta clase
// Entonces todas las URLs de este controller empiezan con /api
@RequestMapping("/api")
public class HealthController {

    // @GetMapping significa: "Cuando alguien haga GET /api/health, ejecuta este metodo"
    @GetMapping("/health")
    public String checkHealth() {
        // Retornamos un String. Spring lo convierte automaticamente en JSON
        return "El servidor está vivo y funcionando correctamente 🚀";
    }
}
