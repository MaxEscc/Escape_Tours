package com.zs.api.apirest.model.dao;

import com.zs.api.apirest.model.entity.ToursEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for accessing and managing TourEntity objects in the
 * database.
 *
 * This repository provides CRUD operations (Create, Read, Update, Delete) for
 * TourEntity objects and also includes a custom query for counting reservations
 * associated with a specific tour.
 */
public interface TourDao extends CrudRepository<ToursEntity, Integer> {

  /**
   * Custom query to count reservations associated with a specific tour.
   *
   * This query retrieves the count of reservations associated with a tour
   * identified by its ID.
   *
   * @param tourId The ID of the tour for which to count reservations.
   * @return The count of reservations associated with the specified tour.
   */
  @Query("SELECT COUNT(r) FROM ReservasEntity r WHERE r.tour.tourId = :tourId")
  Long countReservasByTourId(int tourId);
}
