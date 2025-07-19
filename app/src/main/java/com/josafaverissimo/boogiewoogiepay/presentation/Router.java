package com.josafaverissimo.boogiewoogiepay.presentation;

import com.josafaverissimo.boogiewoogiepay.infraestructure.interfaces.RouterInterface;
import com.josafaverissimo.boogiewoogiepay.presentation.controllers.PayHandlerController;
import com.josafaverissimo.boogiewoogiepay.presentation.routers.PayHandlerRouter;
import com.josafaverissimo.boogiewoogiepay.usecases.PayHandlerUseCase;

import io.javalin.config.JavalinConfig;

public class Router implements RouterInterface {
  private PayHandlerUseCase payHandlerUseCase;
  private PayHandlerRouter payHandlerRouter;

  public Router() {
    this.payHandlerUseCase = new PayHandlerUseCase();
    this.payHandlerRouter = new PayHandlerRouter(
      new PayHandlerController(
        this.payHandlerUseCase
      )
    );

  }

  public void register(JavalinConfig config) {
    this.payHandlerRouter.register(config);
  }
}
