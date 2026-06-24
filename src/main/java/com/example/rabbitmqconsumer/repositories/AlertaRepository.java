package com.example.rabbitmqconsumer.repositories;

import com.example.rabbitmqconsumer.models.Alerta; 
import org.springframework.data.jpa.repository.JpaRepository;


public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    // Aquí puedes añadir métodos personalizados de consulta si los necesitas
}
