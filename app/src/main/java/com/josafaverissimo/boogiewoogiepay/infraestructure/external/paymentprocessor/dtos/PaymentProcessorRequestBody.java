package com.josafaverissimo.boogiewoogiepay.infraestructure.external.paymentprocessor.dtos;

import java.time.ZonedDateTime;

public record PaymentProcessorRequestBody(String correlationId, double amount, String requestAt) {
}
