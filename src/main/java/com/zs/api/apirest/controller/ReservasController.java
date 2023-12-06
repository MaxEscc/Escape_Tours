package com.zs.api.apirest.controller;

import com.zs.api.apirest.model.dto.NuevaReservaDTO;
import com.zs.api.apirest.model.dto.UpdateReservaDTO;
import com.zs.api.apirest.model.entity.ReservasEntity;
import com.zs.api.apirest.model.entity.ToursEntity;
import com.zs.api.apirest.services.IReservaServices;
import com.zs.api.apirest.services.impl.TourStateService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ReservasController {

  private final IReservaServices reservaServices;

  private final TourStateService tourStateServices;

  public ReservasController(IReservaServices reservaServices,
                            TourStateService tourStateServices) {
    this.reservaServices = reservaServices;
    this.tourStateServices = tourStateServices;
  }

  /**
   * @return an Iterable of ReservasEntity
   * @Author zusldev
   * @Description get all reservas from database and calculate the status of
   * each tour
   * @Date 2023/08/14
   */
  @GetMapping("/reservas")
  public Iterable<ReservasEntity> findAll() {
    Iterable<ReservasEntity> reservas = reservaServices.findAll();

    // Calcular y asignar el estado del tour para cada reserva
    reservas.forEach(reserva -> {
      ToursEntity tour = reserva.getTour();
      tour.setEstatus(tourStateServices.calcularEstado(tour));
    });

    return reservas;
  }

  /**
   * @return a ReservasEntity
   * @Author zusldev
   * @PathVariable id
   * @Description get a reserva from database
   * @Date 2023/08/14
   */
  @GetMapping("/reservas/{id}")
  public ReservasEntity findById(@PathVariable Integer id) {
    ReservasEntity reserva = reservaServices.findById(id);
    ToursEntity tour = reserva.getTour();
    tour.setEstatus(tourStateServices.calcularEstado(tour));
    return reserva;
  }

  /**
   * @return a ReservasEntity
   * @Description Create a reserva in database
   * @Author zusldev
   * @RequestBody reserva
   * @Date 2023/08/14
   */
  @PostMapping("/reservas")
  public ReservasEntity create(@RequestBody @Valid NuevaReservaDTO reserva) {
    return reservaServices.crearReserva(reserva);
  }

  /**
   * @return a ReservasEntity
   * @Description Update a reserva in database
   * @Author zusldev
   * @RequestBody reserva
   * @Date 2023/08/14
   */
  @PutMapping("/reservas/{id}")
  public ReservasEntity update(@PathVariable int id,
                               @RequestBody UpdateReservaDTO reserva) {
    return reservaServices.updateReserva(id, reserva);
  }

  /**
   * @Description Delete a reserva in database
   * @Author zusldev
   * @PathVariable id
   * @Date 2023/08/14
   */
  @DeleteMapping("/reservas/{id}")
  public void delete(@PathVariable int id) {
    reservaServices.delete(id);
  }
}
