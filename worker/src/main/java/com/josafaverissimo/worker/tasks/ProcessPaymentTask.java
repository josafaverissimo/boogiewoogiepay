package com.josafaverissimo.worker.tasks;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.josafaverissimo.shared.Json;
import com.josafaverissimo.shared.Utils;
import com.josafaverissimo.worker.infraestructure.dtos.PaymentDataToSendDto;
import com.josafaverissimo.worker.infraestructure.dtos.ProcessPaymentDto;
import com.josafaverissimo.worker.tasks.interfaces.TaskInterface;

public class ProcessPaymentTask implements TaskInterface<ProcessPaymentDto> {
  private final Logger logger = LoggerFactory.getLogger(ProcessPaymentTask.class);

  public void run(ProcessPaymentDto dto) {
    var requestedAt = Utils.nowIsoFormat();
    String payloadJsonString = null;

    try {
      payloadJsonString = Json.stringify(
        new PaymentDataToSendDto(
          dto.correlationId(),
          dto.amount(),
          requestedAt
        )
      );

    } catch(IOException e) {
      logger.error(
        String.format(
          "Failed to encode PaymentDataToSendDto: %s, %f, %s",
          dto.correlationId(),
          dto.amount(),
          requestedAt
        )
      );
    }

    System.out.println(payloadJsonString);
  }
}
