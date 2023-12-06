package com.zs.api.apirest.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import lombok.*;


@Entity
@Data
@Table(name = "reservas", schema = "public", catalog = "escapetours_dev")
public class ReservasEntity {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "reserva_id")
  private int reservaId;

  @ManyToOne
  @NotNull(message = "El ID del tour es requerido")
  @JoinColumn(name = "tour_id", referencedColumnName = "tour_id")
  private ToursEntity tour;

  @Basic
  @NotEmpty(message = "El nombre del cliente es requerido")
  @Column(name = "nombre_cliente")
  private String nombreCliente;

  @Basic
  @NotEmpty(message = "La ciudad de residencia es requerida")
  @Column(name = "ciudad_residencia")
  private String ciudadResidencia;

  @Basic
  @NotEmpty(message = "El número de contacto es requerido")
  @Column(name = "numero_contacto")
  private String numeroContacto;

  @Basic
  @NotEmpty
  @Email(regexp = ".+@.+\\..+", message = "El email no es válido")
  @Column(name = "email")
  private String email;

  @Basic
  @NotNull(message = "El número de personas es requerido")
  @Column(name = "numero_personas")
  private Integer numeroPersonas;

  @Basic
  @NotEmpty(message = "El tipo de cliente es requerido")
  @Column(name = "tipo_cliente")
  private String tipoCliente;

  @Basic @Column(name = "fecha_reserva") private Timestamp fechaReserva;

  @Basic
  @Column(name = "total_pagado")
  private BigDecimal totalPagado = BigDecimal.ZERO;

  @Basic
  @Column(name = "saldo_pendiente")
  private BigDecimal saldoPendiente = BigDecimal.ZERO;

  @Transient
  @Schema(accessMode = Schema.AccessMode.READ_ONLY)
  private BigDecimal abono = BigDecimal.ZERO;

  // Setter
  public void setAbono(BigDecimal abono) { this.abono = abono; }

}
