package com.josafaverissimo.boogiewoogiepay.presentation.controllers;

import com.josafaverissimo.boogiewoogiepay.infraestructure.dtos.PaymentRequestBody;
import static com.josafaverissimo.boogiewoogiepay.infraestructure.validators.PaymentRequestBodyValidator.*;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.josafaverissimo.boogiewoogiepay.usecases.PayHandlerUseCase;
import com.josafaverissimo.shared.Json;
import com.josafaverissimo.shared.enums.ValkeyQueueEnum;
import com.josafaverissimo.shared.infraestructure.Valkey;

import io.javalin.http.Context;

public final class PayHandlerController {
  private final PayHandlerUseCase payHandlerUseCase;
  private final Logger logger = LoggerFactory.getLogger(PayHandlerController.class);

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

    String bodyJsonString = null;

    try {
      bodyJsonString = Json.stringify(body);

    } catch (IOException e) {
      context.status(400);

      return;
    }

    var queue = ValkeyQueueEnum.PROCESS_PAYMENT_QUEUE;
    var success = Valkey.getInstance().pushQueue(queue, bodyJsonString);

    if(!success) {
      context.status(503);

      return;
    }

    logger.info(
      String.format("Body data has been sent to queue %s", queue.getQueue())
    );
    logger.debug(String.format("Data sent: %s", bodyJsonString));

    context.status(204);
  }

  public void payStats(Context context) {
    var response = this.payHandlerUseCase.getPayStats();

    context.json(response);
  }
}
