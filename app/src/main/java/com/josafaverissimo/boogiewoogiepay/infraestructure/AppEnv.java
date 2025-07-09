package com.josafaverissimo.boogiewoogiepay.infraestructure;

import com.josafaverissimo.boogiewoogiepay.infraestructure.exceptions.EnviromentException;

import io.github.cdimascio.dotenv.Dotenv;

public final class AppEnv {
  private final static Dotenv dotenv;
  private final static String[] requiredEnvVars;

  static {
    dotenv = Dotenv.configure().ignoreIfMissing().load();
    requiredEnvVars = new String[]{
      "PAYMENT_PROCESSOR_URL_DEFAULT",
      "PAYMENT_PROCESSOR_URL_FALLBACK"
    };
  }

  private AppEnv() {
  }

  public static void checkEnv() throws EnviromentException {
    var dotenv = AppEnv.getDotenv();

    for (String envVar : AppEnv.requiredEnvVars) {
      if(dotenv.get(envVar) != null) {
        continue;
      }

      throw new EnviromentException(
        String.format("Env var \"%s\" is missing", envVar)
      );
    }
  }

  public static Dotenv getDotenv() {
    return AppEnv.dotenv;
  }
}
