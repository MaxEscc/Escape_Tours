package com.zs.api.apirest.services.validation;

import com.zs.api.apirest.model.dto.NuevaReservaDTO;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


/**
 * Clase de utilidad para validar datos al crear una nueva reserva.
 */
@Service
public class ReservaValidationService {

  private static final Pattern EMAIL_PATTERN =
      Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

  private static final List<String> VALID_TIPO_CLIENTE =
      Arrays.asList("adulto", "nino");

  /**
   * Valida los datos de una nueva reserva.
   *
   * @param nuevaReservaDTO DTO con los datos de la nueva reserva.
   * @throws ResponseStatusException si los datos no son válidos.
   */
  public static void validarNuevaReserva(NuevaReservaDTO nuevaReservaDTO) {
    try {
      validarNumeroPersonas(nuevaReservaDTO.getNumeroPersonas());
      validarTipoCliente(nuevaReservaDTO.getTipoCliente());
      validarAbono(nuevaReservaDTO.getAbono());
      validarNumeroContacto(nuevaReservaDTO.getNumeroContacto());
      validarEmail(nuevaReservaDTO.getEmail());
      // Agrega más validaciones según sea necesario
    } catch (IllegalArgumentException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(),
                                        e);
    }
  }

  /**
   * Valida el numero de contacto
   *
   * @param numeroContacto Número de contacto a validar.
   * @throws IllegalArgumentException si el número de contacto no es válido.
   */
  private static void validarNumeroContacto(String numeroContacto) {
    if (numeroContacto == null || !numeroContacto.matches("\\d{10}")) {
      throw new IllegalArgumentException(
          "El número de contacto debe tener exactamente 10 dígitos");
    }
  }

  /**
   * Valida que el número de personas sea válido.
   *
   * @param numeroPersonas Número de personas en la reserva.
   * @throws IllegalArgumentException si el número de personas no es válido.
   */
  private static void validarNumeroPersonas(Integer numeroPersonas) {
    if (numeroPersonas == null || numeroPersonas <= 0 || numeroPersonas > 100) {
      throw new IllegalArgumentException(
          "Número de personas debe estar entre 1 y 100");
    }
  }

  /**
   * Valida que el tipo de cliente sea válido.
   *
   * @param tipoCliente Tipo de cliente en la reserva.
   * @throws IllegalArgumentException si el tipo de cliente no es válido.
   */
  private static void validarTipoCliente(String tipoCliente) {
    if (!VALID_TIPO_CLIENTE.contains(tipoCliente.toLowerCase())) {
      throw new IllegalArgumentException(
          "Tipo de cliente debe ser 'adulto' o 'nino'");
    }
  }

  /**
   * Valida que el abono sea válido.
   *
   * @param abono Abono en la reserva.
   * @throws IllegalArgumentException si el abono no es válido.
   */
  private static void validarAbono(BigDecimal abono) {
    if (abono == null || abono.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException(
          "El abono debe ser mayor o igual a cero");
    }
  }

  /**
   * Valida el formato del correo electrónico.
   *
   * @param email Correo electrónico a validar.
   * @throws IllegalArgumentException si el correo electrónico no es válido.
   */
  private static void validarEmail(String email) {
    if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
      throw new IllegalArgumentException("El correo electrónico no es válido");
    }
  }
}
