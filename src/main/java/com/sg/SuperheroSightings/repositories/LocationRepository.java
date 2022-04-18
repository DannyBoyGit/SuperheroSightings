/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.repositories;

import com.sg.SuperheroSightings.entities.Location;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author danny
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
    @Query(value = "SELECT l.* from location l "
            + "join hero_location hl on hl.heroId = l.Id "
            + "where hl.date = ?;", nativeQuery=true)
    List<Location> getLocationsForDate(String date);
}
