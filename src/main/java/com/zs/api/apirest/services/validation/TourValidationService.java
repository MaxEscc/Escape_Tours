package com.zs.api.apirest.services.validation;

import com.zs.api.apirest.model.entity.ToursEntity;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TourValidationService {
  private static final BigDecimal PRECIO_MINIMO = BigDecimal.ZERO;
  private static final int DURACION_MINIMA = 0;
  private static final int DURACION_MAXIMA = 30;

  public void validarDatosTour(ToursEntity tour) {
    validarFechas(tour);
    validarPrecios(tour);
    validarDuracion(tour);
  }

  private void validarFechas(ToursEntity tour) {
    if (tour.getFechaSalida().before(Date.valueOf(LocalDate.now()))) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "La fecha de salida debe ser mayor o igual a la fecha actual");
    }
    if (tour.getFechaSalida().after(tour.getFechaRegreso())) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "La fecha de salida debe ser menor o igual a la de regreso");
    }
  }

  private void validarPrecios(ToursEntity tour) {
    if (tour.getPrecioBaseAdulto().compareTo(PRECIO_MINIMO) <= 0 ||
        tour.getPrecioBaseNino().compareTo(PRECIO_MINIMO) <= 0) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "El precio base de adulto y niño debe ser mayor a 0");
    }
  }

  private void validarDuracion(ToursEntity tour) {
    LocalDate fechaSalida = tour.getFechaSalida().toLocalDate();
    LocalDate fechaRegreso = tour.getFechaRegreso().toLocalDate();
    long diasDeDuracion = ChronoUnit.DAYS.between(fechaSalida, fechaRegreso);

    if (diasDeDuracion < DURACION_MINIMA || diasDeDuracion > DURACION_MAXIMA) {
      throw new IllegalArgumentException(
          "La duración del tour debe ser entre 1 y 30 días");
    }
  }
}
