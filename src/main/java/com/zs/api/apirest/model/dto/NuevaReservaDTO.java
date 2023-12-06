package com.zs.api.apirest.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NuevaReservaDTO {
  @NotNull private Integer tourId;
  @NotEmpty private String nombreCliente;
  @NotEmpty private String ciudadResidencia;
  @NotEmpty private String numeroContacto;
  @Email(regexp = ".+@.+\\..+", message = "Email debe ser válido")
  @NotEmpty
  private String email;
  @NotNull private Integer numeroPersonas;
  @NotEmpty private String tipoCliente;
  private BigDecimal abono;
}
