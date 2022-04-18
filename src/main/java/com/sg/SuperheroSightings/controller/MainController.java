/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.sg.SuperheroSightings.repositories.HeroVillainRepository;
import com.sg.SuperheroSightings.repositories.LocationRepository;
import com.sg.SuperheroSightings.repositories.OrganizationRepository;
import com.sg.SuperheroSightings.repositories.SightingRepository;
import com.sg.SuperheroSightings.repositories.SuperpowerRepository;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author danny
 */
@Controller
public class MainController {
    
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
    
    @GetMapping("/")
    public String index(Model model) {
        
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonSighting;
//        try {
//            jsonSighting = mapper.writeValueAsString(sighting.findFirst10ByOrderByDateDesc());
//            model.addAttribute("jsonSighting", jsonSighting);
//            System.out.println(jsonSighting);
//
//        } catch (JsonProcessingException ex) {
//            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        model.addAttribute("sightings", sighting.findFirst10ByOrderByDateDesc());
        return "index";
    }

}
