package com.josafaverissimo.boogiewoogiepay.presentation.controllers;

import com.josafaverissimo.boogiewoogiepay.infraestructure.dtos.PaymentRequestBody;
import com.josafaverissimo.boogiewoogiepay.infraestructure.external.paymentprocessor.dtos.PaymentProcessorRequestBody;
import com.josafaverissimo.boogiewoogiepay.shared.Utils;
import com.josafaverissimo.boogiewoogiepay.usecases.PayHandlerUseCase;

import io.javalin.http.Context;

public final class PayHandlerController {
  private PayHandlerUseCase payHandlerUseCase;

  public PayHandlerController(
    PayHandlerUseCase payHandlerUseCase
  ) {
    this.payHandlerUseCase = payHandlerUseCase;
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

    this.payHandlerUseCase.processPayment(paymentProcessorBody);

    context.json(paymentProcessorBody);
  }

  public void payStats(Context context) {
    var response = this.payHandlerUseCase.getPayStats();

    context.json(response);
  }
}
