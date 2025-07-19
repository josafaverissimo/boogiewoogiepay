package com.josafaverissimo.boogiewoogiepay.infraestructure;

import com.josafaverissimo.boogiewoogiepay.infraestructure.enums.PaymentProcessorEnum;

import com.josafaverissimo.boogiewoogiepay.usecases.PayHandlerUseCase;

public class DummyWarmup {
  public static void warmup() {
    PaymentProcessorEnum[] paymentProcessors = {
      PaymentProcessorEnum.DEFAULT,
      PaymentProcessorEnum.FALLBACK
    };

    for(int i = 0; i < 100; i++) {
      var payHandlerUseCase = new PayHandlerUseCase();
      PaymentProcessorEnum paymentProcessor = paymentProcessors[i % 2];

      payHandlerUseCase.getPayStats();
    }
  }
}
