package com.josafaverissimo.boogiewoogiepay.usecases;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.josafaverissimo.boogiewoogiepay.infraestructure.AppEnv;
import com.josafaverissimo.boogiewoogiepay.infraestructure.HttpClientSingleton;
import com.josafaverissimo.boogiewoogiepay.infraestructure.JsonSingleton;
import com.josafaverissimo.boogiewoogiepay.infraestructure.dtos.PayStatsResponse;
import com.josafaverissimo.boogiewoogiepay.infraestructure.dtos.PaymentProcessorStats;
import com.josafaverissimo.boogiewoogiepay.infraestructure.enums.EnvVarEnum;
import com.josafaverissimo.boogiewoogiepay.infraestructure.enums.PaymentProcessorEnum;
import com.josafaverissimo.boogiewoogiepay.infraestructure.external.paymentprocessor.dtos.PaymentProcessorRequestBody;
import com.josafaverissimo.boogiewoogiepay.infraestructure.external.paymentprocessor.dtos.PaymentSummaryResponse;
import com.josafaverissimo.boogiewoogiepay.shared.BaseUrl;
import com.josafaverissimo.boogiewoogiepay.shared.Utils;

public final class PayHandlerUseCase {
  private final Logger logger = LoggerFactory.getLogger(PayHandlerUseCase.class);

  public PayStatsResponse getPayStats() {
    final var defaultTarget = PaymentProcessorEnum.DEFAULT;
    final var fallbackTarget = PaymentProcessorEnum.FALLBACK;

    PaymentProcessorStats defaultStats = null;
    final var defaultResponse = this.getPaymentProcessorStats(defaultTarget).orElse(null);

    PaymentProcessorStats fallbackStats = null;
    final var fallbackResponse = this.getPaymentProcessorStats(fallbackTarget).orElse(null);

    if(defaultResponse != null) {
      defaultStats = new PaymentProcessorStats(
        defaultResponse.totalRequests(),
        defaultResponse.totalAmount()
      );
    }

    if(fallbackResponse != null) {
      fallbackStats = new PaymentProcessorStats(
        fallbackResponse.totalRequests(),
        fallbackResponse.totalAmount()
      );
    }

    return new PayStatsResponse(defaultStats, fallbackStats);
  }

  private Optional<PaymentSummaryResponse> getPaymentProcessorStats(
    PaymentProcessorEnum paymentProcessor
  ) {
    var baseUrl = new BaseUrl(paymentProcessor.getUrl());
    var target = baseUrl.joinEndpoint("/admin/payments-summary");

    var request = HttpRequest.newBuilder(target)
        .GET()
        .header("X-Rinha-Token", AppEnv.get(EnvVarEnum.X_RINHA_TOKEN))
        .build();

    try {
      try {
        var response = HttpClientSingleton.HTTP.send(request, BodyHandlers.ofString());

        if(!Utils.isHttpStatusOk(response.statusCode())) {
          this.logger.error(
            String.format(
              "got bad status code (%d) in %s",
              response.statusCode(),
              response.uri()
            )
          );

          return Optional.empty();
        }

        var stats = JsonSingleton.MAPPER.readValue(response.body(), PaymentSummaryResponse.class);

        return Optional.of(stats);

      } catch (IOException e) {
        e.printStackTrace();

        return Optional.empty();
      }

    } catch (InterruptedException e) {
      return Optional.empty();

    }
  }

  public boolean processPayment(
    PaymentProcessorRequestBody body,
    PaymentProcessorEnum paymentProcessor
  ) {
    try {
      var baseUrl = new BaseUrl(paymentProcessor.getUrl());
      var target = baseUrl.joinEndpoint("/payments");
      var bodyJson = JsonSingleton.MAPPER.writeValueAsString(body);
      var request = HttpRequest.newBuilder()
        .POST(HttpRequest.BodyPublishers.ofString(bodyJson))
        .uri(target)
        .header("Content-Type", "application/json")
        .build();

      var response = HttpClientSingleton.HTTP.send(
        request,
        HttpResponse.BodyHandlers.ofString());

      return Utils.isHttpStatusOk(response.statusCode());

    } catch (InterruptedException e) {
      e.printStackTrace();

      return false;

    } catch (IOException e) {
      e.printStackTrace();

      return false;

    }
  }
}
