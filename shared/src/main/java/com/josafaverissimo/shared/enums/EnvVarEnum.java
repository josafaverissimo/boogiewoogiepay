package com.josafaverissimo.shared.enums;

public enum EnvVarEnum {
  VALKEY_HOST("valkey"),
  VALKEY_PORT(6379);

  private String host;
  private int port;

  EnvVarEnum(String host) {
    this.host = host;
  }

  EnvVarEnum(int port) {
    this.port = port;
  }

  public String getHost() {
    return host;
  }

  public int getPort() {
    return port;
  }
}


