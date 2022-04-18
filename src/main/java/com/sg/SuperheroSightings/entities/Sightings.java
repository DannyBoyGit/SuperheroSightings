/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

/**
 *
 * @author danny
 */
@Entity
public class Sightings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "locid") 
    private Location location;
    
    @ManyToOne  
    @JoinColumn(name = "heroid")
    private HeroVillain hero;
    @Past(message="Date must be in the past")
    private LocalDateTime date;

    public Sightings(int id) {
        this.id  = id;
    }

    public Sightings() {
    }
    
    public int getId() {
        return id;
    }
    
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public HeroVillain getHero() {
        return hero;
    }

    public void setHero(HeroVillain hero) {
        this.hero = hero;
    }

//    @Override
//    public int hashCode() {
//        int hash = 3;
//        hash = 59 * hash + Objects.hashCode(this.id);
//        hash = 59 * hash + Objects.hashCode(this.location);
//        hash = 59 * hash + Objects.hashCode(this.hero);
//        hash = 59 * hash + Objects.hashCode(this.date);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final Sightings other = (Sightings) obj;
//        if (!Objects.equals(this.id, other.id)) {
//            return false;
//        }
//        if (!Objects.equals(this.location, other.location)) {
//            return false;
//        }
//        if (!Objects.equals(this.hero, other.hero)) {
//            return false;
//        }
//        if (!Objects.equals(this.date, other.date)) {
//            return false;
//        }
//        return true;
//    }
    
}
