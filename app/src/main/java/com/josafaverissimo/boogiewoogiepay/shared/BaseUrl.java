package com.josafaverissimo.boogiewoogiepay.shared;

import java.net.URI;

public final class BaseUrl {
  private final URI baseUri;

  public BaseUrl(String baseUrl) {
    this.baseUri = URI.create(this.removeExtraSlashes(baseUrl));
  }

  public URI joinEndpoint(String endpoint) {
    String endpointWitoutExtraSlashes = this.removeExtraSlashes(endpoint);

    return this.baseUri.resolve(endpointWitoutExtraSlashes);
  }

  private String removeExtraSlashes(String url) {
    return url.replaceAll("(?<!:)/{2,}", "/");
  }
}
