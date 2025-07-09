package com.josafaverissimo.boogiewoogiepay.infraestructure;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSingleton {
  public static final ObjectMapper MAPPER = new ObjectMapper();
}
