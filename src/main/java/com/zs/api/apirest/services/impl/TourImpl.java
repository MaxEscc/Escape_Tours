package com.zs.api.apirest.services.impl;

import com.zs.api.apirest.exceptions.RegistroNoEncontradoException;
import com.zs.api.apirest.model.dao.TourDao;
import com.zs.api.apirest.model.dto.UpdateTourRequestDTO;
import com.zs.api.apirest.model.entity.ToursEntity;
import com.zs.api.apirest.services.ITourServices;
import com.zs.api.apirest.services.validation.TourValidationService;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TourImpl implements ITourServices {


  private final TourDao tourDao;

  private final TourValidationService tourValidationService;

  private final TourStateService tourStateService;

  public TourImpl(TourDao tourDao, TourValidationService tourValidationService,
                  TourStateService tourStateService) {
    this.tourDao = tourDao;
    this.tourValidationService = tourValidationService;
    this.tourStateService = tourStateService;
  }

  /**
   * @param id Integer
   * @return a ToursEntity
   * @Author zusldev
   * @Description get a tour from database
   * @Date 2023/08/11
   */
  @Transactional(readOnly = true)
  @Override
  public ToursEntity findById(int id) {
    // Validate if the tour exists
    return tourDao.findById(id).orElseThrow(
        ()
            -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                String.format("Tour with id %d not found", id)));
  }

  /**
   * @return an Iterable of ToursEntity
   * @Param none
   * @Author zusldev
   * @Description get all tours from database
   * @Date 2023/08/11
   */
  @Transactional(readOnly = true)
  @Override
  public Iterable<ToursEntity> findAll() {
    return tourDao.findAll();
  }

  /**
   * @param tour a ToursEntity
   * @return a ToursEntity
   * @Author zusldev
   * @Description save a tour in database
   * @Date 2023/08/11
   */
  @Transactional
  @Override
  public ToursEntity save(ToursEntity tour) {

    // Validate the data of the tour
    tourValidationService.validarDatosTour(tour);
    ToursEntity savedTour = tourDao.save(tour);
    String estado = tourStateService.calcularEstado(savedTour);
    savedTour.setEstatus(estado);
    return savedTour;
  }

  /**
   * @param id          Integer
   * @param updatedTour UpdateTourRequestDTO @RequestBody
   * @return a ToursEntity
   * @Author zusldev
   * @Description update a tour in database
   * @Date 2023/08/11
   */
  @Transactional
  @Override
  public ToursEntity updateTour(int id,
                                @RequestBody UpdateTourRequestDTO updatedTour) {
    ToursEntity existingTour = findById(id);
    if (existingTour == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tour not found");
    }

    // update necessary fields of the existing tour with the new values
    if (updatedTour.getNombre() != null) {
      existingTour.setNombre(updatedTour.getNombre());
    }
    if (updatedTour.getFechaSalida() != null) {
      existingTour.setFechaSalida(updatedTour.getFechaSalida());
    }
    if (updatedTour.getFechaRegreso() != null) {
      existingTour.setFechaRegreso(updatedTour.getFechaRegreso());
    }
    if (updatedTour.getPrecioBaseAdulto() != null) {
      existingTour.setPrecioBaseAdulto(updatedTour.getPrecioBaseAdulto());
    }
    if (updatedTour.getPrecioBaseNino() != null) {
      existingTour.setPrecioBaseNino(updatedTour.getPrecioBaseNino());
    }
    // Calculate the status of the tour
    String estado = tourStateService.calcularEstado(existingTour);
    existingTour.setEstatus(estado);

    // Save the updated tour in database
    return tourDao.save(existingTour);
  }

  /**
   * Elimina un tour por su ID.
   * Este método elimina un tour de la base de datos según su ID. Si el tour no
   * se encuentra, se lanza una RegistroNoEncontradoException. Si el tour tiene
   * reservas asociadas, se lanza una UnsupportedOperationException.
   *
   * @param id El ID del tour que se desea eliminar.
   * @throws RegistroNoEncontradoException Si no se encuentra el tour con el ID
   *     especificado.
   * @throws UnsupportedOperationException Si el tour tiene reservas asociadas y
   *     no puede ser eliminado.
   * @Modified MaxEscc
   * @History
   * +*********************************************************************************************************************
   * | Version | Date        | Author            | Description
   * +*********************************************************************************************************************
   * | 1.0     | 2023/08/17  | zusldev           | Initial version
   * +*********************************************************************************************************************
   * | 1.1     | 2023/08/17  | MaxEscc           | Added validation for
   * reservations associated to the tour.
   * +*********************************************************************************************************************
   */
  @Transactional
  @Override
  public void delete(int id)throws RegistroNoEncontradoException {
    // Buscar el tour por su ID o lanzar RegistroNoEncontradoException si no se
    // encuentra
    ToursEntity registro = tourDao.findById(id).orElseThrow(
        ()
            -> new RegistroNoEncontradoException(
                "No se encontró el registro con ID: " + id));

    // Contar las reservas asociadas al tour
    Long countReservas = tourDao.countReservasByTourId(id);

    // Verificar si hay reservas y lanzar UnsupportedOperationException si las
    // hay
    if (countReservas > 0) {
      throw new UnsupportedOperationException(
          "No se puede eliminar un tour con reservas.");
    }

    // Eliminar el tour
    tourDao.delete(registro);
  }
}
