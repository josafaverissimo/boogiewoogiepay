package com.josafaverissimo.worker.infraestructure;

import com.josafaverissimo.worker.infraestructure.enums.PaymentProcessorEnum;
import com.josafaverissimo.worker.usecases.ProcessPaymentUseCase;

public class DummyWarmup {
  public static void warmup() {
    PaymentProcessorEnum[] paymentProcessors = {
      PaymentProcessorEnum.DEFAULT,
      PaymentProcessorEnum.FALLBACK
    };

    for(int i = 0; i < 100; i++) {
      var processPaymentUsecase = new ProcessPaymentUseCase();

      var paymentProcessor = paymentProcessors[i % 2];

      processPaymentUsecase.requestProcessPaymentByProcessor(
        paymentProcessor,
        "{" 
        + "\"correlationId\": \"4a7901b8-7d26-4d9d-aa19-4dc1c7cf60b3\","
        + "\"amount\": 19.90,"
        + "\"requestedAt\": \"2025-07-15T12:34:56.000Z\""
        + "}"
      );
    }
  }
}
