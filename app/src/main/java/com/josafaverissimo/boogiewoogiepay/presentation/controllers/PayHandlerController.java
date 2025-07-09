package com.josafaverissimo.boogiewoogiepay.presentation.controllers;

import com.josafaverissimo.boogiewoogiepay.infraestructure.dtos.PaymentRequestBody;
import com.josafaverissimo.boogiewoogiepay.shared.Utils;

import io.javalin.http.Context;

public class PayHandlerController {
  public void doPay(Context context) {
    var body = context.bodyValidator(PaymentRequestBody.class)
      .check(obj -> !Utils.isStrEmpty(obj.correlationId()), "correlationId must be no empty")
      .check(obj -> obj.amount() > 0, "amount must be greater than 0")
      .get();

    context.result(String.valueOf(body.amount()));
  }

  public void payStats(Context context) {
    // TODO: implements payment processors stats
    context.result("Hello world");
  }
}
