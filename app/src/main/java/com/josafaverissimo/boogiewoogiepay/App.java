package com.josafaverissimo.boogiewoogiepay;

import com.josafaverissimo.boogiewoogiepay.presentation.Router;

import io.javalin.Javalin;

public class App {
  public static void main(String[] args) {
    var app = Javalin.create().start(8080);
    var router = new Router();

    router.register(app);
  }
}
