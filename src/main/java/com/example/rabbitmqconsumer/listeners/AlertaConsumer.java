package com.example.rabbitmqconsumer.listeners;

import com.example.rabbitmqconsumer.dto.AlertaRequest;
import com.example.rabbitmqconsumer.services.AlertaService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlertaConsumer {

    private final AlertaService alertaService;

    // Cambiado de "micola" a "Cola-Alerta-BD" para coincidir con la nueva arquitectura
    @RabbitListener(queues = "Cola-Alerta-BD")
    public void recibirMensaje(AlertaRequest alerta) {
        System.out.println(
                "Procesando Alerta desde Cola-Alerta-BD para: " + alerta.getNombrePaciente());
        alertaService.guardarAlerta(alerta);
    }
}
