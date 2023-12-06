package com.zs.api.apirest.exceptions;

/**
 * Exception thrown when a requested record is not found.
 *
 * This exception is thrown when a specific record is not found in the database
 * or another data source. It can be used to handle scenarios where a requested
 * record is expected to exist but cannot be located.
 */
public class RegistroNoEncontradoException extends RuntimeException {

  /**
   * Constructs a RegistroNoEncontradoException with the specified detail
   * message.
   *
   * @param mensaje The detail message indicating the reason for the exception.
   */
  public RegistroNoEncontradoException(String mensaje) { super(mensaje); }

}
