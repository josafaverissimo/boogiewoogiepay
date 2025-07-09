package com.josafaverissimo.boogiewoogiepay.presentation.routers;

import com.josafaverissimo.boogiewoogiepay.presentation.controllers.PayHandlerController;
import com.josafaverissimo.boogiewoogiepay.infraestructure.interfaces.RouterInterface;

import io.javalin.Javalin;

public class PayHandlerRouter implements RouterInterface {
  private PayHandlerController controller;

  public PayHandlerRouter(PayHandlerController controller) {
    this.controller = controller;

  }

  public void register(Javalin app) {
    app.get("/payments", ctx -> ctx.result(this.controller.doPay()));
  }
}
