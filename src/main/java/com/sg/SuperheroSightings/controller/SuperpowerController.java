/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.controller;

import com.sg.SuperheroSightings.entities.HeroVillain;
import com.sg.SuperheroSightings.entities.Superpower;
import com.sg.SuperheroSightings.repositories.HeroVillainRepository;
import com.sg.SuperheroSightings.repositories.LocationRepository;
import com.sg.SuperheroSightings.repositories.OrganizationRepository;
import com.sg.SuperheroSightings.repositories.SightingRepository;
import com.sg.SuperheroSightings.repositories.SuperpowerRepository;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author danny
 */
@Controller
public class SuperpowerController {
    
    @Autowired
    HeroVillainRepository heroes;
    
    @Autowired
    LocationRepository locations;
    
    @Autowired
    OrganizationRepository organizations;
    
    @Autowired
    SuperpowerRepository power;
    
    @Autowired
    SightingRepository sighting;
    
    @PostMapping("addSuperpower")
    public String addSuperpower(Superpower superpower) {
        power.save(superpower);
        return "redirect:/superpowers";
    }
    
    @GetMapping("/superpowers")
    public String viewSuperpowers(Model model) {
        model.addAttribute("superpowers", power.findAll());

        return "superpowers";
    }
    
    @GetMapping("/deleteSuperpower")
    public String deleteSuperpower(Integer id){
        List<HeroVillain> heros = heroes.findAll();
        
        for(HeroVillain hero : heros){
            if(hero.getSuperpower().getId() == id){
                hero.setSuperpower(null);
            }
        }
        
        power.deleteById(id);
        return "redirect:/superpowers";
    }
    
    @GetMapping("editSuperpower")
    public String viewSuperpower(Integer id, Model model) {
        Superpower superpower = power.findById(id).orElse(null);
        model.addAttribute("superpower", superpower);

        return "editSuperpower";
    }
    
    @PostMapping("editSuperpower")
    public String performEditSuperpower(@Valid Superpower superpower) {
        power.save(superpower);
        
        return "redirect:/superpowers";
    }
    
}
