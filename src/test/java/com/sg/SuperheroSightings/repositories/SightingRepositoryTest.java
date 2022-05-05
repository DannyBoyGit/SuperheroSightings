/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.repositories;

import com.sg.SuperheroSightings.App;
import com.sg.SuperheroSightings.entities.HeroVillain;
import com.sg.SuperheroSightings.entities.Location;
import com.sg.SuperheroSightings.entities.Sightings;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 *
 * @author danny
 */
@RunWith(SpringRunner.class) 
@SpringBootTest(classes=App.class)
public class SightingRepositoryTest {
    
    @Autowired
    private HeroVillainRepository heroes;
    @Autowired
    private LocationRepository location;
    @Autowired
    private SightingRepository sightings;
    
    public SightingRepositoryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        if(!sightings.findAll().isEmpty()){
            sightings.deleteAll();
        }
        if(!heroes.findAll().isEmpty()){
            heroes.deleteAll();
        }
        if(!location.findAll().isEmpty()){
            location.deleteAll();
        }
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testFindFirst10ByOrderByDateDesc() {
        HeroVillain hero = new HeroVillain();
        Location loc = new Location();
        
        hero.setName("Superman");
        hero.setDescription("Man of steel");
        heroes.save(hero);
        hero.setId(heroes.findAll().get(0).getId());
        
        loc.setName("Downtown");
        loc.setAddress("211 Somewhere Dr.");
        location.save(loc);
        loc.setId(location.findAll().get(0).getId());
        
        LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        List<LocalDateTime> times = new ArrayList<>();
        // make sure we add 11 to test the first 10 are in right order
        // and only 10 are being returned
        for(int i = 0; i<11; i++){
            Sightings sighting = new Sightings();
            sighting.setHero(hero);
            sighting.setLocation(loc);
            
            times.add(time.plusHours(i));
            sighting.setDate(time.minusHours(i));
            
            sightings.save(sighting);
        }
        
        List<Sightings> sights = sightings.findFirst10ByOrderByDateDesc();
        
        assertTrue("List should equal 10", sights.size()==10);
        
        String FirstDateReturned = sights.get(0).getDate().toString();
        String LastDateEntered = times.get(10).toString();
        assertTrue("The last date added should be the first one returned", FirstDateReturned.equals(LastDateEntered));
        
    }
    
}
