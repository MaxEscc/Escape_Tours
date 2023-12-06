package com.zs.api.apirest.controller;

import com.zs.api.apirest.exceptions.RegistroNoEncontradoException;
import com.zs.api.apirest.model.dto.UpdateTourRequestDTO;
import com.zs.api.apirest.model.entity.ToursEntity;
import com.zs.api.apirest.services.ITourServices;
import com.zs.api.apirest.services.impl.TourStateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class TourController {

  private final ITourServices tourServices;

  private final TourStateService tourStateService;

  public TourController(ITourServices tourServices,
                        TourStateService tourStateService) {
    this.tourServices = tourServices;
    this.tourStateService = tourStateService;
  }

  @GetMapping("/tours")
  public Iterable<ToursEntity> findAll() {
    Iterable<ToursEntity> tours = tourServices.findAll();
    // Calculate the status of each tour
    for (ToursEntity tour : tours) {
      tour.setEstatus(tourStateService.calcularEstado(tour));
    }
    return tours;
  }

  @PostMapping("/tours")
  public ToursEntity create(@RequestBody @Valid ToursEntity tour) {
    return tourServices.save(tour);
  }

  @PutMapping("/tours/{id}")
  public ToursEntity
  update(@PathVariable int id,
         @RequestBody @Valid UpdateTourRequestDTO updatedTour) {
    return tourServices.updateTour(id, updatedTour);
  }

  /**
   * Maneja la solicitud para eliminar un tour por su ID.
   * <p>
   * Este método recibe una solicitud DELETE para eliminar un tour específico
   * según su ID. Dependiendo de diferentes situaciones, devuelve respuestas
   * HTTP apropiadas indicando el resultado de la operación.
   *
   * @param id El ID del tour que se desea eliminar.
   * @return ResponseEntity con un mensaje indicando el resultado de la
   *     operación.
   */
  @DeleteMapping("/tours/{id}")
  public ResponseEntity<String> delete(@PathVariable int id) {
    try {
      tourServices.delete(id);
      return ResponseEntity.ok("Registro eliminado exitosamente");
    } catch (RegistroNoEncontradoException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body("" + e.getMessage());
    } catch (UnsupportedOperationException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("No se puede eliminar un tour con reservas.");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Ocurrió un error: " + e.getMessage());
    }
  }

  @GetMapping("/tours/{id}")
  public ToursEntity findById(@PathVariable int id) {
    ToursEntity tour = tourServices.findById(id);
    // Set the status of the tour
    tour.setEstatus(tourStateService.calcularEstado(tour));
    return tour;
  }
}
