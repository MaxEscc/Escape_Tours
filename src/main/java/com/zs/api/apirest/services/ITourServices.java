package com.zs.api.apirest.services;

import com.zs.api.apirest.model.dto.UpdateTourRequestDTO;
import com.zs.api.apirest.model.entity.ToursEntity;

public interface ITourServices {

  Iterable<ToursEntity> findAll();

  ToursEntity findById(int id);

  ToursEntity save(ToursEntity tour);

  void delete(int id);

  ToursEntity updateTour(int id, UpdateTourRequestDTO updatedTour);

}
