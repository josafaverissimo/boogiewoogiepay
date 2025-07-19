package com.josafaverissimo.worker.infraestructure;

import com.josafaverissimo.worker.infraestructure.enums.EnvVarEnum;
import com.josafaverissimo.shared.infraestructure.exceptions.EnvironmentException;

import io.github.cdimascio.dotenv.Dotenv;

public final class AppEnv {
  private final static Dotenv dotenv;

  static {
    dotenv = Dotenv.configure().ignoreIfMissing().load();
  }

  private AppEnv() {
  }

  public static void checkEnv() throws EnvironmentException {
    for (EnvVarEnum envVar : EnvVarEnum.values()) {
      if(dotenv.get(envVar.name()) != null) {
        continue;
      }

      throw new EnvironmentException(
        String.format("Env var \"%s\" is missing", envVar.name())
      );
    }
  }

  public static String get(EnvVarEnum envVar) {
    return dotenv.get(envVar.name());
  }
}

