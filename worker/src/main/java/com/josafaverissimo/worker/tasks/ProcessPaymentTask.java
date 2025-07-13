package com.josafaverissimo.worker.tasks;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.josafaverissimo.shared.Json;
import com.josafaverissimo.shared.Utils;
import com.josafaverissimo.worker.infraestructure.dtos.PaymentDataToSendDto;
import com.josafaverissimo.worker.infraestructure.dtos.ProcessPaymentDto;
import com.josafaverissimo.worker.infraestructure.enums.PaymentProcessorEnum;
import com.josafaverissimo.worker.tasks.interfaces.TaskInterface;
import com.josafaverissimo.worker.usecases.ProcessPaymentUseCase;

public class ProcessPaymentTask implements TaskInterface<ProcessPaymentDto> {
  private final Logger logger = LoggerFactory.getLogger(ProcessPaymentTask.class);
  private final ProcessPaymentUseCase processPaymentUseCase;

  public ProcessPaymentTask(ProcessPaymentUseCase processPaymentUseCase) {
    this.processPaymentUseCase = processPaymentUseCase;
  }

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

    var success = this.processPaymentUseCase.requestProcessPaymentByProcessor(
      PaymentProcessorEnum.DEFAULT,
      payloadJsonString
    );

    if(!success) {
      logger.error(
        String.format(
          "Failed to process payment on %s",
          PaymentProcessorEnum.DEFAULT.getUrl()
        )
      );

      success = this.processPaymentUseCase.requestProcessPaymentByProcessor(
        PaymentProcessorEnum.FALLBACK,
        payloadJsonString
      );

      if(!success) {
        logger.error(
          String.format(
            "Failed to process payment on %s",
            PaymentProcessorEnum.FALLBACK.getUrl()
          )
        );
      }
    }
  }
}
