package com.josafaverissimo.boogiewoogiepay.infraestructure.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PayStatsResponse(
  @JsonProperty("default") PaymentProcessorStats _default,
  PaymentProcessorStats fallback
) {

}
