package com.josafaverissimo.worker.infraestructure.dtos;

public record ProcessPaymentDto(String correlationId, double amount) {}
