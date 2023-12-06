package com.zs.api.apirest.exceptions;

public class ReservaNotFoundException extends RuntimeException {

  /**
   * Constructs a ReservaNotFoundException with the specified detail message.
   *
   * @param message The detail message indicating the reason for the exception.
   */
  public ReservaNotFoundException(String message) { super(message); }
}
