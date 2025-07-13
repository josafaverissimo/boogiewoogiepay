package com.josafaverissimo.shared.enums;

public enum ValkeyQueueEnum {
  PROCESS_PAYMENT_QUEUE("process-payment-queue");

  private String queue;

  ValkeyQueueEnum(String queue) {
    this.queue = queue;
  }

  public String getQueue() {
    return this.queue;
  }
}

