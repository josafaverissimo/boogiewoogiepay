package com.josafaverissimo.boogiewoogiepay.presentation.controllers;

import com.josafaverissimo.boogiewoogiepay.infraestructure.dtos.PaymentRequestBody;
import com.josafaverissimo.boogiewoogiepay.infraestructure.enums.PaymentProcessorEnum;
import com.josafaverissimo.boogiewoogiepay.infraestructure.external.paymentprocessor.dtos.PaymentProcessorRequestBody;
import static com.josafaverissimo.boogiewoogiepay.infraestructure.validators.PaymentRequestBodyValidator.*;
import com.josafaverissimo.boogiewoogiepay.shared.Utils;
import com.josafaverissimo.boogiewoogiepay.usecases.PayHandlerUseCase;

import io.javalin.http.Context;

public final class PayHandlerController {
  private PayHandlerUseCase payHandlerUseCase;

  public PayHandlerController(
      PayHandlerUseCase payHandlerUseCase) {
    this.payHandlerUseCase = payHandlerUseCase;
  }

  public void doPay(Context context) {
    var body = context.bodyValidator(PaymentRequestBody.class)
    .check(
      obj -> validateCorrelationId(obj.correlationId()),
      CORRELATION_ID_ERROR
    )
    .check(
      obj -> validateAmount(obj.amount()),
      AMOUNT_ERROR
    )
    .get();

    var paymentProcessorBody = new PaymentProcessorRequestBody(
      body.correlationId(),
      body.amount(),
      Utils.nowIsoFormat()
    );

    var successOnPay = this.payHandlerUseCase.processPayment(
      paymentProcessorBody,
      PaymentProcessorEnum.DEFAULT
    );

    if(!successOnPay) {
      successOnPay = this.payHandlerUseCase.processPayment(
        paymentProcessorBody,
        PaymentProcessorEnum.FALLBACK
      );
    }

    if(!successOnPay) {
      context.status(503);
    }

    context.status(204);
  }

  public void payStats(Context context) {
    var response = this.payHandlerUseCase.getPayStats();

    context.json(response);
  }
}
