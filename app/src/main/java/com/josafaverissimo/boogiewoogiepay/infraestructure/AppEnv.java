package com.josafaverissimo.boogiewoogiepay.infraestructure;

import com.josafaverissimo.boogiewoogiepay.infraestructure.enums.EnvVarEnum;
import com.josafaverissimo.boogiewoogiepay.infraestructure.exceptions.EnviromentException;

import io.github.cdimascio.dotenv.Dotenv;

public final class AppEnv {
  private final static Dotenv dotenv;

  static {
    dotenv = Dotenv.configure().ignoreIfMissing().load();
  }

  private AppEnv() {
  }

  public static void checkEnv() throws EnviromentException {
    for (EnvVarEnum envVar : EnvVarEnum.values()) {
      if(dotenv.get(envVar.name()) != null) {
        continue;
      }

      throw new EnviromentException(
        String.format("Env var \"%s\" is missing", envVar.name())
      );
    }
  }

  public static String get(EnvVarEnum envVar) {
    return dotenv.get(envVar.name());
  }
}
