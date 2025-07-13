package com.josafaverissimo.worker;

import com.josafaverissimo.worker.listeners.ValkeyListener;

public class Worker {
  public static void main(String[] args) {
    while(true) {
      ValkeyListener.listen();
    }
  }
}
