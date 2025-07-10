package com.josafaverissimo.boogiewoogiepay.infraestructure.enums;

import com.josafaverissimo.boogiewoogiepay.infraestructure.AppEnv;

public enum PaymentProcessorEnum {
  DEFAULT(AppEnv.get(EnvVarEnum.PAYMENT_PROCESSOR_URL_DEFAULT)),
  FALLBACK(AppEnv.get(EnvVarEnum.PAYMENT_PROCESSOR_URL_FALLBACK));

  private final String url;

  PaymentProcessorEnum(String url) {
    this.url = url;
  }

  public String getUrl() {
    return this.url;
  }
}
