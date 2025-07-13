package com.josafaverissimo.worker.listeners;

import com.josafaverissimo.shared.infraestructure.Valkey;
import com.josafaverissimo.shared.Json;
import com.josafaverissimo.worker.tasks.ProcessPaymentTask;
import com.josafaverissimo.worker.infraestructure.dtos.ProcessPaymentDto;
import com.josafaverissimo.shared.enums.ValkeyQueueEnum;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class ValkeyListener {
  private final static Logger logger = LoggerFactory.getLogger(ValkeyListener.class);

  public static void listen() {
    var queue = ValkeyQueueEnum.PROCESS_PAYMENT_QUEUE;

    Valkey.subscribeQueue(
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
            var processPaymentTask = new ProcessPaymentTask();

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
