package com.trainer.trainer_booking_api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PagoResponseDTO {
    private Integer idPago;
    private Integer idReserva;
    private String estado;
    private BigDecimal monto;
    private String metodoPago;
    private String referenciaPago;
    private LocalDateTime fechaPago;

    public PagoResponseDTO() {}
    public PagoResponseDTO(Integer idPago, Integer idReserva, String estado, BigDecimal monto, String metodoPago, String referenciaPago, LocalDateTime fechaPago) {
        this.idPago = idPago; this.idReserva = idReserva; this.estado = estado;
        this.monto = monto; this.metodoPago = metodoPago; this.referenciaPago = referenciaPago; this.fechaPago = fechaPago;
    }

    public Integer getIdPago() { return idPago; }
    public void setIdPago(Integer idPago) { this.idPago = idPago; }
    public Integer getIdReserva() { return idReserva; }
    public void setIdReserva(Integer idReserva) { this.idReserva = idReserva; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    public String getReferenciaPago() { return referenciaPago; }
    public void setReferenciaPago(String referenciaPago) { this.referenciaPago = referenciaPago; }
    public LocalDateTime getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDateTime fechaPago) { this.fechaPago = fechaPago; }
}