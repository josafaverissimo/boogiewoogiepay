package com.josafaverissimo.boogiewoogiepay;

import com.josafaverissimo.boogiewoogiepay.presentation.Router;

import io.github.cdimascio.dotenv.Dotenv;
import io.javalin.Javalin;

public class App {
  public static void main(String[] args) {
    var dotenv = Dotenv.configure().ignoreIfMissing().load();

    var app = Javalin.create().start(8080);
    var router = new Router();

    System.out.println(dotenv.get("PAYMENT_PROCESSOR_URL_DEFAULT"));

    router.register(app);
  }
}
