package com.trainer.trainer_booking_api.service;

import com.trainer.trainer_booking_api.dto.request.PagoRequestDTO;
import com.trainer.trainer_booking_api.dto.response.PagoResponseDTO;
import com.trainer.trainer_booking_api.entity.Pago;
import com.trainer.trainer_booking_api.entity.enums.EstadoPago;
import com.trainer.trainer_booking_api.exception.RecursoNoEncontradoException;
import com.trainer.trainer_booking_api.repository.PagoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;

    public PagoService(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    private PagoResponseDTO convertirADTO(Pago pago) {
        return new PagoResponseDTO(
                pago.getIdPago(),
                pago.getReserva().getIdReserva(),
                pago.getEstado().name(),
                pago.getMonto(),
                pago.getMetodoPago(),
                pago.getReferenciaPago(),
                pago.getFechaPago()
        );
    }

    public PagoResponseDTO obtenerPorReserva(Integer idReserva) {
        Pago pago = pagoRepository.findByReservaIdReserva(idReserva)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un pago para la reserva con id: " + idReserva));
        return convertirADTO(pago);
    }

    @Transactional
    public PagoResponseDTO confirmarPago(Integer idPago, PagoRequestDTO dto) {
        Pago pago = pagoRepository.findById(idPago)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pago no encontrado con id: " + idPago));

        if (pago.getEstado() != EstadoPago.PENDIENTE) {
            throw new RuntimeException("Este pago ya fue procesado (estado actual: " + pago.getEstado() + ")");
        }

        pago.setEstado(EstadoPago.APROBADO);
        pago.setMetodoPago(dto.getMetodoPago());
        pago.setReferenciaPago(dto.getReferenciaPago());
        pago.setFechaPago(LocalDateTime.now());

        return convertirADTO(pagoRepository.save(pago));
    }
}
