package com.josafaverissimo.worker.usecases;

import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.josafaverissimo.shared.BaseUrl;
import com.josafaverissimo.shared.Utils;
import com.josafaverissimo.worker.infraestructure.AppEnv;
import com.josafaverissimo.worker.infraestructure.enums.EnvVarEnum;
import com.josafaverissimo.worker.infraestructure.enums.PaymentProcessorEnum;
import com.josafaverissimo.worker.infraestructure.providers.HttpClientProvider;

public class ProcessPaymentUseCase {
  public final Logger logger = LoggerFactory.getLogger(ProcessPaymentUseCase.class);

  public boolean requestProcessPaymentByProcessor(
    PaymentProcessorEnum paymentProcessor,
    String json
  ) {
    var target = new BaseUrl(paymentProcessor.getUrl())
      .joinEndpoint("/payments");

    var request = HttpRequest.newBuilder(target)
      .POST(BodyPublishers.ofString(json))
      .header("Content-Type", "application/json")
      .build();

    HttpResponse<String> response = null;

    try {
      response = HttpClientProvider.getInstance()
        .send(request, BodyHandlers.ofString());

      logger.info(
        String.format(
          "Got code: %d, Got body: %s",
          response.statusCode(),
          response.body()
        )
      );

      if(Utils.isHttpStatusOk(response.statusCode())) {
        return true;
      }

      return false;

    } catch(Exception e) {
      logger.error(
        String.format(
          "Failed to request payment process. %s", e.toString()
        )
      );

      return false;
    }
  }
}
