package com.zs.api.apirest.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.sql.Date;
import lombok.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "tours", schema = "public", catalog = "escapetours_dev")
public class ToursEntity {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Schema(accessMode = Schema.AccessMode.READ_ONLY)
  @Column(name = "tour_id")
  private int tourId;

  @Basic
  @NotEmpty(message = "El nombre del tour es requerido")
  @Size(min = 3, max = 50,
        message = "El nombre del tour debe tener entre 3 y 50 caracteres")
  @Column(name = "nombre")
  private String nombre;

  @Basic
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Los_Angeles",
              locale = "es_MX")
  @NotNull(message = "La fecha de salida es requerida")
  @Column(name = "fecha_salida")
  private Date fechaSalida;

  @Basic
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Los_Angeles",
              locale = "es_MX")
  @NotNull(message = "La fecha de regreso es requerida")
  @Column(name = "fecha_regreso")
  private Date fechaRegreso;

  @Basic
  @NotNull(message = "El precio base de adulto es requerido")
  @DecimalMin(value = "0.0", inclusive = false,
              message = "El precio base de adulto debe ser mayor a 0")
  @Column(name = "precio_base_adulto")
  private BigDecimal precioBaseAdulto;

  @Basic
  @NotNull(message = "El precio base de niño es requerido")
  @DecimalMin(value = "0.0", inclusive = false,
              message = "El precio base de niño debe ser mayor a 0")
  @Column(name = "precio_base_nino")
  private BigDecimal precioBaseNino;

  @Transient
  @Schema(accessMode = Schema.AccessMode.READ_ONLY)
  private String estatus;

  // Setter
  public void setEstatus(String estatus) { this.estatus = estatus; }

}
