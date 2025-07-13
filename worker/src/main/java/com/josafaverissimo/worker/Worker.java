package com.josafaverissimo.worker;

import com.josafaverissimo.shared.infraestructure.exceptions.InfraestructureException;
import com.josafaverissimo.worker.infraestructure.AppEnv;
import com.josafaverissimo.worker.listeners.ValkeyListener;

public class Worker {
  public static void main(String[] args) {
    try {
      AppEnv.checkEnv();

    } catch (InfraestructureException exception) {
      exception.printStackTrace();

      return;
    }

    while(true) {
      ValkeyListener.listen();
    }
  }
}
