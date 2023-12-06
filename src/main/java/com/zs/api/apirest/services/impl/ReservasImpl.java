package com.zs.api.apirest.services.impl;

import com.zs.api.apirest.model.dao.ReservasDao;
import com.zs.api.apirest.model.dao.TourDao;
import com.zs.api.apirest.model.dto.NuevaReservaDTO;
import com.zs.api.apirest.model.dto.UpdateReservaDTO;
import com.zs.api.apirest.model.entity.ReservasEntity;
import com.zs.api.apirest.model.entity.ToursEntity;
import com.zs.api.apirest.model.mapper.ReservasMapper;
import com.zs.api.apirest.services.IReservaServices;
import com.zs.api.apirest.services.validation.ReservaValidationService;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ReservasImpl implements IReservaServices {

  private final ReservasDao reservasDao;
  private final ReservasMapper reservasMapper;
  ReservaValidationService reservaValidationService;
  private final TourDao tourDao;

  public ReservasImpl(ReservasDao reservasDao, ReservasMapper reservasMapper,
                      ReservaValidationService reservaValidationService,
                      TourDao tourDao) {
    this.reservasDao = reservasDao;
    this.reservasMapper = reservasMapper;
    this.reservaValidationService = reservaValidationService;
    this.tourDao = tourDao;
  }

  /**
   * @param id Integer
   * @return a ReservasEntity
   * @Author zusldev
   * @Description get a reserva from database
   * @Date 2023/08/14
   */
  @Transactional(readOnly = true)
  @Override
  public ReservasEntity findById(int id) {
    // Validate if the reserva exists
    return reservasDao.findById(id).orElseThrow(
        ()
            -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                String.format("Reserva with id %d not found", id)));
  }

  /**
   * Retrieves all reservations from the database.
   *
   * @return An iterable of ReservasEntity representing all reservations.
   * @throws RuntimeException If an error occurs while fetching reservations.
   * @Author zusldev
   * @Date 2023/08/14
   * @History - 2023/08/21 zusldev: Added exception handling and logging to
   * enhance reliability.
   * - 2023/08/22 zusldev: Updated exception handling messages for clarity.
   */
  @Transactional(readOnly = true)
  @Override
  public Iterable<ReservasEntity> findAll() {
    try {
      return reservasDao.findAll();
    } catch (Exception e) {
      // ResponseStatusException
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "An error occurred while fetching reservations", e);
    }
  }

  /**
   * @param nuevaReservaDTO DTO with the data of the reserva
   * @return a ReservasEntity
   * @Author zusldev
   * @Description save a reserva in database
   * @Date 2023/08/14
   */
  @Transactional
  @Override
  public ReservasEntity crearReserva(NuevaReservaDTO nuevaReservaDTO) {
    // Validate the data of the reserva
    ReservaValidationService.validarNuevaReserva(nuevaReservaDTO);

    // Using the mapper to convert the DTO to an entity
    ReservasEntity nuevaReserva = reservasMapper.toEntity(nuevaReservaDTO);

    // Get the tour from the database
    ToursEntity tour =
        tourDao.findById(nuevaReservaDTO.getTourId())
            .orElseThrow(()
                             -> new ResponseStatusException(
                                 HttpStatus.NOT_FOUND, "Tour not found"));

    nuevaReserva.setTour(tour);
    // Add abono to totalPagado
    nuevaReserva.setTotalPagado(nuevaReservaDTO.getAbono());
    nuevaReserva.setFechaReserva(new Timestamp(System.currentTimeMillis()));

    return reservasDao.save(nuevaReserva);
  }

  /**
   * @param reservaId        Integer
   * @param updateReservaDTO DTO with the data to update
   * @return a ReservasEntity
   * @Author zusldev
   * @Description update a reserva in database
   * @Date 2023/08/14
   */
  @Transactional
  @Override
  public ReservasEntity updateReserva(int reservaId,
                                      UpdateReservaDTO updateReservaDTO) {
    ReservasEntity reservaExistente =
        reservasDao.findById(reservaId).orElseThrow(
            ()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                               "Reserva not found"));

    // Actualizar los campos con los valores proporcionados en el DTO
    if (updateReservaDTO.getNumeroContacto() != null) {
      reservaExistente.setNumeroContacto(updateReservaDTO.getNumeroContacto());
    }
    if (updateReservaDTO.getEmail() != null) {
      reservaExistente.setEmail(updateReservaDTO.getEmail());
    }
    if (updateReservaDTO.getTipoCliente() != null) {
      reservaExistente.setTipoCliente(updateReservaDTO.getTipoCliente());
    }

    // Sumar el abono al total pagado
    if (updateReservaDTO.getAbono() != null &&
        updateReservaDTO.getAbono().compareTo(BigDecimal.ZERO) > 0) {
      BigDecimal totalPagadoActual = reservaExistente.getTotalPagado();
      BigDecimal abonoNuevo = updateReservaDTO.getAbono();
      BigDecimal nuevoTotalPagado = totalPagadoActual.add(abonoNuevo);
      reservaExistente.setTotalPagado(nuevoTotalPagado);
    }

    // Guardar los cambios en la base de datos
    return reservasDao.save(reservaExistente);
  }

  /**
   * Deletes a reservation from the database by ID.
   *
   * @param id The ID of the reservation to be deleted.
   * @throws ResponseStatusException If the provided ID is negative or the
   *                                 reservation is not found.
   * @throws RuntimeException        If an error occurs while deleting the
   *                                 reservation.
   * @Author zusldev
   * @Date 2023/08/14
   * @History - 2023/08/23 zusldev: Added validation, exception handling, and
   * documentation for deletion.
   */
  @Transactional
  @Override
  public void delete(int id) {
    if (id < 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                        "ID must be a non-negative value");
    }

    Optional<ReservasEntity> reservaOptional = reservasDao.findById(id);
    if (reservaOptional.isEmpty()) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "Reservation with ID " + id + " not found");
    }

    try {
      reservasDao.deleteById(id);
    } catch (Exception e) {
      throw new RuntimeException(
          "An error occurred while deleting the reservation. Please try again later.",
          e);
    }
  }

}
