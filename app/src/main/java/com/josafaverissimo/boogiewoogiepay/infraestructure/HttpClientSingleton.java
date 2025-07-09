package com.josafaverissimo.boogiewoogiepay.infraestructure;

import java.net.http.HttpClient;

public final class HttpClientSingleton {
  private HttpClientSingleton() {}

  public static final HttpClient HTTP = HttpClient.newHttpClient();
}
