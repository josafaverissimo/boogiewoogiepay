package com.josafaverissimo.boogiewoogiepay.presentation.routers;

import com.josafaverissimo.boogiewoogiepay.presentation.controllers.PayHandlerController;
import com.josafaverissimo.boogiewoogiepay.infraestructure.interfaces.RouterInterface;

import io.javalin.config.JavalinConfig;
import static io.javalin.apibuilder.ApiBuilder.*;

public class PayHandlerRouter implements RouterInterface {
  private PayHandlerController controller;

  public PayHandlerRouter(PayHandlerController controller) {
    this.controller = controller;
  }

  public void register(JavalinConfig config) {
    config.router.apiBuilder(() -> {
      path("payments", () -> {
        post(this.controller::doPay);
      });
    });
  }
}
