package com.josafaverissimo.shared;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public final class Json {
  private final static ObjectMapper mapper = new ObjectMapper();

  private Json() {}

  public static <T> T read(String json, Class<T> target) throws IOException {
    return mapper.readValue(json, target);
  }

  public static <T> String stringify(T target) throws IOException {
    return mapper.writeValueAsString(target);
  }
}

