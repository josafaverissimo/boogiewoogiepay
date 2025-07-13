package com.josafaverissimo.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.josafaverissimo.shared.infraestructure.exceptions.InfraestructureException;
import com.josafaverissimo.worker.infraestructure.AppEnv;
import com.josafaverissimo.worker.listeners.ValkeyListener;

import io.valkey.exceptions.JedisConnectionException;

public class Worker {
  private final static Logger logger = LoggerFactory.getLogger(Worker.class);

  public static void main(String[] args) {
    try {
      AppEnv.checkEnv();

    } catch (InfraestructureException exception) {
      exception.printStackTrace();

      return;
    }

    while(true) {
      if(!ValkeyListener.listen()) {
        break;
      };
    }
  }
}
