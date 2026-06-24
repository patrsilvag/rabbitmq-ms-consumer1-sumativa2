package com.example.rabbitmqconsumer.listeners;

import com.example.rabbitmqconsumer.dto.AlertaRequest;
import com.example.rabbitmqconsumer.services.AlertaService; // Importa el nuevo servicio
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlertaConsumer { // Nombre de clase cambiado

    private final AlertaService alertaService; // Inyecta el nuevo servicio

    @RabbitListener(queues = "micola")
    public void recibirMensaje(AlertaRequest alerta) { // Recibe el DTO
        System.out.println("Procesando Alerta para: " + alerta.getNombrePaciente());
        alertaService.guardarAlerta(alerta); // Llama al nuevo método
    }
}
