package com.zs.api.apirest.model.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateReservaDTO {
  private String numeroContacto;
  private String email;
  private Integer numeroPersonas;
  private String tipoCliente;
  private BigDecimal abono;
}
