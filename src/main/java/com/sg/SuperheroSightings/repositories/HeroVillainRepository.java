/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.repositories;

import com.sg.SuperheroSightings.entities.HeroVillain;
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
public interface HeroVillainRepository extends JpaRepository<HeroVillain, Integer> {
    @Query(value = "SELECT h.* from hero_villain h "
            + "join hero_location hl on hl.heroId = h.Id "
            + "where hl.date = ?1", nativeQuery=true)
    List<HeroVillain> getHeroesForDate(String date);
}
