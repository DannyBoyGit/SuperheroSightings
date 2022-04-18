/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.controller;

import com.sg.SuperheroSightings.entities.HeroVillain;
import com.sg.SuperheroSightings.entities.Location;
import com.sg.SuperheroSightings.entities.Sightings;
import com.sg.SuperheroSightings.entities.Superpower;
import com.sg.SuperheroSightings.repositories.HeroVillainRepository;
import com.sg.SuperheroSightings.repositories.LocationRepository;
import com.sg.SuperheroSightings.repositories.OrganizationRepository;
import com.sg.SuperheroSightings.repositories.SightingRepository;
import com.sg.SuperheroSightings.repositories.SuperpowerRepository;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author danny
 */
@Controller
public class SightingController {
    
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
    
    Set<ConstraintViolation<Sightings>> violations = new HashSet<>();
    
    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request) {
        
        int heroId = Integer.parseInt(request.getParameter("heroId"));
        int locationId = Integer.parseInt(request.getParameter("locationId"));
        LocalDateTime date = LocalDateTime.parse(request.getParameter("addDate"));
        
        HeroVillain hero = heroes.findById(heroId).orElse(null);
        Location location = locations.findById(locationId).orElse(null);
        
        Sightings sighting = new Sightings();
        sighting.setHero(hero);
        sighting.setLocation(location);
        sighting.setDate(date);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);
        
        if(violations.isEmpty()) {
            hero.getDates().add(sighting);
            location.getDates().add(sighting);
            this.sighting.save(sighting);
        }
        
        return "redirect:/sightings";
    }
    
    @GetMapping("/removeSighting")
    public String removeSighting(Integer id){
        sighting.deleteById(id);
        return "redirect:/sightings";
    }
    
    @GetMapping("/sightings")
    public String viewSightings(Model model) {
        model.addAttribute("heroes", heroes.findAll());
        model.addAttribute("locations", locations.findAll());
        model.addAttribute("sightings", sighting.findAll());
        model.addAttribute("errors", violations);

        return "sightings";
    }
    
    @GetMapping("/editSighting")
    public String editSighting(Integer id, Model model){
        Sightings sighting = this.sighting.findById(id).orElse(null);
        model.addAttribute("heroes", heroes.findAll());
        model.addAttribute("locations", locations.findAll());
        model.addAttribute("sighting", sighting);
        model.addAttribute("errors", violations);
        
        return "editSighting";
    }
    
    @PostMapping("editSighting")
    public String editSighting(HttpServletRequest request){

        
        int heroId = Integer.parseInt(request.getParameter("heroId"));
        int sightingId = Integer.parseInt(request.getParameter("id"));
        int locationId = Integer.parseInt(request.getParameter("locationId"));
        LocalDateTime date = LocalDateTime.parse(request.getParameter("addDate"));

        HeroVillain hero = heroes.findById(heroId).orElse(null);
        Location loc = locations.findById(locationId).orElse(null);
        Sightings sight = sighting.findById(sightingId).orElse(null);
        
        sight.setHero(hero);
        sight.setLocation(loc);
        sight.setDate(date);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sight);
        
        if(violations.isEmpty()) {
        
            sighting.save(sight);

            List<Sightings> heroSighting = hero.getDates();
            List<Sightings> locSighting = loc.getDates();

            for(int i=0; i<heroSighting.size(); i++){
                if(heroSighting.get(i).getId() == sightingId){
                    heroSighting.set(i, sight);
                }
            }

            for(int i=0; i<locSighting.size(); i++){
                if(locSighting.get(i).getId() == sightingId){
                    locSighting.set(i, sight);
                }
            }
            return "redirect:/sightings";
        }
        
        return "redirect:/editSighting?id=" + sightingId;
    }
}
