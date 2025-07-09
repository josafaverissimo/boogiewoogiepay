package com.josafaverissimo.boogiewoogiepay.presentation;

import com.josafaverissimo.boogiewoogiepay.infraestructure.interfaces.RouterInterface;
import com.josafaverissimo.boogiewoogiepay.presentation.controllers.PayHandlerController;
import com.josafaverissimo.boogiewoogiepay.presentation.routers.PayHandlerRouter;

import io.javalin.config.JavalinConfig;

public class Router implements RouterInterface {
  private PayHandlerRouter payHandlerRouter;
  
  public Router() {
    this.payHandlerRouter = new PayHandlerRouter(
      new PayHandlerController()
    );

  }

  public void register(JavalinConfig config) {
    this.payHandlerRouter.register(config);
  }
}
