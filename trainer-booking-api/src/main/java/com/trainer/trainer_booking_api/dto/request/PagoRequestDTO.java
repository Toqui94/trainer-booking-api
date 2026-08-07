package com.trainer.trainer_booking_api.dto.request;

import jakarta.validation.constraints.NotBlank;

public class PagoRequestDTO {
    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago;
    private String referenciaPago;

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    public String getReferenciaPago() { return referenciaPago; }
    public void setReferenciaPago(String referenciaPago) { this.referenciaPago = referenciaPago; }
}