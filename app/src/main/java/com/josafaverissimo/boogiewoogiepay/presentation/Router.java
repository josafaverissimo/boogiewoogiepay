package com.josafaverissimo.boogiewoogiepay.presentation;

import com.josafaverissimo.boogiewoogiepay.infraestructure.interfaces.RouterInterface;
import com.josafaverissimo.boogiewoogiepay.presentation.controllers.PayHandlerController;
import com.josafaverissimo.boogiewoogiepay.presentation.routers.PayHandlerRouter;
import com.josafaverissimo.boogiewoogiepay.usecases.external.paymentprocessor.PaymentProcessorUseCase;

import io.javalin.config.JavalinConfig;

public class Router implements RouterInterface {
  private PaymentProcessorUseCase paymentProcessorUseCase;
  private PayHandlerRouter payHandlerRouter;
  
  public Router() {
    this.paymentProcessorUseCase = new PaymentProcessorUseCase();
    this.payHandlerRouter = new PayHandlerRouter(
      new PayHandlerController(this.paymentProcessorUseCase)
    );

  }

  public void register(JavalinConfig config) {
    this.payHandlerRouter.register(config);
  }
}
