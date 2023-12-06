package com.zs.api.apirest.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "precios", schema = "public", catalog = "escapetours_dev")
public class PreciosEntity {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "precio_id")
  private int precioId;

  @OneToOne
  @NotNull(message = "El ID del tour es requerido")
  @JoinColumn(name = "tour_id", referencedColumnName = "tour_id")
  private ToursEntity tour;

  @Basic @Column(name = "tipo_cliente") private String tipoCliente;

  @Basic @Column(name = "precio") private BigDecimal precio;
}
