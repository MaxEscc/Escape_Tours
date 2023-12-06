package com.zs.api.apirest.services.impl;

import com.zs.api.apirest.model.entity.ToursEntity;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class TourStateService {

  public String calcularEstado(ToursEntity tour) {
    LocalDate currentDate = LocalDate.now();
    LocalDate fechaSalida = tour.getFechaSalida().toLocalDate();
    LocalDate fechaRegreso = tour.getFechaRegreso().toLocalDate();

    if (currentDate.isBefore(fechaSalida)) {
      return "Activo";
    } else if (currentDate.isEqual(fechaSalida) ||
               (currentDate.isAfter(fechaSalida) &&
                currentDate.isBefore(fechaRegreso))) {
      return "En curso";
    } else if (currentDate.isAfter(fechaRegreso)) {
      return "Finalizado";
    } else {
      return "No definido";
    }
  }
}
