/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.controller;

import com.sg.SuperheroSightings.entities.HeroVillain;
import com.sg.SuperheroSightings.entities.Organization;
import com.sg.SuperheroSightings.entities.Sightings;
import com.sg.SuperheroSightings.entities.Superpower;
import com.sg.SuperheroSightings.repositories.HeroVillainRepository;
import com.sg.SuperheroSightings.repositories.LocationRepository;
import com.sg.SuperheroSightings.repositories.OrganizationRepository;
import com.sg.SuperheroSightings.repositories.SightingRepository;
import com.sg.SuperheroSightings.repositories.SuperpowerRepository;
import com.sg.SuperheroSightings.service.ImgBase64;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author danny
 */
@Controller
public class HeroVillainController {
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
    
    Set<ConstraintViolation<HeroVillain>> violations = new HashSet<>();
    
    @PostMapping("addHeroVillain")
    public String addHeroVillain(HeroVillain hero, HttpServletRequest request, @RequestParam("image") MultipartFile file) {
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(hero);
        
        if(violations.isEmpty()) {
            int powerId = Integer.parseInt(request.getParameter("superpowerId"));
            Superpower superpower = power.findById(powerId).orElse(null);
            byte[] imageData;

            try{
                imageData = file.getBytes();
                hero.setHeroimage(imageData);
            } catch(IOException e){

            }
            
            hero.setSuperpower(superpower);
            heroes.save(hero);
        }
        
        return "redirect:/heroVillains";
    }
    
    @GetMapping("/deleteHeroVillain")
    public String deleteHeroVillain(Integer id){
        HeroVillain hero = heroes.findById(id).orElse(null);
        List<Organization> orgs = organizations.findAll();
        List<Sightings> sightings = sighting.findAll();
        
        for(Organization org : orgs){
            if(org.getMembers().contains(hero)){
                org.getMembers().remove(hero);
            }
        }
        for(Sightings sight : sightings){
            if(sight.getHero().getId() == id){
                sighting.deleteById(sight.getId());
            }
        }
        
        heroes.deleteById(id);
        return "redirect:/heroVillains";
    }
    
    
    
    @GetMapping("/heroVillains")
    public String viewHeroVillains(Model model) {
        model.addAttribute("superpowers", power.findAll());
        model.addAttribute("heroes", heroes.findAll());
        model.addAttribute("imageToBase64", new ImgBase64());
         model.addAttribute("errors", violations);

        return "heroVillains";
    }
    
    @GetMapping("/viewHeroVillain")
    public String viewHeroVillain(Integer id, Model model) {
        HeroVillain hero = heroes.findById(id).orElse(null);
        model.addAttribute("hero", hero);
        model.addAttribute("superpowers", power.findAll());
        model.addAttribute("imageToBase64", new ImgBase64());
        model.addAttribute("errors", violations);

        return "viewHeroVillain";
    }
    
    @PostMapping("editHeroVillain")
    public String editHeroVillain(@Valid HeroVillain hero, BindingResult result, HttpServletRequest request, @RequestParam("image") MultipartFile file){
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(hero);
        
        if(violations.isEmpty()) {
            int powerId = Integer.parseInt(request.getParameter("superpowerId"));
            Superpower superpower = power.findById(powerId).orElse(null);
            byte[] imageData;


            if(file.isEmpty()){
                HeroVillain original = heroes.findById(hero.getId()).orElse(null);
                hero.setHeroimage(original.getHeroimage());
            } else{
                try{
                    imageData = file.getBytes();
                    hero.setHeroimage(imageData);
                } catch(IOException e){
                    System.out.println("Error converting to byte code");
                }
            }


            hero.setSuperpower(superpower);
            heroes.save(hero);
            return "redirect:/heroVillains";
        }
        return "redirect:/viewHeroVillain?id=" + hero.getId();
    }
}
