package com.josafaverissimo.boogiewoogiepay.infraestructure.validators;

import com.josafaverissimo.boogiewoogiepay.shared.Utils;

public class PaymentRequestBodyValidator {
  public final static String CORRELATION_ID_ERROR = "correlationId must be no empty";
  public final static String AMOUNT_ERROR = "amount must be greater than 0";

  public static boolean validateCorrelationId(String correlationId) {
    return !Utils.isStrEmpty(correlationId);
  }

  public static boolean validateAmount(double amount) {
    return amount > 0;
  }
}
