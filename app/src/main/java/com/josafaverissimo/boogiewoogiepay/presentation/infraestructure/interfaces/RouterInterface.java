package com.josafaverissimo.boogiewoogiepay.presentation.infraestructure.interfaces;

import io.javalin.Javalin;

public interface RouterInterface {
  public void register(Javalin app);
}
