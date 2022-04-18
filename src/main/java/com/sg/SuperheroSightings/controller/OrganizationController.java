/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.controller;

import com.sg.SuperheroSightings.entities.HeroVillain;
import com.sg.SuperheroSightings.entities.Organization;
import com.sg.SuperheroSightings.repositories.HeroVillainRepository;
import com.sg.SuperheroSightings.repositories.LocationRepository;
import com.sg.SuperheroSightings.repositories.OrganizationRepository;
import com.sg.SuperheroSightings.repositories.SightingRepository;
import com.sg.SuperheroSightings.repositories.SuperpowerRepository;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author danny
 */
@Controller
public class OrganizationController {
    
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
    
    @PostMapping("addOrganization")
    public String addOrganization(Organization org, HttpServletRequest request) {
        String[] heroIds = request.getParameterValues("memberId");
        List<HeroVillain> heroes = new ArrayList<>();
        for(String heroId : heroIds) {
            heroes.add(this.heroes.findById(Integer.parseInt(heroId)).orElse(null));
        }
        org.setMembers(heroes);
        
        organizations.save(org);
        return "redirect:/organizations";
    }
    
    @GetMapping("/deleteOrganization")
    public String deleteOrganization(Integer id){
        organizations.deleteById(id);
        return "redirect:/organizations";
    }
    
    @GetMapping("/organizations")
    public String viewOrganizations(Model model) {
        model.addAttribute("organizations", organizations.findAll());
        model.addAttribute("members", heroes.findAll());
        return "organizations";
    }

    @GetMapping("/viewOrganization")
    public String viewOrganization(Integer id, Model model) {
        Organization organization = organizations.findById(id).orElse(null);
        model.addAttribute("organization", organization);
        model.addAttribute("members", heroes.findAll());

        return "viewOrganization";
    }
    
    @PostMapping("editOrganization")
    public String editOrganization(@Valid Organization org, HttpServletRequest request){
        String[] heroIds = request.getParameterValues("memberId");
        List<HeroVillain> heroes = new ArrayList<>();
        for(String heroId : heroIds) {
            heroes.add(this.heroes.findById(Integer.parseInt(heroId)).orElse(null));
        }
        org.setMembers(heroes);
        
        organizations.save(org);
        return "redirect:/organizations";
    }
}
