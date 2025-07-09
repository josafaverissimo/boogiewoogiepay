package com.josafaverissimo.boogiewoogiepay.infraestructure.dtos;

public record PaymentRequestBody(String correlationId, double amount) {}
