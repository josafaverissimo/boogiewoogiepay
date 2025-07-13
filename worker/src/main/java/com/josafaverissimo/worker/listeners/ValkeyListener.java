package com.josafaverissimo.worker.listeners;

import com.josafaverissimo.shared.infraestructure.Valkey;
import com.josafaverissimo.shared.Json;
import com.josafaverissimo.worker.tasks.ProcessPaymentTask;
import com.josafaverissimo.worker.usecases.ProcessPaymentUseCase;
import com.josafaverissimo.worker.infraestructure.dtos.ProcessPaymentDto;
import com.josafaverissimo.shared.enums.ValkeyQueueEnum;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class ValkeyListener {
  private final static Logger logger = LoggerFactory.getLogger(ValkeyListener.class);
  private final static ProcessPaymentUseCase processPaymentUseCase;
  private final static ProcessPaymentTask processPaymentTask;

  static {
    processPaymentUseCase = new ProcessPaymentUseCase();
    processPaymentTask = new ProcessPaymentTask(processPaymentUseCase);
  }

  public static boolean listen() {
    var queue = ValkeyQueueEnum.PROCESS_PAYMENT_QUEUE;

    return Valkey.getInstance().subscribeQueue(
      queue,
      data -> {
        if(data.isEmpty()) {
          logger.warn(
            String.format("Got empty string from queue: %s", queue.getQueue())
          );

          return;
        }

        data.ifPresent(json -> {
          try {
            var processPaymentDto = Json.read(json, ProcessPaymentDto.class);

            processPaymentTask.run(processPaymentDto);

          } catch(IOException e) {
            logger.error(
              String.format(
                "Failed to parse json of queue %s: %s",
                queue.getQueue(),
                json
              )
            );
          }
        });
      }
    );
  }
}
