package com.example.rabbitmqconsumer.listeners;

import com.example.rabbitmqconsumer.dto.AlertaRequest;
import com.example.rabbitmqconsumer.services.AlertaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
class RabbitMQConsumerTest {

    @Autowired
    private AlertaConsumer alertaConsumer; // 1. Inyectamos directamente el consumidor

    @MockBean
    private AlertaService alertaService; // 2. Mantenemos el mock

    @Test
    void testConsumoMensaje_LlamaAlServicio() {
        // Creamos el objeto (el mismo que recibiría RabbitMQ)
        AlertaRequest alerta = AlertaRequest.builder().nombrePaciente("Test").habitacion("101")
                .colorAlerta("VERDE").estado("PENDIENTE").build();

        // 3. Llamamos al método directamente, saltándonos el broker (es más rápido y fiable)
        alertaConsumer.recibirMensaje(alerta);

        // 4. Verificamos que el servicio fue llamado
        verify(alertaService).guardarAlerta(any(AlertaRequest.class));
    }
}
