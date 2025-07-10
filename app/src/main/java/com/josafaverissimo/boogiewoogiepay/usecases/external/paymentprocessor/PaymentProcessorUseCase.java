package com.josafaverissimo.boogiewoogiepay.usecases.external.paymentprocessor;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.josafaverissimo.boogiewoogiepay.infraestructure.JsonSingleton;
import com.josafaverissimo.boogiewoogiepay.infraestructure.enums.EnvVarEnum;
import com.josafaverissimo.boogiewoogiepay.infraestructure.AppEnv;
import com.josafaverissimo.boogiewoogiepay.infraestructure.HttpClientSingleton;
import com.josafaverissimo.boogiewoogiepay.infraestructure.external.paymentprocessor.dtos.PaymentProcessorRequestBody;

public class PaymentProcessorUseCase {
  public void processPayment(PaymentProcessorRequestBody body) {
    try {
      var baseUrl = AppEnv.get(EnvVarEnum.PAYMENT_PROCESSOR_URL_DEFAULT);
      var target = String.format("%s/payments", baseUrl);
      var bodyJson = JsonSingleton.MAPPER.writeValueAsString(body);
      var request = HttpRequest.newBuilder()
        .POST(HttpRequest.BodyPublishers.ofString(bodyJson))
        .uri(URI.create(target))
        .header("Content-Type", "application/json")
        .build();

      HttpResponse<String> response = HttpClientSingleton.HTTP.send(
        request,
        HttpResponse.BodyHandlers.ofString()
      );

      System.out.println(response.statusCode());
      System.out.println(response.body());

    } catch(JsonProcessingException e) {
      e.printStackTrace();

      return;
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}
