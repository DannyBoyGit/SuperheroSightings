/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.repositories;

import com.sg.SuperheroSightings.entities.Sightings;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author danny
 */
@Repository
public interface SightingRepository extends JpaRepository<Sightings, Integer>{
    List<Sightings> findFirst10ByOrderByDateDesc();
}
