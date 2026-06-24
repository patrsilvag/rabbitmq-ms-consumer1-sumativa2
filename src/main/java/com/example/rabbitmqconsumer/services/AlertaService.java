package com.example.rabbitmqconsumer.services;

import com.example.rabbitmqconsumer.models.Alerta;
import com.example.rabbitmqconsumer.repositories.AlertaRepository;
import com.example.rabbitmqconsumer.dto.AlertaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AlertaService { // Nombre de clase cambiado

    private final AlertaRepository repository; // Inyecta el nuevo repositorio

    public void guardarAlerta(AlertaRequest request) {
        // Mapea el DTO a la Entidad Alerta
        Alerta alerta = Alerta.builder().nombrePaciente(request.getNombrePaciente())
                .habitacion(request.getHabitacion()).colorAlerta(request.getColorAlerta())
                .signosVitales(request.getSignosVitales()).estado(request.getEstado())
                .fechaHoraRegistro(LocalDateTime.now()) // Asegúrate de tener este campo en
                                                        // Alerta.java
                .build();

        repository.save(alerta); // Guarda en ALERTAS_VITALES
    }
}
