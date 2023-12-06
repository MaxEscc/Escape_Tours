package com.zs.api.apirest.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateTourRequestDTO {

  @Size(min = 3, max = 50,
        message = "El nombre del tour debe tener entre 3 y 50 caracteres")
  private String nombre;

  @JsonFormat(pattern = "yyyy-MM-dd") private Date fechaSalida;

  @JsonFormat(pattern = "yyyy-MM-dd") private Date fechaRegreso;

  private BigDecimal precioBaseAdulto;

  private BigDecimal precioBaseNino;

}
