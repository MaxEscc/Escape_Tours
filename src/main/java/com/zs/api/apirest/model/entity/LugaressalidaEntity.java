package com.zs.api.apirest.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lugaressalida", schema = "public", catalog = "escapetours_dev")
public class LugaressalidaEntity {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "lugar_id")
  private int lugarId;

  @ManyToOne
  @NotNull(message = "El ID del tour es requerido")
  @JoinColumn(name = "tour_id", referencedColumnName = "tour_id")
  private ToursEntity tour;

  @Basic @Column(name = "ciudad") private String ciudad;
}
