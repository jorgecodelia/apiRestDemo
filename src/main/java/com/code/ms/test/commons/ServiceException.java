package com.code.ms.test.commons;

import lombok.Generated;

public class ServiceException extends RuntimeException {
  private final String code;

  public ServiceException(String code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  @Generated
  public String getCode() {
    return this.code;
  }
}
