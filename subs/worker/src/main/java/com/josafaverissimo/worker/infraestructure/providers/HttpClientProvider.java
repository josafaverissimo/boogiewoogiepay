package com.josafaverissimo.worker.infraestructure.providers;

import java.net.http.HttpClient;

public final class HttpClientProvider {
  private final static HttpClient httpClient = HttpClient.newHttpClient();

  private HttpClientProvider() {}

  public static HttpClient getInstance() {
    return httpClient;
  }
}
