package com.josafaverissimo.boogiewoogiepay.presentation.controllers;

import com.josafaverissimo.boogiewoogiepay.infraestructure.dtos.PaymentRequestBody;
import com.josafaverissimo.boogiewoogiepay.infraestructure.external.paymentprocessor.dtos.PaymentProcessorRequestBody;
import com.josafaverissimo.boogiewoogiepay.shared.Utils;
import com.josafaverissimo.boogiewoogiepay.usecases.external.paymentprocessor.PaymentProcessorUseCase;

import io.javalin.http.Context;

public final class PayHandlerController {
  private PaymentProcessorUseCase paymentProcessorUseCase;

  public PayHandlerController(PaymentProcessorUseCase paymentProcessorUseCase) {
    this.paymentProcessorUseCase = paymentProcessorUseCase;
  }
  
  public void doPay(Context context) {
    var body = context.bodyValidator(PaymentRequestBody.class)
      .check(obj -> !Utils.isStrEmpty(obj.correlationId()), "correlationId must be no empty")
      .check(obj -> obj.amount() > 0, "amount must be greater than 0")
      .get();

    var paymentProcessorBody = new PaymentProcessorRequestBody(
      body.correlationId(),
      body.amount(),
      Utils.nowIsoFormat()
    );

    this.paymentProcessorUseCase.processPayment(paymentProcessorBody);

    context.json(paymentProcessorBody);
  }

  public void payStats(Context context) {
    // TODO: implements payment processors stats
    context.result("Hello world");
  }
}
