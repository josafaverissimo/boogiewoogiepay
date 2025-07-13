package com.josafaverissimo.shared.infraestructure;

import java.util.Optional;

import com.josafaverissimo.shared.infraestructure.exceptions.EnvironmentException;

import io.github.cdimascio.dotenv.Dotenv;

public final class AppEnv {
  private final static Dotenv dotenv;

  static {
    dotenv = Dotenv.configure().ignoreIfMissing().load();
  }

  private AppEnv() {
  }

  public static <T extends Enum<T>> void checkEnv(
    Class<T> enumClass
  ) throws EnvironmentException {
    for (T envVar : enumClass.getEnumConstants()) {
      if(dotenv.get(envVar.name()) != null) {
        continue;
      }

      throw new EnvironmentException(
        String.format("Env var \"%s\" is missing", envVar.name())
      );
    }
  }

  public static Optional<String> get(Enum<?> envVar) {
    return Optional.ofNullable(dotenv.get(envVar.name()));
  }
}
