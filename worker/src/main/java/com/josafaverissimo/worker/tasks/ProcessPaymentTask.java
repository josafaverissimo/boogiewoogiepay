package com.josafaverissimo.worker.tasks;

import com.josafaverissimo.worker.infraestructure.dtos.ProcessPaymentDto;
import com.josafaverissimo.worker.tasks.interfaces.TaskInterface;

public class ProcessPaymentTask implements TaskInterface<ProcessPaymentDto> {
  public void run(ProcessPaymentDto dto) {
    System.out.println(dto.correlationId());
    System.out.println(dto.amount());
  }
}
