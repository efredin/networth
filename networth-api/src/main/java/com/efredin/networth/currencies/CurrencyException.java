package com.efredin.networth.currencies;

public class CurrencyException extends Exception {
  private static final long serialVersionUID = 1L;

  public CurrencyException(String message) {
    super(message);
  }

  public CurrencyException(String message, Exception e) {
    super(message, e);
  }
}
