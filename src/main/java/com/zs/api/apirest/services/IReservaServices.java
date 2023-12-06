package com.zs.api.apirest.services;

import com.zs.api.apirest.model.dto.NuevaReservaDTO;
import com.zs.api.apirest.model.dto.UpdateReservaDTO;
import com.zs.api.apirest.model.entity.ReservasEntity;
import org.springframework.transaction.annotation.Transactional;

public interface IReservaServices {

  @Transactional(readOnly = true) ReservasEntity findById(int id);

  @Transactional(readOnly = true) Iterable<ReservasEntity> findAll();

  @Transactional ReservasEntity crearReserva(NuevaReservaDTO nuevaReservaDTO);

  @Transactional
  ReservasEntity updateReserva(int reservaId,
                               UpdateReservaDTO updateReservaDTO);

  @Transactional void delete(int id);

}
