/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author danny
 */
@Embeddable
public class SightingKey implements Serializable {
    
//    private static final long serialVersionUID = 1L;
    
    @Column(name = "locid")
    int locationId;

    @Column(name = "heroid")
    int heroId;
    
//    @Column(name="date")
//    LocalDateTime date;

//    public SightingKey(int locationId, int heroId) {
//        this.locationId = locationId;
//        this.heroId = heroId;
////        this.date = date;
//    }

    

    public SightingKey() {
    }
    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getHeroId() {
        return heroId;
    }

    public void setHeroId(int heroId) {
        this.heroId = heroId;
    }

//    public LocalDateTime getDate() {
//        return date;
//    }
//
//    public void setDate(LocalDateTime date) {
//        this.date = date;
//    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.locationId;
        hash = 53 * hash + this.heroId;
//        hash = 53 * hash + Objects.hashCode(this.date);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SightingKey other = (SightingKey) obj;
        if (this.locationId != other.locationId) {
            return false;
        }
        if (this.heroId != other.heroId) {
            return false;
        }
//        if (!Objects.equals(this.date, other.date)) {
//            return false;
//        }
        return true;
    }
    
    

}
