/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.entities;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author danny
 */
@Entity
public class HeroVillain {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Lob
    @Column(name="heroimage", nullable=true)
    private byte[] heroimage;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "superpowerid")
    private Superpower superpower;
    
    @OneToMany(mappedBy = "hero")
    private List<Sightings> dates;
    
    @ManyToMany(mappedBy = "members")
    private List<Organization> orgs;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Superpower getSuperpower() {
        return superpower;
    }

    public void setSuperpower(Superpower superpower) {
        this.superpower = superpower;
    }

    public List<Sightings> getDates() {
        return dates;
    }

    public void setDates(List<Sightings> dates) {
        this.dates = dates;
    }

    public List<Organization> getOrgs() {
        return orgs;
    }

    public void setOrgs(List<Organization> orgs) {
        this.orgs = orgs;
    }

    public byte[] getHeroimage() {
        return heroimage;
    }

    public void setHeroimage(byte[] heroimage) {
        this.heroimage = heroimage;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.id;
        hash = 53 * hash + Arrays.hashCode(this.heroimage);
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + Objects.hashCode(this.description);
        hash = 53 * hash + Objects.hashCode(this.superpower);
        hash = 53 * hash + Objects.hashCode(this.dates);
        hash = 53 * hash + Objects.hashCode(this.orgs);
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
        final HeroVillain other = (HeroVillain) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Arrays.equals(this.heroimage, other.heroimage)) {
            return false;
        }
        if (!Objects.equals(this.superpower, other.superpower)) {
            return false;
        }
        if (!Objects.equals(this.dates, other.dates)) {
            return false;
        }
        if (!Objects.equals(this.orgs, other.orgs)) {
            return false;
        }
        return true;
    }
    
}
