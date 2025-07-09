package com.josafaverissimo.boogiewoogiepay.infraestructure.interfaces;

import io.javalin.Javalin;

public interface RouterInterface {
  public void register(Javalin app);
}
