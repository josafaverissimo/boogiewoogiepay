package com.josafaverissimo.worker.infraestructure.dtos;

public record PaymentDataToSendDto(
  String correlationId,
  double amount,
  String requestedAt
) { }
