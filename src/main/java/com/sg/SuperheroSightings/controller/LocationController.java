/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.controller;

import com.sg.SuperheroSightings.entities.Location;
import com.sg.SuperheroSightings.entities.Sightings;
import com.sg.SuperheroSightings.entities.Superpower;
import com.sg.SuperheroSightings.repositories.HeroVillainRepository;
import com.sg.SuperheroSightings.repositories.LocationRepository;
import com.sg.SuperheroSightings.repositories.OrganizationRepository;
import com.sg.SuperheroSightings.repositories.SightingRepository;
import com.sg.SuperheroSightings.repositories.SuperpowerRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author danny
 */
@Controller
public class LocationController {
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
    
    Set<ConstraintViolation<Location>> violations = new HashSet<>();
    
    @PostMapping("addLocation")
    public String addLocation(Location location) {
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(location);

        if(violations.isEmpty()) {
            locations.save(location);
        }
        
        return "redirect:/locations";
    }
    
    @GetMapping("/deleteLocation")
    public String deleteLocation(Integer id){
        List<Sightings> sightings = sighting.findAll();
        
        for(Sightings sight : sightings){
            if(sight.getLocation().getId() == id){
                sighting.deleteById(sight.getId());
            }
        }
        
        locations.deleteById(id);
        return "redirect:/locations";
    }
    
    
    @GetMapping("/locations")
    public String viewLocations(Model model) {
        model.addAttribute("locations", locations.findAll());
        model.addAttribute("errors", violations);

        return "locations";
    }
    
    @GetMapping("/viewLocation")
    public String viewLocation(Integer id, Model model) {
        Location location = locations.findById(id).orElse(null);
        model.addAttribute("location", location);

        return "viewLocation";
    }
    
    @PostMapping("editLocation")
    public String editLocation(@Valid Location location, BindingResult result){
        if(result.hasErrors()) {
            return "viewLocation";
        }
        
        locations.save(location);
        
        return"redirect:/locations";
    }
}
