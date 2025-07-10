package com.josafaverissimo.boogiewoogiepay.infraestructure.external.paymentprocessor.dtos;

public record PaymentSummaryResponse(
  int totalAmount,
  int totalRequests,
  double totalFee,
  float feePerTransaction
) {

}
