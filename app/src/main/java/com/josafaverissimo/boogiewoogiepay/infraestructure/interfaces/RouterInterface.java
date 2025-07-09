package com.josafaverissimo.boogiewoogiepay.infraestructure.interfaces;

import io.javalin.config.JavalinConfig;

public interface RouterInterface {
  public void register(JavalinConfig app);
}
