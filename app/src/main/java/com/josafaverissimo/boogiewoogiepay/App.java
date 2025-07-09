package com.josafaverissimo.boogiewoogiepay;

import com.josafaverissimo.boogiewoogiepay.infraestructure.AppEnv;
import com.josafaverissimo.boogiewoogiepay.infraestructure.exceptions.InfraestructureException;
import com.josafaverissimo.boogiewoogiepay.presentation.Router;

import io.javalin.Javalin;

public class App {
  public static void main(String[] args) {
    try {
      AppEnv.checkEnv();

    } catch (InfraestructureException exception) {
      exception.printStackTrace();

      return;
    }

    var app = Javalin.create().start(8080);
    var router = new Router();

    router.register(app);
  }
}
